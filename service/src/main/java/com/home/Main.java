package com.home;

import com.home.common.Event;
import com.home.service.CalendarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {

    private List<Event> eventList;

    //local code review (vtegza): not used @ 3/2/2015
    public void main(List<String> titles) {

        eventList = new ArrayList<Event>();

        for (String title : titles) {
            Event event = new Event.Builder().title(title).build();
            eventList.add(event);
        }
    }

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        CalendarService calendarService = context.getBean("calendarServiceImpl", CalendarService.class);

        Event meeting = new Event.Builder()
                .title("Drinking beer")
                .description("We go to pub and drink some beer")
                .attendersEmails(Arrays.asList("Ivan", "Pavel", "Anton"))
                .startDate(new Date())
                .endDate(new Date())
                .build();

        calendarService.addEvent(meeting);

        System.out.println(calendarService.searchEvent(meeting.getTitle()));

    }
}
