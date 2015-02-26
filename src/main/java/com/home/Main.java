package com.home;

import com.home.common.Event;
import com.home.datastore.CalendarDataStoreImpl;
import com.home.service.CalendarService;
import com.home.service.CalendarServiceImpl;

import java.util.*;

public class Main {

    List<Event> eventList;

    public void main(List<String> titles) {

        eventList = new ArrayList<Event>();

        for (String title : titles) {
            Event event = new Event.Builder().title(title).build();
            eventList.add(event);
        }
    }

    public static void main(String[] args) {


        CalendarService calendarService = new CalendarServiceImpl(new CalendarDataStoreImpl());

        Event meeting = new Event.Builder()
                .title("Drinking beer")
                .description("We go to pub and drink some beer")
                .attendersEmails(Arrays.asList("Ivan", "Pavel", "Anton"))
                .startDate(new Date())
                .endDate(new Date())
                .build();

        calendarService.addEvent(meeting);

        System.out.println(calendarService.searchEvent(meeting.getTitle()).toString());

    }

}
