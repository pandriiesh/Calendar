package com.home.common;

import java.util.List;

public class Person {

    private String personName;
    private String personEmail;
    private List<Event> eventList;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }
}
