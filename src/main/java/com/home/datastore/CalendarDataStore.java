package com.home.datastore;

import com.home.common.Event;

import java.util.List;

public interface CalendarDataStore {

    //local code review (vtegza): no need for public modifier for methods - public by default @ 3/2/2015
    public void addEvent(Event event);

    public void removeEvent(Event event);

    public Event createEvent(String title, List<String> attendersEmails);

    public Event searchEvent(String title);

}
