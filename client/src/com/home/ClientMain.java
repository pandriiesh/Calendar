package com.home;

import com.home.common.Event;
import com.home.common.EventInterface;
import com.home.common.Person;
import com.home.datastore.CalendarDataStoreImpl;
import com.home.service.CalendarService;
import com.home.service.CalendarServiceImpl;
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

        Date startTime = new Date(new Date().getTime()-3600000);
        Date endTime = new Date(new Date().getTime()+3600000);

        EventInterface beerFest = new Event.Builder().title("Beerfest").description("Go to pub and drink beer.")
                .startTime(startTime).endTime(endTime).attendersLogins(Arrays.asList("pabloLogin")).build();

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

        Date certainTime = new Date();
        Date certainTime2 = new Date(new Date().getTime()+4000000);
        logger.info("Checking if Pablo free at certain time("+certainTime+"): " + service.checkIfPersonIsFreeAtCertainTime("pabloLogin", certainTime));
        logger.info("Checking if Pablo free at certain time("+certainTime2+"): " + service.checkIfPersonIsFreeAtCertainTime("pabloLogin", certainTime2));




        //test findBestTimePeriodToCreateEventForUsers method
        Person person1 = new Person();
        person1.setLogin("person1Login");
        Date startTime1 = new Date();
        Date endTime1 = new Date(new Date().getTime()+3600000);

        EventInterface event1 = new Event.Builder().startTime(startTime1).endTime(endTime1)
                .attendersLogins(Arrays.asList("person1Login")).build();

        Person person2 = new Person();
        person2.setLogin("person2Login");
        Date startTime2 = new Date(new Date().getTime()+4000000);
        Date endTime2 = new Date(new Date().getTime()+7600000);

        EventInterface event2 = new Event.Builder().startTime(startTime2).endTime(endTime2)
                .attendersLogins(Arrays.asList("person2Login")).build();


        Person person3 = new Person();
        person3.setLogin("person3Login");
        Date startTime3 = new Date(new Date().getTime()+8000000);
        Date endTime3 = new Date(new Date().getTime()+9800000);

        EventInterface event3 = new Event.Builder().startTime(startTime3).endTime(endTime3)
                .attendersLogins(Arrays.asList("person3Login")).build();



        Date expectedTime = new Date(1435682800000L + 900000);

        CalendarService calendarService = (CalendarService) context.getBean("calendarService");

        calendarService.registerPerson(person1);
        calendarService.registerPerson(person2);
        calendarService.registerPerson(person3);

        calendarService.addEvent(event1);
        calendarService.addEvent(event2);
        calendarService.addEvent(event3);

        Date value = calendarService.findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList("person1Login", "person2Login", "person3Login"));

        System.out.println(expectedTime);
        System.out.println(value);
    }
}
