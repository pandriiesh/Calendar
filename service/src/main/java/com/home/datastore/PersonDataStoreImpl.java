package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventInterface;
import com.home.common.Person;

import java.util.Date;
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
    public Map<String, Person> getPersonStore() {
        return personStore;
    }

    @Override
    public boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) {

        Person person = personStore.get(personLogin);

        for (EventInterface event : person.getEvents()) {

            if (date.after(event.getStartTime()) && date.before(event.getEndTime())) {
                return false;
            }
        }
        return true;
    }
}
