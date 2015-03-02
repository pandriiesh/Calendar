package com.home.service;

import com.home.common.Event;

import java.util.List;

public interface CalendarService {

    //local code review (vtegza): no need for public here @ 3/2/2015
    public void addEvent(Event event);

    public void removeEvent(Event event);

    public Event createEvent(String title, List<String> attendersEmails);

    public Event searchEvent(String title);
}
