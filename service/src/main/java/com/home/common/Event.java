package com.home.common;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Event {

    private final String title;
    private final String description;
    private final UUID id;
    private final List<Person> attenders;
    private final List<String> attendersEmails;
    private final Date startDate;
    private final Date endDate;

    private Event(Builder builder) {
        id = UUID.randomUUID();
        title = builder.title;
        description = builder.description;
        attenders = builder.attenders;
        attendersEmails = builder.attendersEmails;
        startDate = builder.startDate;
        endDate = builder.endDate;
    }

    public List<Person> getAttenders() {
        return attenders;
    }

    public List<String> getAttendersEmails() {
        return attendersEmails;
    }

    public String getDescription() {
        return description;
    }

    public Date getEndDate() {
        return endDate;
    }

    public UUID getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
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
                ", attendersEmails=" + attendersEmails +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", id=" + id +
                '}';
    }

    public static class Builder {

        private String title;
        private String description;
        private List<Person> attenders;
        private List<String> attendersEmails;
        private Date startDate;
        private Date endDate;

        public Builder() {
        }

        public Builder(Event event) {
            this.title = event.title;
            this.description = event.description;
            this.attenders = event.attenders;
            this.attendersEmails = event.attendersEmails;
            this.startDate = event.startDate;
            this.endDate = event.endDate;
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

        public Builder attendersEmails(List<String> attendersEmails) {
            this.attendersEmails = attendersEmails;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }
}
