package org.acme.test;

import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.graphql.NonNull;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;

public interface GraphQLApiClient {

    List<PlanTestDTO> plans();

    @Subscription
    Multi<PlanTestDTO> planUpdated(@NonNull UUID runId);
}
