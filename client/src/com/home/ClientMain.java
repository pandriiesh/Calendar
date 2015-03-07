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
        final Date NOW_TIME = new Date();
        final long INTERVAL = 15*60*1000;

        Person person1 = new Person();
        person1.setLogin("person1Login");
        Date event1StartTime = NOW_TIME;
        Date event1EndTime = new Date(NOW_TIME.getTime()+2*60*60*1000 - 60*1000);

        Person person2 = new Person();
        person2.setLogin("person2Login");
        Date event2StartTime = new Date(NOW_TIME.getTime()+60*60*1000);
        Date event2EndTime = new Date(NOW_TIME.getTime()+3*60*60*1000 - 60*1000);

        Person person3 = new Person();
        person3.setLogin("person3Login");
        Date event3StartTime = new Date(NOW_TIME.getTime()+2*60*60*1000);
        Date event3EndTime = new Date(NOW_TIME.getTime() + 4*60*60*1000 - 60*1000);

        EventInterface event1 = new Event.Builder().startTime(event1StartTime).endTime(event1EndTime)
                .attendersLogins(Arrays.asList("person1Login")).build();

        EventInterface event2 = new Event.Builder().startTime(event2StartTime).endTime(event2EndTime)
                .attendersLogins(Arrays.asList("person2Login")).build();

        EventInterface event3 = new Event.Builder().startTime(event3StartTime).endTime(event3EndTime)
                .attendersLogins(Arrays.asList("person3Login")).build();

        Date expectedTime = new Date(NOW_TIME.getTime() + 4 * 60 * 60 * 1000);
        expectedTime.setTime(expectedTime.getTime() / 1000 / 60 * 60 * 1000 + INTERVAL);

        service.registerPerson(person1);
        service.registerPerson(person2);
        service.registerPerson(person3);

        service.addEvent(event1);
        service.addEvent(event2);
        service.addEvent(event3);

        Date calculatedTime = service.findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList("person1Login", "person2Login", "person3Login"));

        logger.info("Checking when three persons are going to be available for event with some duration:");

        logger.info("expectedTime:   " + expectedTime);
        logger.info("calculatedTime: " + calculatedTime);

        logger.info("Verdict: " + expectedTime.equals(calculatedTime));
    }
}
