package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventInterface;

import java.util.List;
import java.util.Map;

public interface CalendarDataStore {

    void addEvent(EventInterface event);

    void removeEvent(EventInterface event);

    EventInterface createEvent(String title, List<String> attendersEmails);

    EventInterface searchEvent(String title);

    Map<String, EventInterface> getEventStore();

}
