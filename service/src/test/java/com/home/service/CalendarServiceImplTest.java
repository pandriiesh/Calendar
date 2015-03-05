package com.home.service;

import com.home.common.Event;
import com.home.common.EventInterface;
import com.home.common.Person;
import com.home.datastore.CalendarDataStore;
import com.home.datastore.PersonDataStore;
import com.home.datastore.PersonDataStoreImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CalendarServiceImplTest {

    @Test
    public void testAddEvent() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event.Builder().title("Event").build();

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);
        PersonDataStore personDataStore = mock(PersonDataStore.class);

        doThrow(new RuntimeException("Void method testing")).when(calendarDataStore).addEvent(actualEvent);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore, personDataStore);

        // invoke method on class to test
        // assert return value

        try {
            calendarService.addEvent(actualEvent);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Void method testing");
        }


        // verify mock expectations
        verify(calendarDataStore).addEvent(actualEvent);

    }

    @Test
    public void testCreateEvent() throws Exception {

        // initialize variable inputs
        String title = "Event";
        List<String> attendersEmails = Arrays.asList("email1", "email2");
        Event actualEvent = new Event.Builder().title(title).attendersEmails(attendersEmails).build();

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);
        PersonDataStore personDataStore = mock(PersonDataStore.class);

        doThrow(new RuntimeException("Void method testing")).when(calendarDataStore).createEvent(title, attendersEmails);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore, personDataStore);

        // invoke method on class to test
        // assert return value

        try {
            calendarService.createEvent(title, attendersEmails);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Void method testing");
        }

        // verify mock expectations
        verify(calendarDataStore).createEvent(title, attendersEmails);
    }

    @Test
    public void testSearchEvent() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event.Builder().title("Event").build();

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);
        PersonDataStore personDataStore = mock(PersonDataStore.class);

        when(calendarDataStore.searchEvent(actualEvent.getTitle())).thenReturn(actualEvent);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore, personDataStore);

        // invoke method on class to test
        EventInterface expectedValue = calendarService.searchEvent(actualEvent.getTitle());

        // assert return value
        assertEquals(expectedValue.getTitle(), actualEvent.getTitle());

        // verify mock expectations
        verify(calendarDataStore).searchEvent(actualEvent.getTitle());
    }

    @Test
    public void testRemoveEvent() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event.Builder().title("Event").build();

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);
        PersonDataStore personDataStore = mock(PersonDataStore.class);

        doThrow(new RuntimeException("Void method testing")).when(calendarDataStore).removeEvent(actualEvent);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore, personDataStore);

        // invoke method on class to test
        // assert return value

        try {
            calendarService.removeEvent(actualEvent);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Void method testing");
        }

        // verify mock expectations
        verify(calendarDataStore).removeEvent(actualEvent);

    }

    @Test
    public void testFindPerson() throws Exception {

        // initialize variable inputs
        Person expectedPerson = new Person();
        expectedPerson.setLogin("personLogin");

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);
        PersonDataStore personDataStore = mock(PersonDataStore.class);

        when(personDataStore.findPerson("personLogin")).thenReturn(expectedPerson);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore, personDataStore);

        // invoke method on class to test
        Person person = calendarService.findPerson("personLogin");

        // assert return value
        assertEquals(expectedPerson, person);

        // verify mock expectations
        verify(personDataStore).findPerson("personLogin");

    }

    @Test
    public void testRegisterPerson() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin("personLogin");

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);
        PersonDataStore personDataStore = mock(PersonDataStore.class);

        RuntimeException tobeThrown = new RuntimeException("testRegisterPerson method");
        doThrow(tobeThrown).when(personDataStore).registerPerson(person);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore, personDataStore);

        // invoke method on class to test
        try {
            calendarService.registerPerson(person);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), tobeThrown.getMessage());
        }

        // assert return value

        // verify mock expectations
        verify(personDataStore).registerPerson(person);
    }

    @Test
    public void testRemovePerson() throws Exception {

        // initialize variable inputs
        Person person = new Person();

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);
        PersonDataStore personDataStore = mock(PersonDataStore.class);

        RuntimeException tobeThrown = new RuntimeException("testRemovePerson method");
        doThrow(tobeThrown).when(personDataStore).removePerson(person);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore, personDataStore);

        // invoke method on class to test
        calendarService.registerPerson(person);

        try {
            calendarService.removePerson(person);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), tobeThrown.getMessage());
        }

        // assert return value

        // verify mock expectations
        verify(personDataStore).removePerson(person);
    }

    @Test
    public void testCheckIfPersonIsFreeAtCertainTime() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin("personLogin");
        Date startTime = new Date(new Date().getTime()-3600000);
        Date endTime = new Date(new Date().getTime()+3600000);
        EventInterface event = new Event.Builder().startTime(startTime).endTime(endTime).build();

        person.addEvent(event);

        Date checkedTime = new Date();
        boolean expectedValue = false;

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);
        PersonDataStore personDataStore = mock(PersonDataStore.class);

        when(personDataStore.checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime)).thenReturn(expectedValue);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore, personDataStore);

        // invoke method on class to test
        boolean value = calendarService.checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime);

        // assert return value
        assertEquals(expectedValue, value);

        // verify mock expectations
        verify(personDataStore).checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime);
    }
}