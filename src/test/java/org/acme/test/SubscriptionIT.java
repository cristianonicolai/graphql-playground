package org.acme.test;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;
import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;
import io.smallrye.mutiny.helpers.test.AssertSubscriber;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.containsString;

@QuarkusIntegrationTest
public class SubscriptionIT {

    static final String METRIC_FORMAT =
            "subscription_active %s\n";

    static GraphQLApiClient buildGraphQLApiClient() {
        Integer port = ConfigProvider.getConfig().getValue("quarkus.http.test-port", Integer.class);
        String endpoint = String.format("http://localhost:%d/graphql", port);
        return TypesafeGraphQLClientBuilder.newBuilder().endpoint(endpoint).build(GraphQLApiClient.class);
    }

    @Test
    public void testSubscription() {
        GraphQLApiClient client = buildGraphQLApiClient();

        AssertSubscriber<PlanTestDTO> sub = AssertSubscriber.create(2);
        client.planUpdated(UUID.randomUUID()).subscribe(sub);

        sub.awaitSubscription();
        //Await subscription to start in the backend
        await().untilAsserted(() -> given().contentType(ContentType.TEXT)
                .when()
                .get("/q/metrics")
                .then()
                .statusCode(200)
                .body(containsString(format(METRIC_FORMAT, "1.0"))));

        sub.awaitItems(1);

        PlanTestDTO dto = sub.getItems().getFirst();
        assertThat(dto).isNotNull();
        assertThat(dto.eventId()).isNotNull();
        assertThat(dto.time()).isNotNull();
        assertThat(dto.score().hard()).isEqualTo(1);
        assertThat(dto.score().soft()).isEqualTo(3);
        assertThat(dto.score().medium()).isEqualTo(2);
        assertThat(dto.events()).hasSize(3);
        assertThat(dto.events().getFirst().eventId()).isNotNull();
        assertThat(dto.events().getFirst().time()).isNotNull();
        assertThat(dto.events().getFirst().score().hard()).isEqualTo(1);
        assertThat(dto.events().getFirst().score().soft()).isEqualTo(3);
        assertThat(dto.events().getFirst().score().medium()).isEqualTo(2);

        sub.cancel();
        await().untilAsserted(() -> given().contentType(ContentType.TEXT)
                .when()
                .get("/q/metrics")
                .then()
                .statusCode(200)
                .body(containsString(format(METRIC_FORMAT, "0.0"))));
    }

}
