package org.acme.test;

import org.acme.EventDTO;
import org.acme.Score;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record PlanTestDTO(UUID eventId, Score score, List<EventDTO> events, OffsetDateTime time) {
}
