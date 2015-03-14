package com.home.service;

import com.home.common.Event;
import com.home.common.EventAdapter;
import com.home.common.Person;
import com.home.common.PersonAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.UUID;

public class JAXBFileVisitor extends SimpleFileVisitor<Path> {

    private final Map<UUID, Event> eventStore;
    private final Map<String, Person> personStore;
    private Unmarshaller personUnmarshaller;
    private Unmarshaller eventUnmarshaller;


    public JAXBFileVisitor(Map<UUID, Event> eventStore, Map<String, Person> personStore) {
        this.eventStore = eventStore;
        this.personStore = personStore;
        try {
            JAXBContext personJAXBContext = JAXBContext.newInstance(PersonAdapter.class);
            JAXBContext eventJAXBContext = JAXBContext.newInstance(EventAdapter.class);

            personUnmarshaller = personJAXBContext.createUnmarshaller();
            eventUnmarshaller = eventJAXBContext.createUnmarshaller();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {

        if(attrs.isRegularFile()) {
            String fileName = path.getFileName().toString();

            if (fileName.startsWith("event_") && fileName.endsWith(".xml")) {
                System.out.println("Event: " + path.getFileName());

                try {
                    EventAdapter eventAdapter = (EventAdapter) eventUnmarshaller.unmarshal(path.toFile());
                    eventStore.put(eventAdapter.getId(), eventAdapter.asEvent());
                } catch (JAXBException e) {
                    e.printStackTrace();
                }

            } else if (fileName.startsWith("person_") && fileName.endsWith(".xml")) {
                System.out.println("Person: " + path.getFileName());

                try {
                    PersonAdapter personAdapter = (PersonAdapter) personUnmarshaller.unmarshal(path.toFile());
                    personStore.put(personAdapter.getLogin(), personAdapter.asPerson());

                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        }

        return FileVisitResult.CONTINUE;
    }
}
