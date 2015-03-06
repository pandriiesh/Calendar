package com.home.service;

import com.home.common.EventInterface;
import com.home.common.Person;
import com.home.datastore.CalendarDataStore;
import com.home.datastore.PersonDataStore;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CalendarServiceImpl implements CalendarService {

    private CalendarDataStore calendarDataStore;
    private PersonDataStore personDataStore;

    public CalendarServiceImpl(CalendarDataStore calendarDataStore, PersonDataStore personDataStore) {
        this.calendarDataStore = calendarDataStore;
        this.personDataStore = personDataStore;
    }

    @Override
    public void addEvent(EventInterface event) {
        for (String login : event.getAttendersLogins()) {
            findPerson(login).addEvent(event);
        }
        calendarDataStore.addEvent(event);
    }

    @Override
    public EventInterface createEvent(String title, List<String> emails) {
        return calendarDataStore.createEvent(title, emails);
    }

    @Override
    public EventInterface searchEvent(String title) {
        return calendarDataStore.searchEvent(title);
    }

    @Override
    public void removeEvent(EventInterface event) {
        calendarDataStore.removeEvent(event);
    }

    @Override
    public Person findPerson(String personLogin) {
        return personDataStore.findPerson(personLogin);
    }

    @Override
    public void registerPerson(Person person) {
        personDataStore.registerPerson(person);
    }

    @Override
    public void removePerson(Person person) {
        personDataStore.removePerson(person);
    }

    @Override
    public Map<String, Person> getPersonStore() {
        return personDataStore.getPersonStore();
    }

    @Override
    public Map<String, EventInterface> getEventStore() {
        return calendarDataStore.getEventStore();
    }

    @Override
    public boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) {
        return personDataStore.checkIfPersonIsFreeAtCertainTime(personLogin, date);
    }
}
