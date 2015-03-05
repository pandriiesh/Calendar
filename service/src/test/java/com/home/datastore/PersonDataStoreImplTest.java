package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventInterface;
import com.home.common.Person;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PersonDataStoreImplTest {

    @Test
    public void testFindPerson() throws Exception {
        // initialize variable inputs
        Person person = new Person();

        // initialize mocks

        // initialize class to test
        PersonDataStore personDataStore = new PersonDataStoreImpl();

        // invoke method on class to test
        personDataStore.registerPerson(person);
        Person expectedPerson = personDataStore.findPerson(person.getLogin());

        // assert return value
        assertEquals(person, expectedPerson);

        // verify mock expectations

    }

    @Test
    public void testRegisterPerson() throws Exception {
        // initialize variable inputs
        Person person = new Person();

        // initialize mocks

        // initialize class to test
        PersonDataStore personDataStore = new PersonDataStoreImpl();

        // invoke method on class to test
        personDataStore.registerPerson(person);

        // assert return value
        assertEquals(person, personDataStore.findPerson(person.getLogin()));

        // verify mock expectations
    }

    @Test
    public void testRemovePerson() throws Exception {
        // initialize variable inputs
        Person person = new Person();

        // initialize mocks

        // initialize class to test
        PersonDataStore personDataStore = new PersonDataStoreImpl();

        // invoke method on class to test
        personDataStore.registerPerson(person);
        personDataStore.removePerson(person);
        Person expectedPerson = personDataStore.findPerson(person.getLogin());

        // assert return value
        assertNull(expectedPerson);

        // verify mock expectations
    }

    @Test
    public void testCheckIfPersonIsFreeAtCertainTime() throws Exception {

        // initialize variable inputs
        Person person = new Person();
        Date startTime = new Date(new Date().getTime()-3600000);
        Date endTime = new Date(new Date().getTime()+3600000);
        EventInterface event = new Event.Builder().startTime(startTime).endTime(endTime).build();

        person.addEvent(event);

        Date checkedTime = new Date();

        // initialize mocks

        // initialize class to test
        PersonDataStore personDataStore = new PersonDataStoreImpl();
        personDataStore.registerPerson(person);

        // invoke method on class to test
        boolean checked = personDataStore.checkIfPersonIsFreeAtCertainTime(person.getLogin(), checkedTime);

        // assert return value
        assertFalse(checked);

        // verify mock expectations

    }
}