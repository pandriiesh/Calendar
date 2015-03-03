package com.home.datastore;

import com.home.common.Event;

import java.util.List;

public interface CalendarDataStore {

    void addEvent(Event event);

    void removeEvent(Event event);

    Event createEvent(String title, List<String> attendersEmails);

    Event searchEvent(String title);

}
