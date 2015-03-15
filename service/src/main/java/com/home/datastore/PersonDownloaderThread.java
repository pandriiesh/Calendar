package com.home.datastore;

import com.home.common.Person;
import com.home.common.PersonAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;

public class PersonDownloaderThread implements Runnable {

    private File file;
    private Person person;

    public PersonDownloaderThread(File file, Person person) {
        this.file = file;
        this.person = person;
    }

    @Override
    public void run() {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PersonAdapter personAdapter = new PersonAdapter(person);
            marshaller.marshal(personAdapter, file);

        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
