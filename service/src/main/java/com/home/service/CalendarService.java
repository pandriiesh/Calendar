package com.home.service;

import com.home.common.Event;
import com.home.common.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CalendarService extends Remote {

    void addEvent(Event event) throws RemoteException;

    void removeEvent(Event event) throws RemoteException;

    Event createEvent(String title, List<String> attendersEmails) throws RemoteException;

    Event searchEvent(String title) throws RemoteException;

    boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) throws RemoteException;

    void registerPerson(Person person) throws RemoteException;

    void removePerson(Person person) throws RemoteException;

    Person findPerson(String personLogin) throws RemoteException;

    Map<String, Person> getPersonStore() throws RemoteException;

    Map<String, Event> getEventStore() throws RemoteException;

    Date findBestTimePeriodToCreateEventForUsers(double durationHours, List<String> personsLogins) throws RemoteException;

    List<Event> findPersonsEventsAtCertainTime(String personLogin, Date date) throws RemoteException;
}
