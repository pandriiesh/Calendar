package com.home.service;

import com.home.common.Event;
import com.home.common.Person;
import com.home.datastore.CalendarDataStore;

import javax.xml.bind.JAXBContext;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CalendarServiceImpl implements CalendarService {

    private final CalendarDataStore calendarDataStore;
    private JAXBContext eventJAXBContext = null;
    private JAXBContext personJAXBContext = null;
    private final String pathToXMLDataStore = "C:/Java/Projects/Calendar2/CalendarXMLDataStore/";

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
    public List<Event> findEventByTitle(String title) {
        return calendarDataStore.findEventsByTitle(title);
    }

    @Override
    public List<Event> findEventById(String id) throws RemoteException {
        return calendarDataStore.findEventsById(id);
    }

    @Override
    public JAXBContext getEventJAXBContext() {
        return eventJAXBContext;
    }

    @Override
    public JAXBContext getPersonJAXBContext() {
        return personJAXBContext;
    }

    @Override
    public String getPathToXMLDataStore() {
        return pathToXMLDataStore;
    }

    @Override
    public List<Event> findEventByAttender(String login) {
        return calendarDataStore.findEventsByAttender(login);
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
    public Date findBestTimePeriodToCreateEventForUsers(double durationMinutes, List<String> personsLogins) throws RemoteException {
        return calendarDataStore.findBestTimePeriodToCreateEventForUsers(durationMinutes, personsLogins);
    }

    @Override
    public List<Event> findPersonsEventsAtCertainTime(String personLogin, Date date) throws RemoteException {
        return calendarDataStore.findPersonsEventsAtCertainTime(personLogin, date);
    }
}
