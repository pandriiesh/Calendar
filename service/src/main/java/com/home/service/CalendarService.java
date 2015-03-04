package com.home.service;

import com.home.common.Event;
import com.home.common.EventInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CalendarService extends Remote {

    public void addEvent(EventInterface event) throws RemoteException;

    public void removeEvent(EventInterface event) throws RemoteException;

    public EventInterface createEvent(String title, List<String> attendersEmails) throws RemoteException;

    public EventInterface searchEvent(String title) throws RemoteException;
}
