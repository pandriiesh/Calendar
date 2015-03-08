package com.home.common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Event implements Serializable {

    private String title;
    private String description;
    private final UUID id;
    private List<Person> attenders;
    private Date startTime;
    private Date endTime;

    public Event() {

        startTime = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        endTime = cal.getTime();

        id = UUID.randomUUID();
    }

    public Event(Event event) {
        title = event.title;
        description = event.description;
        id = UUID.randomUUID();
        attenders = event.attenders;
        startTime = event.startTime;
        endTime = event.endTime;
    }

    public UUID getId() {
        return id;
    }

    public List<Person> getAttenders() {
        return attenders;
    }

    public void setAttenders(List<Person> attenders) {
        this.attenders = attenders;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        endTime = cal.getTime();

        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event that = (Event) o;

        if (!id.equals(that.id)) return false;

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
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", id=" + id +
                '}';
    }
}
