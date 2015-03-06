package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventInterface;
import com.home.common.Person;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarDataStoreImpl implements CalendarDataStore {

    private final Map<String, EventInterface> eventStore = new HashMap<String, EventInterface>();
    private final Map<String, Person> personStore = new HashMap<String, Person>();

    @Override
    public Map<String, EventInterface> getEventStore() {
        return eventStore;
    }

    @Override
    public void addEvent(EventInterface event) {
        eventStore.put(event.getTitle(), event);
    }

    @Override
    public void removeEvent(EventInterface event) {
        eventStore.remove(event.getTitle());
    }

    @Override
    public EventInterface createEvent(String title, List<String> attendersEmails) {
        EventInterface event = new Event.Builder().title(title).attendersLogins(attendersEmails).build();
        return event;
    }

    @Override
    public EventInterface searchEvent(String title) {
        EventInterface event = eventStore.get(title);
        return event;
    }

    @Override
    public Person findPerson(String personLogin) {
        return personStore.get(personLogin);
    }

    @Override
    public void registerPerson(Person person) {
        personStore.put(person.getLogin(), person);
    }

    @Override
    public void removePerson(Person person) {
        personStore.remove(person.getLogin());
    }

    @Override
    public Map<String, Person> getPersonStore() {
        return personStore;
    }

    @Override
    public boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) {

        Person person = personStore.get(personLogin);

        for (EventInterface event : person.getEvents()) {

            if (date.after(event.getStartTime()) && date.before(event.getEndTime())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Date findBestTimePeriodToCreateEventForUsers(double durationHours, List<String> personsLogins) {

        return null;
    }
}
