package org.acme;

import io.micrometer.core.instrument.MeterRegistry;
import io.smallrye.graphql.api.Context;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@GraphQLApi
public class GraphQLResource {

    public static final String METRIC_SUBSCRIPTION_COUNT = "subscription.active";

    MeterRegistry meterRegistry;

    AtomicLong subscriptions = new AtomicLong(0);

    @Inject
    public GraphQLResource(MeterRegistry meterRegistry) {
        System.out.println("post construct");
        meterRegistry.gauge(METRIC_SUBSCRIPTION_COUNT, subscriptions);
        this.meterRegistry = meterRegistry;
    }

    @Query("plans")
    public List<PlanDTO> plans(UUID runId) {
        return List.of(new PlanDTO(UUID.randomUUID(), new Score(1L, 2L, 3L), OffsetDateTime.now()));
    }

    public List<List<EventDTO>> events(@Source List<PlanDTO> dtos, Context context) {
//    public List<EventDTO> events(@Source PlanDTO dto, Context context) {
        System.out.println("events context = " + context);
        System.out.println("context getSelectedFields = " + context.getSelectedFields());
        System.out.println("context getSelectedAndSourceFields = " + context.getSelectedAndSourceFields());
        if (context.getSelectedFields().isEmpty()) {
            throw new RuntimeException("Invalid request");
        }

        return dtos.stream().map(dto -> List.of(
                getEvent(dto), getEvent(dto), getEvent(dto))).toList();
//        return List.of(
//                getEvent(dto), getEvent(dto), getEvent(dto));
    }

    private static EventDTO getEvent(PlanDTO dto) {
        return new EventDTO(dto.getEventId(), UUID.randomUUID(), new Score(1L, 2L, 3L), OffsetDateTime.now());
    }

    @Subscription
    public Multi<PlanDTO> planUpdated(@NonNull UUID runId, Context context) {
        System.out.println("subscription context = " + context);
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(1))
                .onItem()
                .transform(t -> new PlanDTO(UUID.randomUUID(), new Score(1L, 2L, 3L), OffsetDateTime.now()))
                .onSubscription().invoke(() -> subscriptions.incrementAndGet())
                .onTermination().invoke(() -> subscriptions.decrementAndGet());
    }

}