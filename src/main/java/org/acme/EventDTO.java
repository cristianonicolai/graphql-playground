package org.acme;

import org.eclipse.microprofile.graphql.Type;

import java.time.OffsetDateTime;
import java.util.UUID;

@Type("Event")
public record EventDTO(UUID eventId, UUID runId, Score score, OffsetDateTime time) {
}
