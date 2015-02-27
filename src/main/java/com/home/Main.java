package com.home;

import com.home.common.Event;
import com.home.common.Person;
import com.home.service.CalendarService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class Main {

    List<Event> eventList;

    public void main(List<String> titles) {

        eventList = new ArrayList<Event>();

        for (String title : titles) {
            Event event = new Event.Builder().title(title).build();
            eventList.add(event);
        }
    }

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        CalendarService calendarService = (CalendarService) context.getBean("calendarServiceImpl");

        Event meeting = new Event.Builder()
                .title("Drinking beer")
                .description("We go to pub and drink some beer")
                .attendersEmails(Arrays.asList("Ivan", "Pavel", "Anton"))
                .startDate(new Date())
                .endDate(new Date())
                .build();

        calendarService.addEvent(meeting);

        System.out.println(calendarService.searchEvent(meeting.getTitle()).toString());


        // creating and testing database connection
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Person person1 = new Person();
        person1.setPersonName("person1.name");
        person1.setPersonEmail("person1.email");
        person1.setLogin("person1.login");
        person1.setPassword("person1.password");

        Person person2 = new Person();
        person2.setPersonName("person2.name");
        person2.setPersonEmail("person2.email");
        person2.setLogin("person2.login");
        person2.setPassword("person2.password");

        session.save(person1);
        session.save(person2);

        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        Person getPerson1 = (Person) session.get(Person.class, "person1.login");
        Person getPerson2 = (Person) session.get(Person.class, "person2.login");

        printPerson(getPerson1);
        printPerson(getPerson2);

        session.close();
        sessionFactory.close();
    }

    private static void printPerson(Person person) {
        System.out.println("Person name is " + person.getPersonName());
        System.out.println("Person email is " + person.getPersonEmail());
        System.out.println("Person login is " + person.getLogin());
        System.out.println("Person password is " + person.getPassword());
    }

}
