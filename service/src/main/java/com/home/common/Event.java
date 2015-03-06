package com.home.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Event implements Serializable, EventInterface {

    private final String title;
    private final String description;
    private final UUID id;
    private final List<Person> attenders;
    private final List<String> attendersLogins;
    private final Date startTime;
    private final Date endTime;

    private Event(Builder builder) {
        id = UUID.randomUUID();
        title = builder.title;
        description = builder.description;
        attenders = builder.attenders;
        attendersLogins = builder.attendersLogins;
        startTime = builder.startTime;
        endTime = builder.endTime;
    }

    public List<Person> getAttenders() {
        return attenders;
    }

    public List<String> getAttendersLogins() {
        return attendersLogins;
    }

    public String getDescription() {
        return description;
    }

    public Date getEndTime() {
        return endTime;
    }

    public UUID getId() {
        return id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!id.equals(event.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Event{" +
                "title=" + title +
                ", description='" + description + '\'' +
                ", attenders='" + attenders + '\'' +
                ", attendersLogins=" + attendersLogins +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", id=" + id +
                '}';
    }

    public static class Builder {

        private String title;
        private String description;
        private List<Person> attenders;
        private List<String> attendersLogins;
        private Date startTime;
        private Date endTime;

        public Builder() {
        }

        public Builder(Event event) {
            this.title = event.title;
            this.description = event.description;
            this.attenders = event.attenders;
            this.attendersLogins = event.attendersLogins;
            this.startTime = event.startTime;
            this.endTime = event.endTime;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder attenders(List<Person> attenders) {
            this.attenders = attenders;
            return this;
        }

        public Builder attendersLogins(List<String> attendersLogins) {
            this.attendersLogins = attendersLogins;
            return this;
        }

        public Builder startTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }
}
