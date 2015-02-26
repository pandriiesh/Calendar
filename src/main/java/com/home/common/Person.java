package com.home.common;

import java.util.List;

public class Person {

    private String name;
    private String email;
    private List<Event> eventList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
