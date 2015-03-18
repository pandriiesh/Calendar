package com.home.datastore;

import com.home.common.Person;
import com.home.common.PersonAdapter;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;

public class PersonUploaderThread implements Callable<Boolean> {

    private final Path path;
    private final Map<String, Person> personStore;
    private final Logger logger = Logger.getLogger(PersonUploaderThread.class);


    public PersonUploaderThread(Path path, Map<String, Person> personStore) {
        this.path = path;
        this.personStore = personStore;
    }

    @Override
    public Boolean call() {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            PersonAdapter personAdapter = (PersonAdapter) unmarshaller.unmarshal(path.toFile());
            personStore.put(personAdapter.getLogin(), personAdapter.asPerson());

            logger.info("Person " + path.toString() + " uploaded successfully");

            return Boolean.TRUE;

        } catch (JAXBException e) {
            logger.warn("Person " + path.toString() + " upload error:", e);
        }
        return Boolean.FALSE;
    }
}
