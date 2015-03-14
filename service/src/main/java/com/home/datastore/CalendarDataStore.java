package com.home.datastore;

import com.home.common.Event;
import com.home.common.Person;

import javax.xml.bind.JAXBContext;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CalendarDataStore {

    void addEvent(Event event);

    void removeEvent(Event event);

    boolean removeEventById(String id);

    Event createEvent(String title, List<String> attendersLogins);

    List<Event> findEventsByTitle(String title);

    List<Event> findEventsById(String id);

    List<Event> findEventsByAttender(String login);

    List<Event> findEventsByDate(Date day);

    void registerPerson(Person person);

    void removePerson(Person person);

    Person findPerson(String personLogin);

    List<Person> findPersonsAlike(String personLogin);

    boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date);

    Date findBestTimePeriodToCreateEventForUsers(double durationMinutes, List<String> personsLogins);

    List<Event> findPersonsEventsAtCertainTime(String personLogin, Date date);
}
