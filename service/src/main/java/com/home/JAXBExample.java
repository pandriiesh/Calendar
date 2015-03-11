package com.home;

import com.home.common.EventAdapter;
import com.home.common.PersonAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

public class JAXBExample {

    public static void main(String[] args) {

        String pathToXMLDataStore = "C:/Java/Projects/Calendar2/CalendarXMLDataStore/";

        EventAdapter event = new EventAdapter();

        event.setTitle("title");
        event.setDescription("description");
        event.setStartTime(new Date());
        event.setEndTime(new Date());

        try{
            File file = new File(pathToXMLDataStore + "event_" + event.getId() + ".xml");
            JAXBContext context = JAXBContext.newInstance(EventAdapter.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(event, file);
//            marshaller.marshal(event, System.out);


        } catch (Exception e) {
            e.printStackTrace();
        }

        PersonAdapter person = new PersonAdapter();
        person.setLogin("personLogin");
        person.setPassword("personPassword");
        person.setPersonName("personName");
        person.setPersonEmail("personEmail");
        person.addEventToPerson(UUID.randomUUID().toString());

        try{
            File file = new File(pathToXMLDataStore + "person_" + person.getLogin() +".xml");
            JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(person, file);
//            marshaller.marshal(person, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Path eventStorePath = Paths.get(pathToXMLDataStore + "person_" + person.getLogin() +".xml");

        for (Path name : eventStorePath) {
            if (name.toString().startsWith("person_") && name.toString().endsWith(".xml")) {
                System.out.println("name = " + name);

                try {
                    File file = new File(pathToXMLDataStore + "person_" + person.getLogin() +".xml");
                    JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);

                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    PersonAdapter personAdapter = (PersonAdapter) unmarshaller.unmarshal(file);
                    System.out.println(personAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
