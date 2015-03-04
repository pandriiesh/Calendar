package com.home.service;

import com.home.common.Person;

import java.util.Map;

public interface PersonService {

    void registerPerson(Person person);

    void removePerson(Person person);

    Person findPerson(String personLogin);

    Map<String, Person> getPersonMap();

}
