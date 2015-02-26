package com.home.service;

import com.home.common.Event;
import com.home.datastore.CalendarDataStore;
import com.home.datastore.CalendarDataStoreImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarServiceImpl implements CalendarService {

    private CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

    private Map<String, Event> eventMap = new HashMap<String, Event>();

    public CalendarServiceImpl(CalendarDataStore calendarDataStore) {
        this.calendarDataStore = calendarDataStore;
    }

    public Map<String, Event> getEventMap() {
        return eventMap;
    }

    @Override
    public void addEvent(Event event) {
        calendarDataStore.addEvent(event);
    }

    @Override
    public Event createEvent(String title, List<String> emails) {
        return calendarDataStore.createEvent(title, emails);
    }

    @Override
    public Event searchEvent(String title) {
        return calendarDataStore.searchEvent(title);
    }

    @Override
    public void removeEvent(Event event) {
        calendarDataStore.removeEvent(event);
    }

}
