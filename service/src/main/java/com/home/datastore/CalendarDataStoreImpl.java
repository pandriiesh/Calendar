package com.home.datastore;

import com.home.common.Event;
import com.home.common.Person;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

public class CalendarDataStoreImpl implements CalendarDataStore {

    private final Map<UUID, Event> eventStore;
    private final Map<String, Person> personStore;
    private final String pathToXMLDataStore = "./CalendarXMLDataStore/";
    private final ExecutorService executor;
    private final Logger logger = Logger.getLogger(CalendarDataStoreImpl.class);

    public CalendarDataStoreImpl() {
        eventStore = new ConcurrentHashMap<UUID, Event>();
        personStore = new ConcurrentHashMap<String, Person>();
        executor = new ThreadPoolExecutor(10, 100, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        Path path = Paths.get(pathToXMLDataStore);

        try {
            logger.info("Loading files...");

            JAXBFileVisitor fileVisitor = new JAXBFileVisitor(eventStore, personStore);
            Files.walkFileTree(path, fileVisitor);

            logger.info("Loading complete: " + fileVisitor.getCountOfUploadedFiles() +
                    " files were uploaded from disk storage");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addEvent(Event event) {
        for (String personLogin : event.getAttenders()) {
            Person person = findPerson(personLogin);

            if (!person.getEvents().contains(event.getId().toString())) {
                person.addEventToPerson(event.getId().toString());
                registerOrOverridePersonIfExists(person);
            }
        }

        File file = new File(pathToXMLDataStore + "EventDataStore/event_" + event.getId() + ".xml");

        Callable<Boolean> thread = new EventDownloaderThread(file, event);
        Future future = executor.submit(thread);

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        eventStore.put(event.getId(), event);
    }

    private void removeEvent(Event event) {
        if (eventStore.containsKey(event.getId())) {
            String filePath = pathToXMLDataStore + "EventDataStore/event_" + event.getId() + ".xml";

            try {
                File file = new File(filePath);
                Files.delete(file.toPath());
                logger.info("Event " + file.getAbsolutePath() + " removed");
            } catch (IOException e) {
                logger.error("Removing event " + filePath + " failed");
            }

            List<String> attenders = event.getAttenders();
            for (String personLogin : attenders) {
                Person person = personStore.get(personLogin);
                registerOrOverridePersonIfExists(person);
            }

            eventStore.remove(event.getId());
        }
    }


    @Override
    public boolean removeEventById(String id) {
        boolean eventRemoved = false;

        if (findEventsById(id).isEmpty()) {
            return false;
        }

        for (Map.Entry<String, Person> entry : personStore.entrySet()) {
            List<String> eventList = entry.getValue().getEvents();

            if (eventList.contains(id)) {
                entry.getValue().removeEventFromPerson(id);
                eventRemoved = true;
            }
        }

        removeEvent(eventStore.get(UUID.fromString(id)));
        eventStore.remove(UUID.fromString(id));

        return eventRemoved;
    }

    @Override
    public Event createEvent(String eventId, List<String> attendersLogins) {

        Event event = new Event();

        Date startTime = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        Date endTime = cal.getTime();

        event.setTitle(eventId);
        event.setAttenders(attendersLogins);
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
        UUID uuid;

        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return events;
        }

        if (eventStore.containsKey(uuid)) {
            events.add(eventStore.get(uuid));
        }

        return events;
    }

    @Override
    public List<Event> findEventsByAttender(String personLogin) {
        List<Event> eventList = new ArrayList<Event>();

        for(String eventId : findPerson(personLogin).getEvents()) {
            eventList.addAll(findEventsById(eventId));
        }
        return eventList;
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
        return personStore.get(personLogin);
    }

    @Override
    public List<Person> findPersonsAlike(String personLogin) {
        List<Person> personList = new ArrayList<Person>();

        for (Map.Entry<String, Person> entry : personStore.entrySet()) {
            if (entry.getValue().getLogin().contains(personLogin)) {
                personList.add(entry.getValue());
            }
        }

        return personList;
    }

    @Override
    public void registerPerson(Person person) {

        if (personStore.get(person.getLogin())!= null) {
            throw new RuntimeException("Such login already exists");
        }
        registerOrOverridePersonIfExists(person);
    }

    private void registerOrOverridePersonIfExists(Person person) {

        File file = new File(pathToXMLDataStore + "PersonDataStore/person_" + person.getLogin() + ".xml");

        Callable<Boolean> thread = new PersonDownloaderThread(file, person);
        Future future = executor.submit(thread);

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        personStore.put(person.getLogin(), person);
    }

    @Override
    public void removePerson(String personLogin) {
        String filePath = pathToXMLDataStore + "PersonDataStore/person_" + personLogin + ".xml";

        try {
            File file = new File(filePath);
            Files.delete(file.toPath());
            logger.info("Person " + file.getAbsolutePath() + " removed");
        } catch (IOException e) {
            logger.error("Removing person " + filePath + " failed", e);
        }

        Person person = personStore.get(personLogin);

        List<String> events = new ArrayList<String>(person.getEvents());

        for (String eventId : events) {
            Event event = eventStore.get(UUID.fromString(eventId));
            event.removePersonFromEvent(personLogin);
            addEvent(event);

            if (event.getAttenders().isEmpty()) {
                removeEventById(event.getId().toString());
            }
        }

        person.setEventList(events);
        personStore.remove(personLogin);

    }


    @Override
    public boolean checkIfPersonIsFreeAtCertainTime(String personLogin, Date date) {

        for (Event event : findEventsByAttender(personLogin)) {
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
            for (Event event : findEventsByAttender(personList.get(i).getLogin())) {

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

        for (Event event : findEventsByAttender(personLogin)) {

            if (date.after(event.getStartTime()) && date.before(event.getEndTime())) {
                eventList.add(event);
            }
        }

        return eventList;
    }

    @Override
    public void addPeriodicEvent(Event event, Period period, int quantity) {

        addEvent(event);

        for (int i = 1; i <= quantity; i++) {

            Event nextEvent = new Event(event);

            Calendar calStart = Calendar.getInstance();
            calStart.setTime(nextEvent.getStartTime());

            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(nextEvent.getEndTime());

            switch (period) {

                case YEAR:
                    calStart.add(Calendar.YEAR, i);
                    calEnd.add(Calendar.YEAR, i);
                    break;

                case MONTH:
                    calStart.add(Calendar.MONTH, i);
                    calEnd.add(Calendar.MONTH, i);
                    break;

                case WEEK:
                    calStart.add(Calendar.WEEK_OF_YEAR, i);
                    calEnd.add(Calendar.WEEK_OF_YEAR, i);
                    break;

                case DAY:
                    calStart.add(Calendar.DAY_OF_YEAR, i);
                    calEnd.add(Calendar.DAY_OF_YEAR, i);
                    break;

                default: break;
            }

            Date startDate = calStart.getTime();
            Date endDate = calEnd.getTime();

            nextEvent.setStartTime(startDate);
            nextEvent.setEndTime(endDate);

            addEvent(nextEvent);
        }
    }

    @Override
    public void addPeriodicEvent(Event event, List<PeriodDayOfWeek> daysList, int quantity) {

        System.out.println(event.getStartTime());
        addEvent(event);
        List<Integer> daysListValues = new ArrayList<Integer>();

        for(PeriodDayOfWeek period : daysList) {
            daysListValues.add(period.getValue());
        }

        Event nextEvent = new Event(event);

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(nextEvent.getStartTime());

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(nextEvent.getEndTime());

        for (int i = 0; i < quantity; i++) {
            calStart.add(Calendar.DAY_OF_YEAR, 1);
            calEnd.add(Calendar.DAY_OF_YEAR, 1);

            if (daysListValues.contains(calStart.get(Calendar.DAY_OF_WEEK))) {
                System.out.println(event.getStartTime());
                addEvent(new Event(nextEvent));
            } else {
                i--;
            }
        }
    }
}
