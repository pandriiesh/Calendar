package com.home.datastore;

import com.home.common.Person;

import java.util.List;

public interface PersonDataStore {

    void registerPerson(Person person);

    void removePerson(Person person);

    Person findPerson(String title);
}
