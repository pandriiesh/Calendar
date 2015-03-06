package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventInterface;
import com.home.common.Person;
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
        Date startTime = new Date(new Date().getTime()-3600000);
        Date endTime = new Date(new Date().getTime()+3600000);
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

}