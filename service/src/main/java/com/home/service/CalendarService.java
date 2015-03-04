package com.home.service;

import com.home.common.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CalendarService extends Remote {

    public void addEvent(Event event) throws RemoteException;

    public void removeEvent(Event event) throws RemoteException;

    public Event createEvent(String title, List<String> attendersEmails) throws RemoteException;

    public Event searchEvent(String title) throws RemoteException;
}
