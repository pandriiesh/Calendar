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
import java.util.Map;
import java.util.logging.Logger;

public class ClientMain {

    public static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) throws RemoteException {

        ApplicationContext context = new ClassPathXmlApplicationContext("clientApplicationContext.xml");

        CalendarService service = (CalendarService) context.getBean("calendarService");

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

        logger.info("All users:");
        for (Map.Entry<String, Person> entry : personStore.entrySet()) {
            logger.info(entry.getKey() + ": " + entry.getValue());
        }

        Map<String, EventInterface> dataStore = service.getEventStore();

        logger.info("All events:");
        for (Map.Entry<String, EventInterface> entry : dataStore.entrySet()) {
            logger.info(entry.getKey() + ": " + entry.getValue());
        }
    }
}
