package com.home.common;

import java.io.Serializable;
import java.util.*;

public class Event implements Serializable {

    private String title;
    private String description;
    private UUID id;
    private List<String> attenders;
    private Date startTime;
    private Date endTime;

    public Event() {
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

    public void setId(UUID id) {
        this.id = id;
    }

    public List<String> getAttenders() {
        return attenders;
    }

    public void setAttenders(List<String> attenders) {
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
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", id=" + id +
                ", attenders='" + attenders + '\'' +
                '}';
    }
}
