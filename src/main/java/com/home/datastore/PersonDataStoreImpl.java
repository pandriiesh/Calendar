package com.home.datastore;

import com.home.common.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDataStoreImpl implements PersonDataStore {

    private final Map<String, Person> personStore = new HashMap<String, Person>();

    @Override
    public Person findPerson(String personLogin) {
        return personStore.get(personLogin);
    }

    @Override
    public void registerPerson(Person person) {
        personStore.put(person.getLogin(), person);
    }

    @Override
    public void removePerson(Person person) {
        personStore.remove(person.getLogin());
    }

    @Override
    public Map<String, Person> getPersonMap() {
        return personStore;
    }
}
