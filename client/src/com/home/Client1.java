package com.home;

import com.home.common.Event;
import com.home.common.Person;
import com.home.service.CalendarService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.*;

public class Client1 {

    private final Logger logger = Logger.getLogger(Client1.class);

    private final ApplicationContext context = new ClassPathXmlApplicationContext("clientApplicationContext.xml");

    private final CalendarService service = (CalendarService) context.getBean("calendarService");

    public static void main(String[] args) throws RemoteException {

        Client1 client = new Client1();

        try {
            client.checkIfPersonFreeAtCertainTime();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            client.findBestTimeForEvent();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void checkIfPersonFreeAtCertainTime() throws RemoteException {

        Date startTime = new Date(new Date().getTime()-3600000);
        Date endTime = new Date(new Date().getTime()+3600000);

        Person pavlo = new Person();
        pavlo.setLogin("pavloLogin");
        pavlo.setPassword("pavloPassword");
        pavlo.setPersonName("Pavlo");
        pavlo.setPersonEmail("pavlo@gmail.com");

        service.registerPerson(pavlo);

        Event beerFest = service.createEvent("Beerfest", Arrays.asList("pavloLogin"));
        beerFest.setDescription("Go to pub and drink beer.");
        beerFest.setStartTime(startTime);
        beerFest.setEndTime(endTime);

        service.addEvent(beerFest);

        logger.info("All users:");
        for (Person person : service.findPersonsAlike("")) {
            logger.info(person.toString());
        }

        logger.info("All events:");
        for (Event event : service.findEventsByTitle("")) {
            logger.info(event.toString());
        }


        Date certainTime = new Date();
        Date certainTime2 = new Date(new Date().getTime()+4000000);

        logger.info("Checking if Pablo free at certain time("+certainTime+"): " + service.checkIfPersonIsFreeAtCertainTime("pavloLogin", certainTime));
        logger.info("Checking if Pablo free at certain time("+certainTime2+"): " + service.checkIfPersonIsFreeAtCertainTime("pavloLogin", certainTime2));

        try {
            Thread.sleep(1000);
            service.removePerson(pavlo.getLogin());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private void findBestTimeForEvent() throws RemoteException {

        final Date NOW_TIME = new Date();
        final long INTERVAL = 15*60*1000;


        Person person1 = new Person();
        person1.setLogin("person1Login");

        Person person2 = new Person();
        person2.setLogin("person2Login");

        Person person3 = new Person();
        person3.setLogin("person3Login");

        service.registerPerson(person1);
        service.registerPerson(person2);
        service.registerPerson(person3);


        Event event1 = service.createEvent("event1", Arrays.asList("person1Login"));
        Date event1StartTime = NOW_TIME;
        Date event1EndTime = new Date(NOW_TIME.getTime()+2*60*60*1000 - 60*1000);
        event1.setStartTime(event1StartTime);
        event1.setEndTime(event1EndTime);
        service.addEvent(event1);

        Event event2 = service.createEvent("event2", Arrays.asList("person1Login", "person2Login"));
        Date event2StartTime = new Date(NOW_TIME.getTime()+60*60*1000);
        Date event2EndTime = new Date(NOW_TIME.getTime()+3*60*60*1000 - 60*1000);
        event2.setStartTime(event2StartTime);
        event2.setEndTime(event2EndTime);
        service.addEvent(event2);

        Event event3 = service.createEvent("event3", Arrays.asList("person1Login", "person2Login", "person3Login"));
        Date event3StartTime = new Date(NOW_TIME.getTime()+2*60*60*1000);
        Date event3EndTime = new Date(NOW_TIME.getTime() + 4*60*60*1000 - 60*1000);
        event3.setStartTime(event3StartTime);
        event3.setEndTime(event3EndTime);
        service.addEvent(event3);



        Date expectedTime = new Date(NOW_TIME.getTime() + 4 * 60 * 60 * 1000);
        expectedTime.setTime(expectedTime.getTime() / 1000 / 60 * 60 * 1000 + INTERVAL);

        List<String> loginList = Arrays.asList("person1Login", "person2Login", "person3Login");
        Date calculatedTime = service.findBestTimePeriodToCreateEventForUsers(1, loginList);

        logger.info("Checking when three persons are going to be available for event with some duration:");

        logger.info("expectedTime:   " + expectedTime);
        logger.info("calculatedTime: " + calculatedTime);

        logger.info("Verdict: " + expectedTime.equals(calculatedTime));

        logger.info("Testing finding events by title:");
        logger.info(service.findEventsByTitle("event1").toString());
        logger.info(service.findEventsByTitle("event2").toString());
        logger.info(service.findEventsByTitle("event3").toString());

        logger.info("Testing finding persons by login:");
        logger.info(service.findPerson("person1Login").toString());
        logger.info(service.findPerson("person2Login").toString());
        logger.info(service.findPerson("person3Login").toString());

        logger.info("Removing person1, person2, person3...");

        try {
            Thread.sleep(2000);
            service.removePerson(person1.getLogin());
            Thread.sleep(2000);
            service.removePerson(person2.getLogin());
            Thread.sleep(2000);
            service.removePerson(person3.getLogin());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Removed person1, person2, person3.");

    }




}
