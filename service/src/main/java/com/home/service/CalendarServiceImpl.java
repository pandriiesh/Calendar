package com.home.service;

import com.home.common.Event;
import com.home.common.Person;
import com.home.datastore.CalendarDataStore;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CalendarServiceImpl implements CalendarService {

    private CalendarDataStore calendarDataStore;

    public CalendarServiceImpl(CalendarDataStore calendarDataStore) {
        this.calendarDataStore = calendarDataStore;
    }

    @Override
    public void addEvent(Event event) {
        for (Person person : event.getAttenders()) {
            findPerson(person.getLogin()).addEventToPerson(event);
        }
        calendarDataStore.addEvent(event);
    }

    @Override
    public Event createEvent(String title, List<String> emails) {
        return calendarDataStore.createEvent(title, emails);
    }

    @Override
    public List<Event> searchEvent(String title) {
        return calendarDataStore.searchEvent(title);
    }

    @Override
    public List<Event> searchEvent(Person person) {
        return calendarDataStore.searchEvent(person);
    }

    @Override
    public void removeEvent(Event event) {
        calendarDataStore.removeEvent(event);
    }

    @Override
    public Person findPerson(String personLogin) {
        return calendarDataStore.findPerson(personLogin);
    }

    @Override
    public void registerPerson(Person person) {
        calendarDataStore.registerPerson(person);
    }

    @Override
    public void removePerson(Person person) {
        calendarDataStore.removePerson(person);
    }

    @Override
    public Map<String, Person> getPersonStore() {
        return calendarDataStore.getPersonStore();
    }

    @Override
    public Map<UUID, Event> getEventStore() {
        return calendarDataStore.getEventStore();
    }

    @Override
    public boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) {
        return calendarDataStore.checkIfPersonIsFreeAtCertainTime(personLogin, date);
    }

    @Override
    public Date findBestTimePeriodToCreateEventForUsers(double durationHours, List<String> personsLogins) throws RemoteException {
        return calendarDataStore.findBestTimePeriodToCreateEventForUsers(durationHours, personsLogins);
    }

    @Override
    public List<Event> findPersonsEventsAtCertainTime(String personLogin, Date date) throws RemoteException {
        return calendarDataStore.findPersonsEventsAtCertainTime(personLogin, date);
    }
}
