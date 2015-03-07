package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventInterface;
import com.home.common.Person;
import com.home.service.CalendarService;
import com.home.service.CalendarServiceImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CalendarDataStoreImplTest {

    @Test
    public void testAddEvent() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event.Builder().title("Event").build();

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.addEvent(actualEvent);

        // assert return value
        EventInterface expectedEvent = calendarDataStore.searchEvent(actualEvent.getTitle());

        assertEquals(expectedEvent, actualEvent);

        // verify mock expectations

    }

    @Test
    public void testCreateEvent() throws Exception {
        // initialize variable inputs
        EventInterface actualEvent = null;

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        actualEvent = calendarDataStore.createEvent("Event", Arrays.asList("mail1@gmail.com", "mail2@gmail.com", "mail3@gmail.com"));
        calendarDataStore.addEvent(actualEvent);

        // assert return value
        EventInterface expectedEvent = calendarDataStore.searchEvent(actualEvent.getTitle());

        assertEquals(expectedEvent, actualEvent);

        // verify mock expectations


    }

    @Test
    public void testSearchEvent() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event.Builder().title("Event").build();

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.addEvent(actualEvent);
        EventInterface expectedEvent = calendarDataStore.searchEvent(actualEvent.getTitle());

        // assert return value
        assertEquals(expectedEvent, actualEvent);

        // verify mock expectations

    }

    @Test
    public void testRemoveEvent() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event.Builder().title("Event").build();

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.addEvent(actualEvent);
        calendarDataStore.removeEvent(actualEvent);

        // assert return value
        assertNull(calendarDataStore.searchEvent(actualEvent.getTitle()));

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
        EventInterface event = new Event.Builder().startTime(startTime).endTime(endTime).build();

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
}