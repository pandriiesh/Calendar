package com.home.mvc;

import com.home.common.Event;
import com.home.common.Person;
import com.home.datastore.CalendarDataStoreImpl;
import com.home.service.CalendarService;
import com.home.service.CalendarServiceImpl;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("myRequestObject")
public class MainController {

    private final CalendarService calendarService;

    public MainController() {
        this.calendarService = new CalendarServiceImpl(new CalendarDataStoreImpl());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        binder.registerCustomEditor(Date.class, "startTime", new CustomDateEditor(dateFormat, false));
        binder.registerCustomEditor(Date.class, "endTime", new CustomDateEditor(dateFormat, true));
    }

    @ModelAttribute
    public void addingCommonObjects(Model model, HttpServletRequest request) throws RemoteException {

        model.addAttribute("headerMessage", "ONLINE CALENDAR");
        request.getSession().setAttribute("personMap", calendarService.getPersonStore());
    }

    @RequestMapping("/")
    public String home() {
        return "pages/LoginForm";
    }

    @RequestMapping(value = "/LoginForm.html", method = RequestMethod.GET)
    public String getLoginForm(HttpServletRequest request) {

        request.getSession().setAttribute("personLogin", null);
        return "pages/LoginForm";
    }

    @RequestMapping(value = "/submitLoginForm.html", method = RequestMethod.POST)
    public String submitLoginForm(@RequestParam("login") String login,
                                        @RequestParam("password") String password,
                                        HttpServletRequest request) throws RemoteException {

        Person person = calendarService.findPerson(login);

        if (person != null && person.getPassword().equals(password)) {
            request.getSession().setAttribute("personLogin", person.getLogin());
            return "pages/LoginSuccess";
        } else {
            return "pages/LoginFailure";
        }
    }


    @RequestMapping(value = "/RegistrationForm.html", method = RequestMethod.GET)
    public String getRegistrationForm(HttpServletRequest request) {

        String personLogin = (String) request.getSession().getAttribute("personLogin");
        if (personLogin != null) {
            return "pages/LoggedInPage";
        }

        return "pages/RegistrationForm";
    }

    @RequestMapping(value = "/submitRegistrationForm.html", method = RequestMethod.POST)
    public String submitRegistrationForm(@Valid @ModelAttribute("person") Person person,
                                         BindingResult result) throws RemoteException {

        if (result.hasErrors()) {
            return "pages/RegistrationForm";
        }


        calendarService.registerPerson(person);

        return "pages/RegistrationSuccess";
    }

    @RequestMapping(value = "/ControlPanel.html")
    public String getControlPanel(HttpServletRequest request) throws RemoteException {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        request.getSession().setAttribute("person", calendarService.findPerson(personLogin));

        return "pages/ControlPanel";
    }

    @RequestMapping(value = "/RegisteredPersons.html")
    public String showRegisteredPersons(HttpServletRequest request) throws RemoteException {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        request.getSession().setAttribute("personMap", calendarService.getPersonStore());

        return "pages/RegisteredPersons";
    }

    @RequestMapping(value = "/CreateEventForm.html")
    public String createEvent(HttpServletRequest request) {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        return "pages/CreateEventForm";
    }

    @RequestMapping(value = "/submitCreateEventForm.html")
    public String submitCreateEvent(@RequestParam("title") String title,
                                    @RequestParam("description") String description,
                                    @RequestParam("startTime") String startTime,
                                    @RequestParam("endTime") String endTime,
                                    @RequestParam("attendersLogins") String attenders,
                                    HttpServletRequest request) throws RemoteException {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        if (title == null || title.isEmpty() || attenders == null || attenders.isEmpty() ) {
            return "pages/CreateEventForm";
        }

        Event event;

        try {
             event = calendarService.createEvent(title, Arrays.asList(attenders.split(" ")));
        } catch (Exception e) {
            return "pages/CreateEventForm";
        }

        if (description != null && !description.isEmpty()) {
            event.setDescription(description);
        }

        if (startTime != null && !startTime.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            Date parsedDate;
            try {
                parsedDate = formatter.parse(startTime);
            } catch (ParseException e) {
                return "pages/CreateEventForm";
            }
            event.setStartTime(parsedDate);
        }

        if (endTime != null && !endTime.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            Date parsedDate;
            try {
                parsedDate = formatter.parse(endTime);
            } catch (ParseException e) {
                return "pages/CreateEventForm";
            }
            event.setEndTime(parsedDate);
        }

        calendarService.addEvent(event);

        request.getSession().setAttribute("event", event);

        return "pages/ControlPanel";
    }

