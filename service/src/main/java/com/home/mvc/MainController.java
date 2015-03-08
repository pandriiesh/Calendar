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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("myRequestObject")
public class MainController {

    CalendarService calendarService = new CalendarServiceImpl(new CalendarDataStoreImpl());

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

    @RequestMapping(value = "/LoginForm.html", method = RequestMethod.GET)
    public String getLoginForm(HttpServletRequest request) {

        request.getSession().setAttribute("personName", null);
        return "pages/LoginForm";
    }

    @RequestMapping(value = "/submitLoginForm.html", method = RequestMethod.POST)
    public ModelAndView submitLoginForm(@RequestParam("login") String login,
                                        @RequestParam("password") String password,
                                        HttpServletRequest request) throws RemoteException {

        Person person = calendarService.findPerson(login);

        if (person != null && person.getPassword().equals(password)) {
            ModelAndView model = new ModelAndView("pages/LoginSuccess");
            model.addObject("person", person);
            request.getSession().setAttribute("personName", person.getPersonName());
            return model;
        } else {
            ModelAndView model = new ModelAndView("pages/LoginFailure");
            model.addObject("login", login);
            model.addObject("password", password);
            return model;
        }
    }


    @RequestMapping(value = "/RegistrationForm.html", method = RequestMethod.GET)
    public String getRegistrationForm(HttpServletRequest request) {

        String personName = (String) request.getSession().getAttribute("personName");
        if (personName != null) {
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

        String personName = (String) request.getSession().getAttribute("personName");

        if (personName == null) {
            return "pages/LoginForm";
        }

        request.getSession().setAttribute("person", calendarService.findPerson(personName));

        return "pages/ControlPanel";
    }

    @RequestMapping(value = "/RegisteredPersons.html")
    public String showRegisteredPersons(HttpServletRequest request) throws RemoteException {

        String personName = (String) request.getSession().getAttribute("personName");

        if (personName == null) {
            return "pages/LoginForm";
        }

        request.getSession().setAttribute("personMap", calendarService.getPersonStore());

        return "pages/RegisteredPersons";
    }

    @RequestMapping(value = "/CreateEventForm.html")
    public String createEvent(HttpServletRequest request) {

        String personName = (String) request.getSession().getAttribute("personName");

        if (personName == null) {
            return "pages/LoginForm";
        }

        return "pages/CreateEventForm";
    }

    @RequestMapping(value = "/submitCreateEventForm.html")
    public String submitCreateEvent(@ModelAttribute("event") Event event,
                                    @RequestParam("attenders") String attenders,
                                    HttpServletRequest request) throws RemoteException {

        String personName = (String) request.getSession().getAttribute("personName");

        if (personName == null) {
            return "pages/LoginForm";
        }

        List<Person> personList = new ArrayList<Person>();

        for (String personLogin : Arrays.asList(attenders.split(" "))) {
            personList.add(calendarService.findPerson(personLogin));
        }

        event.setAttenders(personList);

        calendarService.addEvent(event);

        request.getSession().setAttribute("event", event);

        return "pages/ControlPanel";
    }

    @RequestMapping(value = "/ShowEvents.html")
    public String submitCreateEvent(HttpServletRequest request) throws RemoteException {

        String personName = (String) request.getSession().getAttribute("personName");

        if (personName == null) {
            return "pages/LoginForm";
        }

        Person person = calendarService.findPerson((String) request.getSession().getAttribute("personName"));

        request.getSession().setAttribute("person", person);

        return "pages/ShowEvents";
    }

    @RequestMapping(value = "/FindEvent.html")
    public String findEvent(@RequestParam("eventToFind") String eventToFind,
                            HttpServletRequest request) throws RemoteException {

        String personName = (String) request.getSession().getAttribute("personName");

        if (personName == null) {
            return "pages/LoginForm";
        }

        Event event = calendarService.searchEvent(eventToFind);

        request.setAttribute("foundedEvent", event);

        return "pages/ShowEvents";
    }

    @RequestMapping(value = "/RemoveEvent.html")
    public String removeEvent(@RequestParam("eventToRemove") String eventToRemove,
                              HttpServletRequest request) throws RemoteException {

        String personName = (String) request.getSession().getAttribute("personName");

        if (personName == null) {
            return "pages/LoginForm";
        }

        Event event = calendarService.searchEvent(eventToRemove);

        for (Person person : event.getAttenders()) {
            calendarService.findPerson(person.getLogin()).removeEvent(event);
        }

        calendarService.removeEvent(event);

        request.setAttribute("isRemoved", Boolean.TRUE);

        return "pages/ShowEvents";
    }
}

