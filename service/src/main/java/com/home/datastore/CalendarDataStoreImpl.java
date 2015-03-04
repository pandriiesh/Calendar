package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarDataStoreImpl implements CalendarDataStore {

    private final Map<String, EventInterface> eventMap = new HashMap<String, EventInterface>();

    @Override
    public void addEvent(EventInterface event) {
        eventMap.put(event.getTitle(), event);
    }

    @Override
    public void removeEvent(EventInterface event) {
        eventMap.remove(event.getTitle());
    }

    @Override
    public EventInterface createEvent(String title, List<String> attendersEmails) {
        EventInterface event = new Event.Builder().title(title).attendersEmails(attendersEmails).build();
        return event;
    }

    @Override
    public EventInterface searchEvent(String title) {
        EventInterface event = eventMap.get(title);
        return event;
    }
}
