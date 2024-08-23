package org.acme.test;

import org.acme.EventDTO;
import org.acme.PlanDTO;
import org.acme.Score;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class PlanTestDTO extends PlanDTO {

    private UUID eventId;
    private Score score;
    private List<EventDTO> events;
    private OffsetDateTime time;

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

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "PlanTestDTO{" +
                "eventId=" + eventId +
                ", score=" + score +
                ", events=" + events +
                ", time=" + time +
                '}';
    }
}
