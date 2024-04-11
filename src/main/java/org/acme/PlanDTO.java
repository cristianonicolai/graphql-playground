package org.acme;

import org.eclipse.microprofile.graphql.Type;

import java.time.OffsetDateTime;
import java.util.UUID;

@Type("Plan")
public record PlanDTO(UUID eventId, Score score, OffsetDateTime time) {
}
