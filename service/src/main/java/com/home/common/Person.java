package com.home.common;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

public class Person implements Serializable{

    @Size(min=1, max=20, message = "Name length must be between {min} and {max} characters!")
    @Pattern(regexp="[^0-9]*", message = "Name field cannot contain digits.")
    private String personName;

    @Email
    @Size(min=1, message = "Email is required!")
    private String personEmail;

    @Size(min=2, max=20, message = "Login length must be between {min} and {max} characters!")
    private String login;

    @Size(min=4, max=20, message = "Password length must be between {min} and {max} characters!")
    private String password;

    private List<String> eventList = new ArrayList<String>();

    public void addEventToPerson(String id) {
        if(eventList==null) {
            eventList = new ArrayList<String>();
        }
        eventList.add(id);
    }

    public void removeEventFromPerson(String id) {
        for (int i=0; i < eventList.size(); i++) {
            if (eventList.get(i).equals(id)) {
                eventList.remove(i);
                break;
            }
        }
    }

    public void setEventList(List<String> eventList) {
        this.eventList = eventList;
    }

    public List<String> getEvents() {
        if (eventList==null)
            return new ArrayList<String>();
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
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", personName='" + personName + '\'' +
                ", personEmail='" + personEmail + '\'' +
                ", events='" + eventList + '\'' +
                '}';
    }
}
