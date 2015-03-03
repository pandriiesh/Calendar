package com.home.service;

import com.home.common.Person;
import com.home.datastore.PersonDataStore;
import com.home.datastore.PersonDataStoreImpl;

import java.util.Map;

public class PersonServiceImpl implements PersonService {

    private PersonDataStore personDataStore = new PersonDataStoreImpl();


    @Override
    public Person findPerson(String personLogin) {
        return personDataStore.findPerson(personLogin);
    }

    @Override
    public void registerPerson(Person person) {
        personDataStore.registerPerson(person);
    }

    @Override
    public void removePerson(Person person) {
        personDataStore.removePerson(person);
    }

    @Override
    public Map<String, Person> getPersonMap() {
        return personDataStore.getPersonMap();
    }
}
