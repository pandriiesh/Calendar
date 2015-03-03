package com.home.datastore;

import com.home.common.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarDataStoreImpl implements CalendarDataStore {

    private final Map<String, Event> eventMap = new HashMap<String, Event>();

    //local code review (vtegza): not used @ 3/2/2015
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
