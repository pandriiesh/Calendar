package com.home.service;

import com.home.common.Event;
import com.home.common.Person;
import com.home.datastore.CalendarDataStore;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public class CalendarServiceImpl implements CalendarService {

    private final CalendarDataStore calendarDataStore;

    public CalendarServiceImpl(CalendarDataStore calendarDataStore) {
        this.calendarDataStore = calendarDataStore;
    }

    @Override
    public void addEvent(Event event) {
        calendarDataStore.addEvent(event);
    }

    @Override
    public Event createEvent(String title, List<String> emails) {
        return calendarDataStore.createEvent(title, emails);
    }

    @Override
    public List<Event> findEventsByTitle(String title) {
        return calendarDataStore.findEventsByTitle(title);
    }

    @Override
    public List<Event> findEventsByAttender(String personLogin) {
        return calendarDataStore.findEventsByAttender(personLogin);
    }

    @Override
    public List<Event> findEventsById(String id) throws RemoteException {
        return calendarDataStore.findEventsById(id);
    }

    @Override
    public List<Event> findEventsByDate(Date day) throws RemoteException {
        return calendarDataStore.findEventsByDate(day);
    }

    @Override
    public void removeEvent(Event event) {
        calendarDataStore.removeEvent(event);
    }

    @Override
    public boolean removeEventById(String id) throws RemoteException {
        return calendarDataStore.removeEventById(id);
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
    public void removePerson(String personLogin) {
        calendarDataStore.removePerson(personLogin);
    }

    @Override
    public List<Person> findPersonsAlike(String personLogin) throws RemoteException {
        return calendarDataStore.findPersonsAlike(personLogin);
    }

    @Override
    public boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) {
        return calendarDataStore.checkIfPersonIsFreeAtCertainTime(personLogin, date);
    }

    @Override
    public Date findBestTimePeriodToCreateEventForUsers(double durationMinutes, List<String> personsLogins) throws RemoteException {
        return calendarDataStore.findBestTimePeriodToCreateEventForUsers(durationMinutes, personsLogins);
    }

    @Override
    public List<Event> findPersonsEventsAtCertainTime(String personLogin, Date date) throws RemoteException {
        return calendarDataStore.findPersonsEventsAtCertainTime(personLogin, date);
    }
}
