package com.home.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// This class is Event class clone, and here because i cant bind the Event class attribute from request after
// JSP form filling at RegistrationForm.jsp (because of Builder)
// later will think how to do it
public class EventClone implements Serializable, EventInterface{

    private String title;
    private String description;
    private final UUID id;
    private List<Person> attenders;
    private List<String> attendersLogins;
    private Date startTime;
    private Date endTime;

    public EventClone() {
        id = UUID.randomUUID();
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

    public List<String> getAttendersLogins() {
        return attendersLogins;
    }

    public void setAttendersLogins(List<String> attendersLogins) {
        this.attendersLogins = attendersLogins;
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

        EventClone that = (EventClone) o;

        if (attenders != null ? !attenders.equals(that.attenders) : that.attenders != null) return false;
        if (attendersLogins != null ? !attendersLogins.equals(that.attendersLogins) : that.attendersLogins != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (!id.equals(that.id)) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + id.hashCode();
        result = 31 * result + (attenders != null ? attenders.hashCode() : 0);
        result = 31 * result + (attendersLogins != null ? attendersLogins.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
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
}
