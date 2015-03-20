package com.home.datastore;

import com.home.common.Event;
import com.home.common.Person;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CalendarDataStoreImplTest {

    @Test
    public void testAddEvent() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event actualEvent = new Event();
        actualEvent.setTitle("Event");
        actualEvent.setAttenders(Arrays.asList(person.getLogin()));

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        calendarDataStore.addEvent(actualEvent);

        // assert return value
        List<Event> expectedEvents = calendarDataStore.findEventsByTitle(actualEvent.getTitle());

        assertEquals(expectedEvents, Arrays.asList(actualEvent));

        calendarDataStore.removePerson(person.getLogin());
        // verify mock expectations

    }

    @Test
    public void testCreateEvent() throws Exception {
        // initialize variable inputs
        Person person1 = new Person();
        person1.setLogin(UUID.randomUUID().toString());

        Person person2 = new Person();
        person2.setLogin(UUID.randomUUID().toString());

        Person person3 = new Person();
        person3.setLogin(UUID.randomUUID().toString());

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        calendarDataStore.registerPerson(person1);
        calendarDataStore.registerPerson(person2);
        calendarDataStore.registerPerson(person3);

        // invoke method on class to test
        Event actualEvent = calendarDataStore.createEvent("Event",
                Arrays.asList(person1.getLogin(), person2.getLogin(), person3.getLogin()));

        calendarDataStore.addEvent(actualEvent);

        List<Event> expectedEvents = calendarDataStore.findEventsById(actualEvent.getId().toString());

        // assert return value

        assertEquals(expectedEvents, Arrays.asList(actualEvent));

        // verify mock expectations

        calendarDataStore.removePerson(person1.getLogin());
        calendarDataStore.removePerson(person2.getLogin());
        calendarDataStore.removePerson(person3.getLogin());

    }

    @Test
    public void testFindEventsByTitle() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event actualEvent = new Event();
        actualEvent.setTitle("Event");
        actualEvent.setAttenders(Arrays.asList(person.getLogin()));

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        calendarDataStore.addEvent(actualEvent);

        List<Event> expectedEvents = calendarDataStore.findEventsByTitle(actualEvent.getTitle());

        // assert return value
        assertTrue(expectedEvents.containsAll(Arrays.asList(actualEvent)));

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());

    }

    @Test
    public void testRemoveEventById() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event actualEvent = new Event();
        actualEvent.setTitle("Event");
        actualEvent.setAttenders(Arrays.asList(person.getLogin()));

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        calendarDataStore.addEvent(actualEvent);
        calendarDataStore.removeEventById(actualEvent.getId().toString());

        // assert return value
        assertEquals(new ArrayList(), calendarDataStore.findEventsById(actualEvent.getId().toString()));

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());
    }

    @Test
    public void testFindPerson() throws Exception {
        // initialize variable inputs
        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        Person expectedPerson = calendarDataStore.findPerson(person.getLogin());

        // assert return value
        assertEquals(person, expectedPerson);

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());

    }

    @Test
    public void testRegisterPerson() throws Exception {
        // initialize variable inputs
        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);

        // assert return value
        assertEquals(person, calendarDataStore.findPerson(person.getLogin()));

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());
    }

    @Test
    public void testRemovePerson() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        calendarDataStore.removePerson(person.getLogin());
        Person expectedPerson = calendarDataStore.findPerson(person.getLogin());

        // assert return value
        assertNull(expectedPerson);

        // verify mock expectations
    }

    @Test
    public void testCheckIfPersonIsFreeAtCertainTime() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event event = new Event();
        Date startTime = new Date(new Date().getTime() - 3600000);
        Date endTime = new Date(new Date().getTime() + 3600000);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setAttenders(Arrays.asList(person.getLogin()));

        Date checkedTime = new Date();

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();
        calendarDataStore.registerPerson(person);
        calendarDataStore.addEvent(event);

        // invoke method on class to test
        boolean checked = calendarDataStore.checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime);

        // assert return value
        assertFalse(checked);

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());

    }

    @Test
    public void testFindBestTimePeriodToCreateEventForUsers() throws Exception {

        // initialize variable inputs

        final Date NOW_TIME = new Date();
        final long INTERVAL = 15*60*1000;

        Person person1 = new Person();
        person1.setLogin(UUID.randomUUID().toString());
        Date event1EndTime = new Date(NOW_TIME.getTime()+2*60*60*1000 - 60*1000);

        Person person2 = new Person();
        person2.setLogin(UUID.randomUUID().toString());
        Date event2StartTime = new Date(NOW_TIME.getTime()+60*60*1000);
        Date event2EndTime = new Date(NOW_TIME.getTime()+3*60*60*1000 - 60*1000);

        Person person3 = new Person();
        person3.setLogin(UUID.randomUUID().toString());
        Date event3StartTime = new Date(NOW_TIME.getTime()+2*60*60*1000);
        Date event3EndTime = new Date(NOW_TIME.getTime() + 4*60*60*1000 - 60*1000);

        Event event1 = new Event();
        event1.setStartTime(NOW_TIME);
        event1.setEndTime(event1EndTime);
        event1.setAttenders(Arrays.asList(person1.getLogin()));

        Event event2 = new Event();
        event2.setStartTime(event2StartTime);
        event2.setEndTime(event2EndTime);
        event2.setAttenders(Arrays.asList(person2.getLogin()));

        Event event3 = new Event();
        event3.setStartTime(event3StartTime);
        event3.setEndTime(event3EndTime);
        event3.setAttenders(Arrays.asList(person3.getLogin()));

        Date expectedTime = new Date(NOW_TIME.getTime() + 4 * 60 * 60 * 1000);
        expectedTime.setTime(expectedTime.getTime()/1000/60*60*1000 + INTERVAL);

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        calendarDataStore.registerPerson(person1);
        calendarDataStore.registerPerson(person2);
        calendarDataStore.registerPerson(person3);

        calendarDataStore.addEvent(event1);
        calendarDataStore.addEvent(event2);
        calendarDataStore.addEvent(event3);

        // invoke method on class to test
        Date calculatedTime = calendarDataStore.findBestTimePeriodToCreateEventForUsers(1,
                Arrays.asList(person1.getLogin(), person2.getLogin(), person3.getLogin()));

        // assert return value
        assertEquals(expectedTime, calculatedTime);

        // verify mock expectations
        calendarDataStore.removePerson(person1.getLogin());
        calendarDataStore.removePerson(person2.getLogin());
        calendarDataStore.removePerson(person3.getLogin());

    }

    @Test
    public void testFindPersonsEventsAtCertainTime() throws Exception {

        // initialize variable inputs
        final Date eventStartTime = new Date();
        final Date eventEndTime = new Date(eventStartTime.getTime()+2*60*60*1000);
        final Date checkTime = new Date(eventStartTime.getTime()+60*60*1000);

        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event event1 = new Event();
        event1.setStartTime(eventStartTime);
        event1.setEndTime(eventEndTime);
        event1.setAttenders(Arrays.asList(person.getLogin()));

        Event event2 = new Event();
        event2.setStartTime(eventStartTime);
        event2.setEndTime(eventEndTime);
        event2.setAttenders(Arrays.asList(person.getLogin()));

        Event event3 = new Event();
        event3.setStartTime(eventStartTime);
        event3.setEndTime(eventEndTime);
        event3.setAttenders(Arrays.asList(person.getLogin()));

        List<Event> expectedEventList = Arrays.asList(event1, event2, event3);
        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        calendarDataStore.registerPerson(person);

        calendarDataStore.addEvent(event1);
        calendarDataStore.addEvent(event2);
        calendarDataStore.addEvent(event3);

        // invoke method on class to test
        List<Event> eventList = calendarDataStore.findPersonsEventsAtCertainTime(person.getLogin(), checkTime);

        // assert return value
        assertEquals(expectedEventList, eventList);

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());
    }

    @Test
    public void testAddYearlyPeriodicEvent() throws Exception {

        // initialize variable inputs
        final int quantity = 10;

        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event event = new Event();
        event.setStartTime(new Date());
        event.setTitle(UUID.randomUUID().toString());
        event.setAttenders(Arrays.asList(person.getLogin()));

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        calendarDataStore.addPeriodicEvent(event, Period.YEAR, quantity);

        List<Event> returnedValue = calendarDataStore.findEventsByTitle(event.getTitle());

        // assert return value
        assertEquals(quantity+1, returnedValue.size());

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());
    }

    @Test
    public void testAddMonthlyPeriodicEvent() throws Exception {

        // initialize variable inputs
        final int quantity = 10;

        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event event = new Event();
        event.setStartTime(new Date());
        event.setTitle(UUID.randomUUID().toString());
        event.setAttenders(Arrays.asList(person.getLogin()));

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        calendarDataStore.addPeriodicEvent(event, Period.MONTH, quantity);

        List<Event> returnedValue = calendarDataStore.findEventsByTitle(event.getTitle());

        // assert return value
        assertEquals(quantity+1, returnedValue.size());

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());
    }

    @Test
    public void testAddWeeklyPeriodicEvent() throws Exception {

        // initialize variable inputs
        final int quantity = 10;

        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event event = new Event();
        event.setStartTime(new Date());
        event.setTitle(UUID.randomUUID().toString());
        event.setAttenders(Arrays.asList(person.getLogin()));

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        calendarDataStore.addPeriodicEvent(event, Period.WEEK, quantity);

        List<Event> returnedValue = calendarDataStore.findEventsByTitle(event.getTitle());

        // assert return value
        assertEquals(quantity+1, returnedValue.size());

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());
    }

    @Test
    public void testAddDailyPeriodicEvent() throws Exception {

        // initialize variable inputs
        final int quantity = 10;

        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event event = new Event();
        event.setStartTime(new Date());
        event.setTitle(UUID.randomUUID().toString());
        event.setAttenders(Arrays.asList(person.getLogin()));

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        calendarDataStore.addPeriodicEvent(event, Period.DAY, quantity);

        List<Event> returnedValue = calendarDataStore.findEventsByTitle(event.getTitle());

        // assert return value
        assertEquals(quantity + 1, returnedValue.size());

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());
    }

    @Test
    public void testAddConcreteDaysPeriodicEvent() throws Exception {

        // initialize variable inputs
        final int quantity = 10;

        Person person = new Person();
        person.setLogin(UUID.randomUUID().toString());

        Event event = new Event();
        event.setStartTime(new Date());
        event.setTitle(UUID.randomUUID().toString());
        event.setAttenders(Arrays.asList(person.getLogin()));

        List<PeriodDayOfWeek> daysList = Arrays.asList(PeriodDayOfWeek.TUESDAY, PeriodDayOfWeek.THURSDAY);

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        calendarDataStore.registerPerson(person);
        calendarDataStore.addPeriodicEvent(event, daysList, quantity);

        List<Event> returnedValue = calendarDataStore.findEventsByTitle(event.getTitle());

        // assert return value
        assertEquals(quantity * daysList.size() + 1, returnedValue.size());

        // verify mock expectations
        calendarDataStore.removePerson(person.getLogin());
    }

}