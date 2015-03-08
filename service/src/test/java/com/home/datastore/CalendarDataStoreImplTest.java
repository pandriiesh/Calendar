package com.home.datastore;

import com.home.common.Event;
import com.home.common.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CalendarDataStoreImplTest {

    @Test
    public void testAddEvent() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event();
        actualEvent.setTitle("Event");

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.addEvent(actualEvent);

        // assert return value
        List<Event> expectedEvents = calendarDataStore.searchEvent(actualEvent.getTitle());

        assertEquals(expectedEvents, Arrays.asList(actualEvent));

        // verify mock expectations

    }

    @Test
    public void testCreateEvent() throws Exception {
        // initialize variable inputs

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        Event actualEvent = calendarDataStore.createEvent("Event",
                Arrays.asList("mail1@gmail.com", "mail2@gmail.com", "mail3@gmail.com"));
        calendarDataStore.addEvent(actualEvent);

        List<Event> expectedEvents = calendarDataStore.searchEvent(actualEvent.getTitle());

        // assert return value

        assertEquals(expectedEvents, Arrays.asList(actualEvent));

        // verify mock expectations


    }

    @Test
    public void testSearchEvent() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event();
        actualEvent.setTitle("Event");

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.addEvent(actualEvent);
        List<Event> expectedEvents = calendarDataStore.searchEvent(actualEvent.getTitle());

        // assert return value
        assertEquals(expectedEvents, Arrays.asList(actualEvent));

        // verify mock expectations

    }

    @Test
    public void testRemoveEvent() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event();
        actualEvent.setTitle("Event");

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.addEvent(actualEvent);
        calendarDataStore.removeEvent(actualEvent);

        // assert return value
        assertEquals(new ArrayList(), calendarDataStore.searchEvent(actualEvent.getTitle()));

        // verify mock expectations
    }

    @Test
    public void testFindPerson() throws Exception {
        // initialize variable inputs
        Person person = new Person();

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        Person expectedPerson = calendarDataStore.findPerson(person.getLogin());

        // assert return value
        assertEquals(person, expectedPerson);

        // verify mock expectations

    }

    @Test
    public void testRegisterPerson() throws Exception {
        // initialize variable inputs
        Person person = new Person();

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);

        // assert return value
        assertEquals(person, calendarDataStore.findPerson(person.getLogin()));

        // verify mock expectations
    }

    @Test
    public void testRemovePerson() throws Exception {
        // initialize variable inputs
        Person person = new Person();

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        calendarDataStore.removePerson(person);
        Person expectedPerson = calendarDataStore.findPerson(person.getLogin());

        // assert return value
        assertNull(expectedPerson);

        // verify mock expectations
    }

    @Test
    public void testCheckIfPersonIsFreeAtCertainTime() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        Date startTime = new Date(new Date().getTime() - 3600000);
        Date endTime = new Date(new Date().getTime() + 3600000);
        Event event = new Event();
        event.setStartTime(startTime);
        event.setEndTime(endTime);

        person.addEventToPerson(event);

        Date checkedTime = new Date();

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();
        calendarDataStore.registerPerson(person);

        // invoke method on class to test
        boolean checked = calendarDataStore.checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime);

        // assert return value
        assertFalse(checked);

        // verify mock expectations

    }

    @Test
    public void testFindBestTimePeriodToCreateEventForUsers() throws Exception {

        // initialize variable inputs

        final Date NOW_TIME = new Date();
        final long INTERVAL = 15*60*1000;

        Person person1 = new Person();
        person1.setLogin("person1Login");
        Date event1EndTime = new Date(NOW_TIME.getTime()+2*60*60*1000 - 60*1000);

        Person person2 = new Person();
        person2.setLogin("person2Login");
        Date event2StartTime = new Date(NOW_TIME.getTime()+60*60*1000);
        Date event2EndTime = new Date(NOW_TIME.getTime()+3*60*60*1000 - 60*1000);

        Person person3 = new Person();
        person3.setLogin("person3Login");
        Date event3StartTime = new Date(NOW_TIME.getTime()+2*60*60*1000);
        Date event3EndTime = new Date(NOW_TIME.getTime() + 4*60*60*1000 - 60*1000);

        Event event1 = new Event();
        event1.setStartTime(NOW_TIME);
        event1.setEndTime(event1EndTime);
        event1.setAttenders(Arrays.asList(person1));

        Event event2 = new Event();
        event2.setStartTime(event2StartTime);
        event2.setEndTime(event2EndTime);
        event2.setAttenders(Arrays.asList(person2));

        Event event3 = new Event();
        event3.setStartTime(event3StartTime);
        event3.setEndTime(event3EndTime);
        event3.setAttenders(Arrays.asList(person3));

        Date expectedTime = new Date(NOW_TIME.getTime() + 4 * 60 * 60 * 1000);
        expectedTime.setTime(expectedTime.getTime()/1000/60*60*1000 + INTERVAL);

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        person1.addEventToPerson(event1);
        person2.addEventToPerson(event2);
        person3.addEventToPerson(event3);

        calendarDataStore.registerPerson(person1);
        calendarDataStore.registerPerson(person2);
        calendarDataStore.registerPerson(person3);

        calendarDataStore.addEvent(event1);
        calendarDataStore.addEvent(event2);
        calendarDataStore.addEvent(event3);

        // invoke method on class to test
        Date calculatedTime = calendarDataStore.findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList("person1Login", "person2Login", "person3Login"));

        // assert return value
        assertEquals(expectedTime, calculatedTime);

        // verify mock expectations

    }

    @Test
    public void testFindPersonsEventsAtCertainTime() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        final Date NOW_TIME = new Date();
        Date checkTime = new Date(NOW_TIME.getTime()+60*60*1000);

        person.setLogin("personLogin");

        Date eventEndTime = new Date(NOW_TIME.getTime()+2*60*60*1000);

        Event event1 = new Event();
        event1.setStartTime(NOW_TIME);
        event1.setEndTime(eventEndTime);
        event1.setAttenders(Arrays.asList(person));

        Event event2 = new Event();
        event2.setStartTime(NOW_TIME);
        event2.setEndTime(eventEndTime);
        event2.setAttenders(Arrays.asList(person));

        Event event3 = new Event();
        event3.setStartTime(NOW_TIME);
        event3.setEndTime(eventEndTime);
        event3.setAttenders(Arrays.asList(person));

        List<Event> expectedEventList = Arrays.asList(event1, event2, event3);
        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        person.addEventToPerson(event1);
        person.addEventToPerson(event2);
        person.addEventToPerson(event3);

        calendarDataStore.registerPerson(person);

        calendarDataStore.addEvent(event1);
        calendarDataStore.addEvent(event2);
        calendarDataStore.addEvent(event3);

        // invoke method on class to test
        List<Event> eventList = calendarDataStore.findPersonsEventsAtCertainTime(person.getLogin(), checkTime);

        // assert return value
        assertEquals(expectedEventList, eventList);

        // verify mock expectations

    }
}