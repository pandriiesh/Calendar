package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventInterface;

import java.util.List;

public interface CalendarDataStore {

    void addEvent(EventInterface event);

    void removeEvent(EventInterface event);

    EventInterface createEvent(String title, List<String> attendersEmails);

    EventInterface searchEvent(String title);

}
