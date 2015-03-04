package com.home.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable{

    private String personName;
    private String personEmail;
    private String login;
    private String password;
    private List<EventInterface> eventList = new ArrayList<EventInterface>();

    public void addEvent(EventInterface event) {
        eventList.add(event);
    }

    public void removeEvent(EventInterface event) {
        eventList.remove(event);
    }

    public List<EventInterface> getEvents() {
        return eventList;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "eventList=" + eventList +
                ", personName='" + personName + '\'' +
                ", personEmail='" + personEmail + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
