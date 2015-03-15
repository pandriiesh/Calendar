package com.home.datastore;

import com.home.common.Person;
import com.home.common.PersonAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;
import java.util.Map;

public class PersonUploaderThread implements Runnable {

    private Path path;
    private Map<String, Person> personStore;

    public PersonUploaderThread(Path path, Map<String, Person> personStore) {
        this.path = path;
        this.personStore = personStore;
    }

    @Override
    public void run() {
        try {

            JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            PersonAdapter personAdapter = (PersonAdapter) unmarshaller.unmarshal(path.toFile());
            personStore.put(personAdapter.getLogin(), personAdapter.asPerson());

            System.out.println("PersonThread: " + Thread.currentThread().getName() + " "
                    + Thread.currentThread().getId() + ", uploaded: " + personAdapter.getLogin());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
