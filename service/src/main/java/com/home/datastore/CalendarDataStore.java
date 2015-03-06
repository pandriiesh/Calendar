package com.home.datastore;

import com.home.common.EventInterface;
import com.home.common.Person;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CalendarDataStore {

    void addEvent(EventInterface event);

    void removeEvent(EventInterface event);

    EventInterface createEvent(String title, List<String> attendersEmails);

    EventInterface searchEvent(String title);

    Map<String, EventInterface> getEventStore();

    void registerPerson(Person person);

    void removePerson(Person person);

    Person findPerson(String personLogin);

    Map<String, Person> getPersonStore();

    boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date);

    Date findBestTimePeriodToCreateEventForUsers(double durationHours, List<String> personsLogins);

}
