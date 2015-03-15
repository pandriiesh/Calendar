package com.home.service;

import com.home.common.Event;
import com.home.common.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface CalendarService extends Remote {

    void addEvent(Event event) throws RemoteException;

    void removeEvent(Event event) throws RemoteException;

    boolean removeEventById(String id) throws RemoteException;

    Event createEvent(String title, List<String> attendersEmails) throws RemoteException;

    List<Event> findEventsByTitle(String title) throws RemoteException;

    List<Event> findEventsById(String id) throws RemoteException;

    List<Event> findEventsByAttender(String login) throws RemoteException;

    List<Event> findEventsByDate(Date day) throws RemoteException;

    boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) throws RemoteException;

    void registerPerson(Person person) throws RemoteException;

    void removePerson(String personLogin) throws RemoteException;

    Person findPerson(String personLogin) throws RemoteException;

    List<Person> findPersonsAlike(String personLogin) throws RemoteException;

    Date findBestTimePeriodToCreateEventForUsers(double durationMinutes, List<String> personsLogins) throws RemoteException;

    List<Event> findPersonsEventsAtCertainTime(String personLogin, Date date) throws RemoteException;
}
