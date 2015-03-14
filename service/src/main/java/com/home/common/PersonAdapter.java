package com.home.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class PersonAdapter {

    private String personName;

    private String personEmail;

    private String login;

    private String password;

    private List<String> eventList;


    public PersonAdapter() {
    }

    public PersonAdapter(Person person) {
        personName = person.getPersonName();
        personEmail = person.getPersonEmail();
        login = person.getLogin();
        password = person.getPassword();
        eventList = person.getEvents();
    }

    public Person asPerson() {
        Person person = new Person();

        person.setLogin(login);
        person.setPassword(password);
        person.setPersonName(personName);
        person.setPersonEmail(personEmail);
        person.setEventList(eventList);

        return person;
    }

    public void addEventToPerson(String eventId) {
        eventList.add(eventId);
    }

    public void removeEvent(String id) {
        for (int i=0; i < eventList.size(); i++) {
            if (eventList.equals(id)) {
                eventList.remove(i);
                return;
            }
        }
    }

    public void setEventList(List<String> eventList) {
        this.eventList = eventList;
    }

    public List<String> getEventList() {
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
