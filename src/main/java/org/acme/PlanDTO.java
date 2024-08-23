package org.acme;

import org.eclipse.microprofile.graphql.Type;

import java.time.OffsetDateTime;
import java.util.UUID;

@Type("Plan")
public class PlanDTO {

    private UUID eventId;
    private Score score;
    private OffsetDateTime time;

    public PlanDTO() {
    }

    public PlanDTO(UUID eventId, Score score, OffsetDateTime time) {
        this.eventId = eventId;
        this.score = score;
        this.time = time;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "PlanDTO{" +
                "eventId=" + eventId +
                ", score=" + score +
                ", time=" + time +
                '}';
    }
}
