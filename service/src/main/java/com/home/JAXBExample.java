package com.home;

import com.home.common.EventAdapter;
import com.home.common.PersonAdapter;
import com.home.service.JAXBFileVisitor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

public class JAXBExample {

    public static void main(String[] args) {

        Path path = Paths.get("./CalendarXMLDataStore/person_personLogin.xml");
        Path path1 = Paths.get("./CalendarXMLDataStore");
        Path path2 = Paths.get("./CalendarXMLDataStore2");
        try {
            Files.copy(path1, path2);
            Thread.sleep(1000);
            Files.delete(path2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getFileName " + path.getFileName());
            System.out.println("toUri " +path.toUri());
            System.out.println("toAbsolutePath " + path.toAbsolutePath());
            System.out.println("normalize " + path.normalize());
            System.out.println("---------------------------------");

        Path path1Normalized = Paths.get(path.normalize().toString());
        System.out.println("toAbsolutePath " + path1Normalized.toAbsolutePath());
        try {
            System.out.println("toRealPath " + path1Normalized.toRealPath(LinkOption.NOFOLLOW_LINKS));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.walkFileTree(path1, new JAXBFileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }


        String pathToXMLDataStore = "C:/Java/Projects/Calendar2/CalendarXMLDataStore/";

        EventAdapter event = new EventAdapter();

        event.setTitle("title");
        event.setDescription("description");
        event.setStartTime(new Date());
        event.setEndTime(new Date());

        try {
            File file = new File(pathToXMLDataStore + "event_" + event.getId() + ".xml");
            JAXBContext context = JAXBContext.newInstance(EventAdapter.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(event, file);


        } catch (Exception e) {
            e.printStackTrace();
        }

        PersonAdapter person = new PersonAdapter();
        person.setLogin("personLogin");
        person.setPassword("personPassword");
        person.setPersonName("personName");
        person.setPersonEmail("personEmail");
        person.addEventToPerson(UUID.randomUUID().toString());

        try {
            File file = new File(pathToXMLDataStore + "person_" + person.getLogin() + ".xml");
            JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(person, file);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            File file = new File(pathToXMLDataStore + "person_" + person.getLogin() + ".xml");
            JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            PersonAdapter personAdapter = (PersonAdapter) unmarshaller.unmarshal(file);
            System.out.println(personAdapter);

        } catch (Exception e) {
            e.printStackTrace();


        }

    }
}
