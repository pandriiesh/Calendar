package com.home.service;

import com.home.common.Event;
import com.home.common.Person;
import com.home.datastore.CalendarDataStore;
import com.home.datastore.Period;
import com.home.datastore.PeriodDayOfWeek;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class CalendarServiceImplTest {

    @Test
    public void testAddEvent() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin("personLogin");

        Event actualEvent = new Event();
        actualEvent.setTitle("Event");
        actualEvent.setAttenders(Arrays.asList(person.getLogin()));

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
            fail("Exception should be thrown");

        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Void method testing string");
            verify(calendarDataStore).addEvent(actualEvent);
        }

        // verify mock expectations
        verifyNoMoreInteractions(calendarDataStore);
    }

    @Test
    public void testCreateEvent() throws Exception {

        // initialize variable inputs
        String title = "Event";
        List<String> attendersEmails = Arrays.asList("email1", "email2");

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        doThrow(new RuntimeException("Void method testing")).when(calendarDataStore).createEvent(title, attendersEmails);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        // assert return value

        try {
            calendarService.createEvent(title, attendersEmails);
            fail("Exception should be thrown");

        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Void method testing");
            verify(calendarDataStore).createEvent(title, attendersEmails);
        }

        // verify mock expectations
        verifyNoMoreInteractions(calendarDataStore);
    }

    @Test
    public void testSearchEvent() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event();
        actualEvent.setTitle("Event");

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        when(calendarDataStore.findEventsByTitle(actualEvent.getTitle())).thenReturn(Arrays.asList(actualEvent));

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        List<Event> expectedValue = calendarService.findEventsByTitle(actualEvent.getTitle());

        // assert return value
        assertEquals(expectedValue, Arrays.asList(actualEvent));

        // verify mock expectations
    }

    @Test
    public void testRemoveEventById() throws Exception {

        // initialize variable inputs
        Event actualEvent = new Event();
        actualEvent.setTitle("Event");

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        doThrow(new RuntimeException("Void method testing")).when(calendarDataStore).removeEventById(actualEvent.getId().toString());

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        // assert return value

        try {
            calendarService.removeEventById(actualEvent.getId().toString());
            fail("Exception should be thrown");

        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Void method testing");
            verify(calendarDataStore).removeEventById(actualEvent.getId().toString());
        }

        // verify mock expectations
        verifyNoMoreInteractions(calendarDataStore);

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
            fail("Exception should be thrown");

        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), tobeThrown.getMessage());
            verify(calendarDataStore).registerPerson(person);
        }

        // assert return value

        // verify mock expectations
        verifyNoMoreInteractions(calendarDataStore);
    }

    @Test
    public void testRemovePerson() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin("personLogin");

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        RuntimeException tobeThrown = new RuntimeException("testRemovePerson method");
        doThrow(tobeThrown).when(calendarDataStore).removePerson(person.getLogin());

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        try {
            calendarService.removePerson(person.getLogin());
            fail("Exception should be thrown");

        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), tobeThrown.getMessage());
            verify(calendarDataStore).removePerson(person.getLogin());
        }

        // assert return value

        // verify mock expectations
        verifyNoMoreInteractions(calendarDataStore);
    }

    @Test
    public void testCheckIfPersonIsFreeAtCertainTime() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin("personLogin");
        Date startTime = new Date(new Date().getTime()-3600000);
        Date endTime = new Date(new Date().getTime()+3600000);

        Event event = new Event();
        event.setStartTime(startTime);
        event.setEndTime(endTime);

        person.addEventToPerson(event.getId().toString());

        Date checkedTime = new Date();

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        when(calendarDataStore.checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime)).thenReturn(false);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        // invoke method on class to test
        boolean value = calendarService.checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime);

        // assert return value
        assertEquals(false, value);

        // verify mock expectations
    }

    @Test
    public void testFindBestTimePeriodToCreateEventForUsers() throws Exception {

        // initialize variable inputs

        final long INTERVAL = 15*60*1000;

        Person person1 = new Person();
        person1.setLogin("person1Login");

        Person person2 = new Person();
        person2.setLogin("person2Login");

        Person person3 = new Person();
        person3.setLogin("person3Login");

        final Date NOW_TIME = new Date();
        Date event1EndTime = new Date(NOW_TIME.getTime()+2*60*60*1000 - 60*1000);

        Event event1 = new Event();
        event1.setStartTime(NOW_TIME);
        event1.setEndTime(event1EndTime);
        event1.setAttenders(Arrays.asList(person1.getLogin()));

        Date event2StartTime = new Date(NOW_TIME.getTime()+60*60*1000);
        Date event2EndTime = new Date(NOW_TIME.getTime()+3*60*60*1000 - 60*1000);

        Event event2 = new Event();
        event2.setStartTime(event2StartTime);
        event2.setEndTime(event2EndTime);
        event2.setAttenders(Arrays.asList(person2.getLogin()));

        Date event3StartTime = new Date(NOW_TIME.getTime()+2*60*60*1000);
        Date event3EndTime = new Date(NOW_TIME.getTime() + 4*60*60*1000 - 60*1000);

        Event event3 = new Event();
        event3.setStartTime(event3StartTime);
        event3.setEndTime(event3EndTime);
        event3.setAttenders(Arrays.asList(person3.getLogin()));

        Date expectedTime = new Date(NOW_TIME.getTime() + 4 * 60 * 60 * 1000);
        expectedTime.setTime(expectedTime.getTime() /1000/60 *1000*60 + INTERVAL);

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
        Date calculatedTime = calendarService.findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList("person1Login", "person2Login", "person3Login"));

        // assert return value
        assertEquals(expectedTime, calculatedTime);

        // verify mock expectations
        verify(calendarDataStore).findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList("person1Login", "person2Login", "person3Login"));

        verifyNoMoreInteractions(calendarDataStore);

    }

    @Test
    public void testFindPersonsEventsAtCertainTime() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin("personLogin");

        final Date NOW_TIME = new Date();
        Date checkTime = new Date(NOW_TIME.getTime()+60*60*1000);


        Date eventEndTime = new Date(NOW_TIME.getTime()+2*60*60*1000);

        Event event1 = new Event();
        event1.setTitle("Event1");
        event1.setStartTime(NOW_TIME);
        event1.setEndTime(eventEndTime);
        event1.setAttenders(Arrays.asList(person.getLogin()));

        Event event2 = new Event();
        event2.setTitle("Event2");
        event2.setStartTime(NOW_TIME);
        event2.setEndTime(eventEndTime);
        event2.setAttenders(Arrays.asList(person.getLogin()));

        Event event3 = new Event();
        event3.setTitle("Event3");
        event3.setStartTime(NOW_TIME);
        event3.setEndTime(eventEndTime);
        event3.setAttenders(Arrays.asList(person.getLogin()));

        List<Event> expectedEventList = Arrays.asList(event1, event2, event3);

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
        List<Event> eventList = calendarDataStore.findPersonsEventsAtCertainTime(person.getLogin(), checkTime);

        // assert return value
        assertEquals(expectedEventList, eventList);

        // verify mock expectations
        verify(calendarDataStore).registerPerson(person);

        verify(calendarDataStore).addEvent(event1);
        verify(calendarDataStore).addEvent(event2);
        verify(calendarDataStore).addEvent(event3);

        verify(calendarDataStore).findPersonsEventsAtCertainTime(person.getLogin(), checkTime);
    }


    @Test
    public void testAddPeriodicEvent() throws Exception {

        // initialize variable inputs
        final int quantity = 10;

        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event event = new Event();
        event.setStartTime(new Date());
        event.setTitle(UUID.randomUUID().toString());
        event.setAttenders(Arrays.asList(person.getLogin()));

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        doThrow(new RuntimeException("Must be thrown")).when(calendarDataStore).addPeriodicEvent(event, Period.YEAR, quantity);

        // invoke method on class to test
        try {
            calendarService.addPeriodicEvent(event, Period.YEAR, quantity);
            fail("Exception must be thrown");

        } catch (RuntimeException e) {
            assertEquals("Must be thrown", e.getMessage());
        }

        // assert return value

        // verify mock expectations
        verify(calendarDataStore).addPeriodicEvent(event, Period.YEAR, quantity);
        verifyNoMoreInteractions(calendarDataStore);
    }


    @Test
    public void testAddPeriodicEventWithCertainDays() throws Exception {

        // initialize variable inputs
        final int quantity = 10;

        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event event = new Event();
        event.setStartTime(new Date());
        event.setTitle(UUID.randomUUID().toString());
        event.setAttenders(Arrays.asList(person.getLogin()));

        List<PeriodDayOfWeek> daysList = Arrays.asList(PeriodDayOfWeek.TUESDAY, PeriodDayOfWeek.FRIDAY);

        // initialize mocks
        CalendarDataStore calendarDataStore = mock(CalendarDataStore.class);

        // initialize class to test
        CalendarService calendarService = new CalendarServiceImpl(calendarDataStore);

        doThrow(new RuntimeException("Must be thrown")).when(calendarDataStore).addPeriodicEvent(event, daysList, quantity);

        // invoke method on class to test
        try {
            calendarService.addPeriodicEvent(event, daysList, quantity);
            fail("Exception must be thrown");

        } catch (RuntimeException e) {
            assertEquals("Must be thrown", e.getMessage());
        }

        // assert return value

        // verify mock expectations
        verify(calendarDataStore).addPeriodicEvent(event, daysList, quantity);
        verifyNoMoreInteractions(calendarDataStore);
    }
}