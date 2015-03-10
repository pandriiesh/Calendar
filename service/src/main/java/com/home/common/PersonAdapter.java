package com.home.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class PersonAdapter {

    private String personName;

    private String personEmail;

    private String login;

    private String password;

    private List<String> eventList = new ArrayList<String>();

    public PersonAdapter() {
    }

    public PersonAdapter(Person person) {
        personName = person.getPersonName();
        personEmail = person.getPersonEmail();
        login = person.getLogin();
        password = person.getPassword();

        List<String> eventList = new ArrayList<String>(person.getEvents().size());

        for (Event event : person.getEvents()) {
            eventList.add(event.getId().toString());
        }

        this.eventList = eventList;
    }

    public void addEventToPerson(String eventId) {
        eventList.add(eventId);
    }

    public void removeEvent(String id) {
        for (int i=0; i < eventList.size(); i++) {
            if (eventList.equals(id)) {
                eventList.remove(i);
                i--;
            }
        }
    }

    public List<String> getEvents() {
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
