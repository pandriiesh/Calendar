package com.home.common;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface EventInterface {

    public List<Person> getAttenders();

    public List<String> getAttendersLogins();

    public String getDescription();

    public Date getEndTime();

    public UUID getId();

    public Date getStartTime();

    public String getTitle();
}
