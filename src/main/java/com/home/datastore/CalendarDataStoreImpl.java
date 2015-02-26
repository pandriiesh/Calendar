package com.home.datastore;

import com.home.common.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarDataStoreImpl implements CalendarDataStore {

    private Map<String, Event> eventMap = new HashMap<String, Event>();

    public Map<String, Event> getEventMap() {
        return eventMap;
    }

    @Override
    public void addEvent(Event event) {
        eventMap.put(event.getTitle(), event);
    }

    @Override
    public void removeEvent(Event event) {
        eventMap.remove(event.getTitle());
    }

    @Override
    public Event createEvent(String title, List<String> attendersEmails) {
        Event event = new Event.Builder().title(title).attendersEmails(attendersEmails).build();
        return event;
    }

    @Override
    public Event searchEvent(String title) {
        Event event = eventMap.get(title);
        return event;
    }
}
