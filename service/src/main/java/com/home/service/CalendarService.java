package com.home.service;

import com.home.common.Event;
import com.home.common.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CalendarService extends Remote {

    void addEvent(Event event) throws RemoteException;

    void removeEvent(Event event) throws RemoteException;

    Event createEvent(String title, List<String> attendersEmails) throws RemoteException;

    List<Event> findEventByTitle(String title) throws RemoteException;

    List<Event> findEventById(String id) throws RemoteException;

    List<Event> findEventByAttender(String login) throws RemoteException;

    List<Event> findEventsByDate(Date day) throws RemoteException;

    boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) throws RemoteException;

    void registerPerson(Person person) throws RemoteException;

    void removePerson(Person person) throws RemoteException;

    Person findPerson(String personLogin) throws RemoteException;

    Map<String, Person> getPersonStore() throws RemoteException;

    Map<UUID, Event> getEventStore() throws RemoteException;

    Date findBestTimePeriodToCreateEventForUsers(double durationHours, List<String> personsLogins) throws RemoteException;

    List<Event> findPersonsEventsAtCertainTime(String personLogin, Date date) throws RemoteException;
}
