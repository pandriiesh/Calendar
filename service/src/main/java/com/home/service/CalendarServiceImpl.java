package com.home.service;

import com.home.common.EventInterface;
import com.home.common.Person;
import com.home.datastore.CalendarDataStore;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CalendarServiceImpl implements CalendarService {

    private CalendarDataStore calendarDataStore;

    public CalendarServiceImpl(CalendarDataStore calendarDataStore) {
        this.calendarDataStore = calendarDataStore;
    }

    @Override
    public void addEvent(EventInterface event) {
        for (String login : event.getAttendersLogins()) {
            findPerson(login).addEventToPerson(event);
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
    public Map<String, EventInterface> getEventStore() {
        return calendarDataStore.getEventStore();
    }

    @Override
    public boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) {
        return calendarDataStore.checkIfPersonIsFreeAtCertainTime(personLogin, date);
    }

    @Override
    public Date findBestTimePeriodToCreateEventForUsers(double durationHours, List<String> personsLogins) throws RemoteException {
        return null;
    }
}
