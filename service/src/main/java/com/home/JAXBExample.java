package com.home;

import com.home.common.EventAdapter;
import com.home.common.Person;
import com.home.common.PersonAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class JAXBExample {

    public static void main(String[] args) {


        EventAdapter event = new EventAdapter();

        event.setTitle("title");
        event.setDescription("description");
        event.setStartTime(new Date());
        event.setEndTime(new Date());

        try{
            File file = new File("C:\\Java\\CalendarXMLDataStore\\file.xml");
            JAXBContext context = JAXBContext.newInstance(EventAdapter.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(event, file);
            marshaller.marshal(event, System.out);




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
            File file = new File("C:\\Java\\CalendarXMLDataStore\\"+ person.getLogin() +".xml");
            JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(person, file);
            marshaller.marshal(person, System.out);


        } catch (Exception e) {
            e.printStackTrace();
        }


        try{
            File file = new File("C:\\Java\\CalendarXMLDataStore\\"+ person.getLogin() +".xml");
            JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            PersonAdapter personAdapter = (PersonAdapter) unmarshaller.unmarshal(file);
            System.out.println(personAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
