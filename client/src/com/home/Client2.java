package com.home;

import com.home.common.Person;
import com.home.service.CalendarService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;

public class Client2 {

    private final Logger logger = Logger.getLogger(Client2.class);

    private final ApplicationContext context = new ClassPathXmlApplicationContext("clientApplicationContext.xml");

    private final CalendarService service = (CalendarService) context.getBean("calendarService");

    public static void main(String[] args) throws RemoteException, InterruptedException {

        Client2 client = new Client2();
        Logger logger = client.logger;
        CalendarService service = client.service;


        logger.info("Adding 100 persons...");
        add(service);
        logger.info("Added 100 persons");

        logger.info("Removing 100 persons...");
        remove(service);
        logger.info("Removed 100 persons");

    }

    private static void add(CalendarService service) throws RemoteException {

        for (int i=1; i<101; i++) {

            Person person = new Person();

            person.setPersonName("name_" + i);
            person.setPersonEmail("email_" + i);
            person.setLogin("login_" + i);
            person.setPassword("password_" + i);

            service.registerPerson(person);
        }

    }

    private static void remove(CalendarService service) throws RemoteException, InterruptedException {

        for (int i=1; i<101; i++) {
            service.removePerson("login_" + i);
        }
    }
}