    @RequestMapping(value = "/ShowEvents.html")
    public String submitCreateEvent(HttpServletRequest request) throws RemoteException {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        Person person = calendarService.findPerson(personLogin);
        List<Event> personEvents = calendarService.findEventsByAttender(personLogin);

        request.getSession().setAttribute("person", person);
        request.getSession().setAttribute("personEvents", personEvents);

        return "pages/ShowEvents";
    }

    @RequestMapping(value = "/FindEventsByTitle.html")
    public String findEventByTitle(@RequestParam("eventTitleToFind") String attenderLogin,
                                      HttpServletRequest request) throws RemoteException {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        List<Event> events = calendarService.findEventsByTitle(attenderLogin);

        request.setAttribute("foundedEvents", events);

        return "pages/ShowEvents";
    }

    @RequestMapping(value = "/FindEventByID.html")
    public String findEventByID(@RequestParam("ID") String attenderID,
                                      HttpServletRequest request) throws RemoteException {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        List<Event> events = calendarService.findEventsById(attenderID);

        request.setAttribute("foundedEvents", events);

        return "pages/ShowEvents";
    }

    @RequestMapping(value = "/FindEventByAttender.html")
    public String findEventByAttender(@RequestParam("attenderLogin") String attenderLogin,
                            HttpServletRequest request) throws RemoteException {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        if (attenderLogin==null) {
            return "pages/ShowEvents";
        }

        List<Event> events;

        events = calendarService.findEventsByAttender(attenderLogin);

        request.setAttribute("foundedEvents", events);

        return "pages/ShowEvents";
    }

    @RequestMapping(value = "/FindEventByDate.html")
    public String findEventsByDate(@RequestParam("dateToFind") String timeToFind,
                                   HttpServletRequest request) throws RemoteException {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        Date dateToFind = null;

        if (timeToFind != null && !timeToFind.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            try {
                dateToFind = formatter.parse(timeToFind);
            } catch (ParseException e) {
                return "pages/ShowEvents";
            }
        }

        List<Event> events = calendarService.findEventsByDate(dateToFind);

        request.setAttribute("foundedEvents", events);

        return "pages/ShowEvents";
    }

    @RequestMapping(value = "/RemoveEventByID.html")
    public String removeEventByID(@RequestParam("eventID") String eventID,
                              HttpServletRequest request) throws RemoteException {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        if(eventID==null || eventID.isEmpty()) {
            return "pages/ShowEvents";
        }

        boolean eventRemoved;

        try {
            eventRemoved = calendarService.removeEventById(eventID);
        } catch (IllegalArgumentException e) {
            return "pages/ShowEvents";
        }

        request.setAttribute("isRemoved", eventRemoved);
        request.getSession().setAttribute("personEvents", calendarService.findEventsByAttender(personLogin));

        return "pages/ShowEvents";
    }


    @RequestMapping(value = "/findBestTimePeriodToCreateEventForUsers.html")
    public String findBestTimePeriodToCreateEventForUsers(@RequestParam("eventDuration") String eventDuration,
                                                          @RequestParam("eventAttenders") String eventAttenders,
                                                          HttpServletRequest request) throws RemoteException {

        String personLogin = (String) request.getSession().getAttribute("personLogin");

        if (personLogin == null) {
            return "pages/LoginForm";
        }

        List<String> personsLogins = Arrays.asList(eventAttenders.split(" "));
        Date date;
        try {
            date = calendarService.findBestTimePeriodToCreateEventForUsers(Double.parseDouble(eventDuration), personsLogins);
        } catch (Exception e) {
            return "pages/ShowEvents";
        }
        request.setAttribute("calculatedTimeForNewEvent", date);

        return "pages/ShowEvents";
    }
}

