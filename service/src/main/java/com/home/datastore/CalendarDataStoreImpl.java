package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventAdapter;
import com.home.common.Person;
import com.home.common.PersonAdapter;

import javax.xml.bind.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CalendarDataStoreImpl implements CalendarDataStore {

    private final Map<UUID, Event> eventStore;
    private final Map<String, Person> personStore;
    private JAXBContext eventJAXBContext = null;
    private JAXBContext personJAXBContext = null;
    private final String pathToXMLDataStore = "C:/Java/Projects/Calendar2/CalendarXMLDataStore/";


    public CalendarDataStoreImpl() {
        eventStore = new HashMap<UUID, Event>();
        personStore = new HashMap<String, Person>();

        try {
            eventJAXBContext = JAXBContext.newInstance(EventAdapter.class);
            personJAXBContext = JAXBContext.newInstance(PersonAdapter.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JAXBContext getEventJAXBContext() {
        return eventJAXBContext;
    }

    @Override
    public JAXBContext getPersonJAXBContext() {
        return personJAXBContext;
    }

    @Override
    public String getPathToXMLDataStore() {
        return pathToXMLDataStore;
    }

    @Override
    public Map<UUID, Event> getEventStore() {
        return eventStore;
    }

    @Override
    public void addEvent(Event event) {


        for (Person person : event.getAttenders()) {
            findPerson(person.getLogin()).addEventToPerson(event);
        }
        eventStore.put(event.getId(), event);

    }

    @Override
    public void removeEvent(Event event) {
        eventStore.remove(event.getId());
    }

    @Override
    public boolean removeEventById(String id) {
        boolean eventRemoved = false;
        for(Map.Entry<String, Person> entry : personStore.entrySet()) {

            if(entry.getValue().getEvents().contains(eventStore.get(UUID.fromString(id)))) {
                entry.getValue().removeEvent(id);
                eventRemoved = true;
            }
        }
        eventStore.remove(UUID.fromString(id));
        return eventRemoved;
    }

    @Override
    public Event createEvent(String title, List<String> attendersLogins) {

        Event event = new Event();

        List<Person> personList = new ArrayList<Person>(attendersLogins.size());

        for(String login :attendersLogins) {
            personList.add(personStore.get(login));
        }

        Date startTime = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        Date endTime = cal.getTime();

        event.setTitle(title);
        event.setAttenders(personList);
        event.setStartTime(startTime);
        event.setEndTime(endTime);

        return event;
    }

    @Override
    public List<Event> findEventsByTitle(String title) {

        List<Event> events = new ArrayList<Event>();

        for (Map.Entry<UUID, Event> entry : eventStore.entrySet())
        {
            if(entry.getValue().getTitle().contains(title)) {
                events.add(entry.getValue());
            }
        }
        return events;
    }

    @Override
    public List<Event> findEventsById(String id) {
        List<Event> events = new ArrayList<Event>();

        for (Map.Entry<UUID, Event> entry : eventStore.entrySet()) {
            if (entry.getValue().getId().toString().equals(id)) {
                events.add(entry.getValue());
            }
        }

        return events;
    }

    @Override
    public List<Event> findEventsByAttender(String login) {
        return personStore.get(login).getEvents();
    }

    @Override
    public List<Event> findEventsByDate(Date startOfTheDay) {

        Date endOfTheDay = new Date();
        endOfTheDay.setTime(startOfTheDay.getTime() + 24*60*60*1000 - 1);

        List<Event> eventList = new ArrayList<Event>();

        for(Map.Entry<UUID, Event> entry : eventStore.entrySet()) {

            if ((entry.getValue().getStartTime().after(startOfTheDay) &&
                 entry.getValue().getStartTime().before(endOfTheDay)) ||
                 entry.getValue().getEndTime().after(startOfTheDay) &&
                 entry.getValue().getEndTime().before(endOfTheDay))
            {
                eventList.add(entry.getValue());
            }
        }
        return eventList;
    }

    @Override
    public Person findPerson(String personLogin) {

        PersonAdapter personAdapter = null;

        try {
            File file = new File(pathToXMLDataStore + "person_" + personLogin + ".xml");
            JAXBContext context = JAXBContext.newInstance(PersonAdapter.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            personAdapter = (PersonAdapter) unmarshaller.unmarshal(file);


        } catch (Exception e) {
            e.printStackTrace();


        }

        return personAdapter.asPerson();

//        return personStore.get(personLogin);
    }

    @Override
    public void registerPerson(Person person) {

        File file = new File(pathToXMLDataStore + "person_" + person.getLogin() + ".xml");

        Marshaller marshaller;

        try {
            marshaller = personJAXBContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PersonAdapter personAdapter = new PersonAdapter(person);
            marshaller.marshal(personAdapter, file);
            marshaller.marshal(personAdapter, System.out);

        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        personStore.put(person.getLogin(), person);
    }

    @Override
    public void removePerson(Person person) {
        personStore.remove(person.getLogin());
    }

    @Override
    public Map<String, Person> getPersonStore() {
        return personStore;
    }

    @Override
    public boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) {

        Person person = personStore.get(personLogin);

        for (Event event : person.getEvents()) {

            if (date.after(event.getStartTime()) && date.before(event.getEndTime())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Date findBestTimePeriodToCreateEventForUsers(double durationMinutes, List<String> personsLogins) {

        List<Person> personList = new ArrayList<Person>(personsLogins.size());

        long NOW_TIME_MINUTES = new Date().getTime()/1000/60;
        long EVENT_DURATION_MINUTES = (long) durationMinutes;
        long INTERVAL = 15*60*1000;
        int MATRIX_SIZE = 11*365*24*60;
        int MINUTES_IN_YEAR = 525600;

        for (String personLogin : personsLogins) {
            personList.add(personStore.get(personLogin));
        }

        byte[][] timeMatrix = new byte[personsLogins.size()][MATRIX_SIZE];

            for (int i = 0; i < personsLogins.size(); i++) {
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    timeMatrix[i][j]=0;
                }
            }

        for (int i=0; i<personList.size(); i++) {
            for (Event event : personList.get(i).getEvents()) {

                if (event.getEndTime().before(new Date()))
                    continue;

                int startTimeInMinutes = (int) (event.getStartTime().getTime()/1000/60 - NOW_TIME_MINUTES);
                startTimeInMinutes += MINUTES_IN_YEAR;

                int endTimeInMinutes = (int) (event.getEndTime().getTime()/1000/60 - NOW_TIME_MINUTES);
                endTimeInMinutes += MINUTES_IN_YEAR;

                for (int j = startTimeInMinutes; j < endTimeInMinutes + 1; j++) {
                    timeMatrix[i][j] = 1;
                }
            }
        }

        int countOfFreeMinutes = 0;
        int startPeriodPosition = MINUTES_IN_YEAR;
        int[] marker = new int[personList.size()];
        for(int i=0; i< marker.length; i++) {
            marker[i] = -1;
        }

        for (int i=0; i<personList.size(); i++) {

            if (marker[i] != -1)
                continue;

            for (int j = startPeriodPosition; j<MATRIX_SIZE; j++) {

                if(countOfFreeMinutes == EVENT_DURATION_MINUTES) {
                    marker[i] = j - countOfFreeMinutes;
                    startPeriodPosition = j - countOfFreeMinutes;
                    i = -1;
                    countOfFreeMinutes = 0;
                    break;
                }

                if (timeMatrix[i][j] == 1) {
                    for(int k=0; k < marker.length; k++) {
                        marker[k] = -1;
                    }
                    countOfFreeMinutes = -1;
                }
                countOfFreeMinutes++;
            }
        }

        Date date = new Date();
        date.setTime((NOW_TIME_MINUTES + startPeriodPosition - MINUTES_IN_YEAR)*60*1000 + INTERVAL);

        return date;
    }

    @Override
    public List<Event> findPersonsEventsAtCertainTime(String personLogin, Date date) {

        List<Event> eventList = new ArrayList<Event>();

        for (Event event : personStore.get(personLogin).getEvents()) {

            if (date.after(event.getStartTime()) && date.before(event.getEndTime())) {
                eventList.add(event);
            }
        }

        return eventList;
    }
}
