package com.home.datastore;

import com.home.common.Event;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        Event expectedEvent = calendarDataStore.searchEvent(actualEvent.getTitle());

        assertEquals(expectedEvent, actualEvent);

        // verify mock expectations

    }

    @Test
    public void testCreateEvent() throws Exception {
        // initialize variable inputs
        Event actualEvent = null;

        // initialize mocks

        // initialize class to test
        CalendarDataStore calendarDataStore = new CalendarDataStoreImpl();

        // invoke method on class to test
        actualEvent = calendarDataStore.createEvent("Event", Arrays.asList("mail1@gmail.com", "mail2@gmail.com", "mail3@gmail.com"));
        calendarDataStore.addEvent(actualEvent);

        // assert return value
        Event expectedEvent = calendarDataStore.searchEvent(actualEvent.getTitle());

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
        Event expectedEvent = calendarDataStore.searchEvent(actualEvent.getTitle());

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
}