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

        final Date NOW_TIME = new Date();
        final long INTERVAL = 15*60*1000;

        Person person1 = new Person();
        person1.setLogin("person1Login");

        Person person2 = new Person();
        person2.setLogin("person2Login");

        Person person3 = new Person();
        person3.setLogin("person3Login");


        Date event1StartTime = NOW_TIME;
        Date event1EndTime = new Date(NOW_TIME.getTime()+2*60*60*1000 - 60*1000);

        EventInterface event1 = new Event.Builder().startTime(event1StartTime).endTime(event1EndTime)
                .attendersLogins(Arrays.asList("person1Login")).build();

        Date event2StartTime = new Date(NOW_TIME.getTime()+60*60*1000);
        Date event2EndTime = new Date(NOW_TIME.getTime()+3*60*60*1000 - 60*1000);

        EventInterface event2 = new Event.Builder().startTime(event2StartTime).endTime(event2EndTime)
                .attendersLogins(Arrays.asList("person2Login")).build();

        Date event3StartTime = new Date(NOW_TIME.getTime()+2*60*60*1000);
        Date event3EndTime = new Date(NOW_TIME.getTime() + 4*60*60*1000 - 60*1000);

        EventInterface event3 = new Event.Builder().startTime(event3StartTime).endTime(event3EndTime)
                .attendersLogins(Arrays.asList("person3Login")).build();



        Date expectedTime = new Date(NOW_TIME.getTime() + 4 * 60 * 60 * 1000);
        expectedTime.setTime(expectedTime.getTime()/1000/60*60*1000 + INTERVAL);

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        RuntimeException toBeThrownRegisterPerson1 = new RuntimeException("register person1");
        RuntimeException toBeThrownRegisterPerson2 = new RuntimeException("register person2");
        RuntimeException toBeThrownRegisterPerson3 = new RuntimeException("register person3");

        RuntimeException toBeThrownAddEvent1 = new RuntimeException("add event 1");
        RuntimeException toBeThrownAddEvent2 = new RuntimeException("add event 2");
        RuntimeException toBeThrownAddEvent3 = new RuntimeException("add event 3");

        doThrow(toBeThrownRegisterPerson1).when(calendarDataStore).registerPerson(person1);
        doThrow(toBeThrownRegisterPerson2).when(calendarDataStore).registerPerson(person2);
        doThrow(toBeThrownRegisterPerson3).when(calendarDataStore).registerPerson(person3);

        doThrow(toBeThrownAddEvent1).when(calendarDataStore).addEvent(event1);
        doThrow(toBeThrownAddEvent2).when(calendarDataStore).addEvent(event2);
        doThrow(toBeThrownAddEvent3).when(calendarDataStore).addEvent(event3);

        when(calendarDataStore.findPerson(person1.getLogin())).thenReturn(person1);
        when(calendarDataStore.findPerson(person2.getLogin())).thenReturn(person2);
        when(calendarDataStore.findPerson(person3.getLogin())).thenReturn(person3);

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

        try {
            calendarService.addEvent(event1);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "add event 1");
        }

        try {
            calendarService.addEvent(event2);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "add event 2");
        }

        try {
            calendarService.addEvent(event3);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "add event 3");
        }

        // invoke method on class to test
        Date calculatedTime = calendarService.findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList("person1Login", "person2Login", "person3Login"));

        // assert return value
        assertEquals(expectedTime, calculatedTime);

        // verify mock expectations
        verify(calendarDataStore).registerPerson(person1);
        verify(calendarDataStore).registerPerson(person2);
        verify(calendarDataStore).registerPerson(person3);

        verify(calendarDataStore).addEvent(event1);
        verify(calendarDataStore).addEvent(event2);
        verify(calendarDataStore).addEvent(event3);

        verify(calendarDataStore).findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList("person1Login", "person2Login", "person3Login"));

    }

    @Test
    public void testFindPersonsEventsAtCertainTime() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        final Date NOW_TIME = new Date();
        Date checkTime = new Date(NOW_TIME.getTime()+60*60*1000);

        person.setLogin("personLogin");

        Date eventStartTime = NOW_TIME;
        Date eventEndTime = new Date(NOW_TIME.getTime()+2*60*60*1000);

        EventInterface event1 = new Event.Builder().title("Event1").startTime(eventStartTime).endTime(eventEndTime)
                .attendersLogins(Arrays.asList("personLogin")).build();

        EventInterface event2 = new Event.Builder().title("Event2").startTime(eventStartTime).endTime(eventEndTime)
                .attendersLogins(Arrays.asList("personLogin")).build();

        EventInterface event3 = new Event.Builder().title("Event3").startTime(eventStartTime).endTime(eventEndTime)
                .attendersLogins(Arrays.asList("personLogin")).build();

        List<EventInterface> expectedEventList = Arrays.asList(event1, event2, event3);

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        RuntimeException toBeThrownRegisterPerson = new RuntimeException("register person");

        RuntimeException toBeThrownAddEvent1 = new RuntimeException("add event 1");
        RuntimeException toBeThrownAddEvent2 = new RuntimeException("add event 2");
        RuntimeException toBeThrownAddEvent3 = new RuntimeException("add event 3");

        doThrow(toBeThrownRegisterPerson).when(calendarDataStore).registerPerson(person);

        doThrow(toBeThrownAddEvent1).when(calendarDataStore).addEvent(event1);
        doThrow(toBeThrownAddEvent2).when(calendarDataStore).addEvent(event2);
        doThrow(toBeThrownAddEvent3).when(calendarDataStore).addEvent(event3);

        when(calendarDataStore.findPerson(person.getLogin())).thenReturn(person);

        when(calendarDataStore.findPersonsEventsAtCertainTime(person.getLogin(), checkTime))
                .thenReturn(expectedEventList);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        try {
            calendarDataStore.registerPerson(person);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "register person");
        }

        try {
            calendarService.addEvent(event1);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "add event 1");
        }

        try {
            calendarService.addEvent(event2);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "add event 2");
        }

        try {
            calendarService.addEvent(event3);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "add event 3");
        }

        // invoke method on class to test
        List<EventInterface> eventList = calendarDataStore.findPersonsEventsAtCertainTime(person.getLogin(), checkTime);

        // assert return value
        assertEquals(expectedEventList, eventList);

        // verify mock expectations
        verify(calendarDataStore).registerPerson(person);

        verify(calendarDataStore).addEvent(event1);
        verify(calendarDataStore).addEvent(event2);
        verify(calendarDataStore).addEvent(event3);

        verify(calendarDataStore).findPersonsEventsAtCertainTime(person.getLogin(), checkTime);

    }
}