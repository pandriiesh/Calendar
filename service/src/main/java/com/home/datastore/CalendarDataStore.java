package com.home.datastore;

import com.home.common.Event;
import com.home.common.Person;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CalendarDataStore {

    void addEvent(Event event);

    void removeEvent(Event event);

    Event createEvent(String title, List<String> attendersLogins);

    List<Event> searchEvent(String title);

    List<Event> searchEvent(Person person);

    Map<UUID, Event> getEventStore();

    void registerPerson(Person person);

    void removePerson(Person person);

    Person findPerson(String personLogin);

    Map<String, Person> getPersonStore();

    boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date);

    Date findBestTimePeriodToCreateEventForUsers(double durationHours, List<String> personsLogins);

    List<Event> findPersonsEventsAtCertainTime(String personLogin, Date date);
}
