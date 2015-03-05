package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarDataStoreImpl implements CalendarDataStore {

    private final Map<String, EventInterface> eventStore = new HashMap<String, EventInterface>();

    @Override
    public Map<String, EventInterface> getEventStore() {
        return eventStore;
    }

    @Override
    public void addEvent(EventInterface event) {
        eventStore.put(event.getTitle(), event);
    }

    @Override
    public void removeEvent(EventInterface event) {
        eventStore.remove(event.getTitle());
    }

    @Override
    public EventInterface createEvent(String title, List<String> attendersEmails) {
        EventInterface event = new Event.Builder().title(title).attendersEmails(attendersEmails).build();
        return event;
    }

    @Override
    public EventInterface searchEvent(String title) {
        EventInterface event = eventStore.get(title);
        return event;
    }
}
