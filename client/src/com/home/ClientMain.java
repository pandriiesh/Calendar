package com.home;

import com.home.common.Event;
import com.home.common.EventInterface;
import com.home.common.Person;
import com.home.service.CalendarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ClientMain {

    public static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) throws RemoteException {

        ApplicationContext context = new ClassPathXmlApplicationContext("clientApplicationContext.xml");

        CalendarService service = (CalendarService) context.getBean("calendarService");

        String[] reservedCalendarNames = {"New Year", "Meeting10", "code review"};

        for (String name : reservedCalendarNames)
            service.addEvent(new Event.Builder().title(name).description(name + "'s description").build());

        for (String name : reservedCalendarNames)
            logger.info("Created event in data store: " + service.searchEvent(name));


        EventInterface beerFest = new Event.Builder().title("Beerfest").description("Go to pub and drink beer.")
                .startTime(new Date()).endTime(new Date()).attendersLogins(Arrays.asList("pabloLogin")).build();

        Person pablo = new Person();
        pablo.setLogin("pabloLogin");
        pablo.setPassword("pabloPassword");
        pablo.setPersonName("Pablo");
        pablo.setPersonEmail("pablo@gmail.com");

        service.registerPerson(pablo);

        service.addEvent(beerFest);

        Map<String, Person> personStore = service.getPersonStore();

        System.out.println("Users:");

        for (Map.Entry<String, Person> entry : personStore.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        Map<String, EventInterface> dataStore = service.getEventStore();

        System.out.println("Events:");

        for (Map.Entry<String, EventInterface> entry : dataStore.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
