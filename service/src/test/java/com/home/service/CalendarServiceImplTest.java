package com.home.service;

import com.home.common.Event;
import com.home.common.EventInterface;
import com.home.common.Person;
import com.home.datastore.CalendarDataStore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CalendarServiceImplTest {

    @Test
    public void testAddEvent() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin("personLogin");

        Event actualEvent = new Event.Builder().title("Event").attendersLogins(Arrays.asList("personLogin")).build();

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        when(calendarDataStore.findPerson("personLogin")).thenReturn(person);
        doThrow(new RuntimeException("Void method testing string")).when(calendarDataStore).addEvent(actualEvent);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        // assert return value

        try {
            calendarService.addEvent(actualEvent);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Void method testing string");
        }

        // verify mock expectations
        verify(calendarDataStore).findPerson("personLogin");
        verify(calendarDataStore).addEvent(actualEvent);

    }

    @Test
    public void testCreateEvent() throws Exception {

        // initialize variable inputs
        String title = "Event";
        List<String> attendersEmails = Arrays.asList("email1", "email2");
        Event actualEvent = new Event.Builder().title(title).attendersLogins(attendersEmails).build();

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        doThrow(new RuntimeException("Void method testing")).when(calendarDataStore).createEvent(title, attendersEmails);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

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

        when(calendarDataStore.searchEvent(actualEvent.getTitle())).thenReturn(actualEvent);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

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

        doThrow(new RuntimeException("Void method testing")).when(calendarDataStore).removeEvent(actualEvent);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

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

        when(calendarDataStore.findPerson("personLogin")).thenReturn(expectedPerson);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        Person person = calendarService.findPerson("personLogin");

        // assert return value
        assertEquals(expectedPerson, person);

        // verify mock expectations
        verify(calendarDataStore).findPerson("personLogin");

    }

    @Test
    public void testRegisterPerson() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin("personLogin");

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        RuntimeException tobeThrown = new RuntimeException("testRegisterPerson method");
        doThrow(tobeThrown).when(calendarDataStore).registerPerson(person);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        try {
            calendarService.registerPerson(person);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), tobeThrown.getMessage());
        }

        // assert return value

        // verify mock expectations
        verify(calendarDataStore).registerPerson(person);
    }

    @Test
    public void testRemovePerson() throws Exception {

        // initialize variable inputs
        Person person = new Person();

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        RuntimeException tobeThrown = new RuntimeException("testRemovePerson method");
        doThrow(tobeThrown).when(calendarDataStore).removePerson(person);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        calendarService.registerPerson(person);

        try {
            calendarService.removePerson(person);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), tobeThrown.getMessage());
        }

        // assert return value

        // verify mock expectations
        verify(calendarDataStore).removePerson(person);
    }

    @Test
    public void testCheckIfPersonIsFreeAtCertainTime() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin("personLogin");
        Date startTime = new Date(new Date().getTime()-3600000);
        Date endTime = new Date(new Date().getTime()+3600000);
        EventInterface event = new Event.Builder().startTime(startTime).endTime(endTime).build();

        person.addEventToPerson(event);

        Date checkedTime = new Date();
        boolean expectedValue = false;

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        when(calendarDataStore.checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime)).thenReturn(expectedValue);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        boolean value = calendarService.checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime);

        // assert return value
        assertEquals(expectedValue, value);

        // verify mock expectations
        verify(calendarDataStore).checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime);
    }

    @Test
    public void testFindBestTimePeriodToCreateEventForUsers() throws Exception {

        // initialize variable inputs
        Person person1 = new Person();
        person1.setLogin("person1Login");
        long time = 1435673000000L;
        Date startTime = new Date(time);
        Date endTime = new Date(new Date().getTime()+3600000);

        EventInterface event1 = new Event.Builder().startTime(startTime).endTime(endTime)
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


        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        RuntimeException toBeThrownRegisterPerson1 = new RuntimeException("register person1");
        RuntimeException toBeThrownRegisterPerson2 = new RuntimeException("register person2");
        RuntimeException toBeThrownRegisterPerson3 = new RuntimeException("register person3");

        doThrow(toBeThrownRegisterPerson1).when(calendarDataStore).registerPerson(person1);
        doThrow(toBeThrownRegisterPerson2).when(calendarDataStore).registerPerson(person2);
        doThrow(toBeThrownRegisterPerson3).when(calendarDataStore).registerPerson(person3);

        Date expectedTime = new Date(1435682800000L + 900000);
        when(calendarDataStore.findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList("person1Login", "person2Login", "person3Login")))
                .thenReturn(expectedTime);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        try {
            calendarService.registerPerson(person1);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "register person1");
        }

        try {
            calendarService.registerPerson(person2);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "register person2");
        }

        try {
            calendarService.registerPerson(person3);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "register person3");
        }

        Date value = calendarService.findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList("person1Login", "person2Login", "person3Login"));

        // assert return value
        assertEquals(expectedTime, value);

        // verify mock expectations
        verify(calendarDataStore).registerPerson(person1);
        verify(calendarDataStore).registerPerson(person2);
        verify(calendarDataStore).registerPerson(person3);
        verify(calendarDataStore).findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList("person1Login", "person2Login", "person3Login"));

    }
}