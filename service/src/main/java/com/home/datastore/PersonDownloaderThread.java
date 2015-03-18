package com.home.datastore;

import com.home.common.Person;
import com.home.common.PersonAdapter;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;
import java.util.concurrent.Callable;

public class PersonDownloaderThread implements Callable<Boolean> {

    private final File file;
    private final Person person;
    private final Logger logger = Logger.getLogger(PersonDownloaderThread.class);

    public PersonDownloaderThread(File file, Person person) {
        this.file = file;
        this.person = person;
    }

    @Override
    public Boolean call() {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PersonAdapter personAdapter = new PersonAdapter(person);
            marshaller.marshal(personAdapter, file);

            logger.info("Person " + file.getAbsolutePath() + " downloaded successfully");

            return Boolean.TRUE;

        } catch (PropertyException e) {
            logger.warn("Person " + file.getAbsolutePath() + " downloading error: PropertyException", e);
        } catch (JAXBException e) {
            logger.warn("Person " + file.getAbsolutePath() + " downloading error: JAXBException", e);
        }
        return Boolean.FALSE;
    }
}
