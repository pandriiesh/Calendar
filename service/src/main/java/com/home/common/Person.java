package com.home.common;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private List<Event> eventList = new ArrayList<Event>();

    public void addEventToPerson(Event event) {
        eventList.add(event);
    }

    public void removeEvent(Event event) {
        eventList.remove(event);
    }

    public List<Event> getEvents() {
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
