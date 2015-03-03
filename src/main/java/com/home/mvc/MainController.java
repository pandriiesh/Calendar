package com.home.mvc;

import com.home.common.EventClone;
import com.home.common.Person;
import com.home.datastore.PersonDataStore;
import com.home.datastore.PersonDataStoreImpl;
import com.home.service.PersonService;
import com.home.service.PersonServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes("myRequestObject")
public class MainController {

    PersonService personService = new PersonServiceImpl();

    @ModelAttribute
    public void addingCommonObjects(Model model, HttpServletRequest request) {

        model.addAttribute("headerMessage", "ONLINE CALENDAR");
        request.getSession().setAttribute("personMap", personService.getPersonMap());
    }

    @RequestMapping(value = "/LoginForm.html", method = RequestMethod.GET)
    public String getLoginForm(HttpServletRequest request) {

        request.getSession().setAttribute("personName", null);
        return "pages/LoginForm";
    }

    @RequestMapping(value = "/submitLoginForm.html", method = RequestMethod.POST)
    public ModelAndView submitLoginForm(@RequestParam("login") String login,
                                            @RequestParam("password") String password,
                                            HttpServletRequest request) {

        Person person = personService.findPerson(login);

        if (person!=null && person.getPassword().equals(password)) {
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
        if (personName!=null) {
            return "pages/LoggedInPage";
        }

        return "pages/RegistrationForm";
    }

    @RequestMapping(value = "/submitRegistrationForm.html", method = RequestMethod.POST)
    public String submitRegistrationForm(@ModelAttribute("person") Person person) {


        if (person.getPersonName().isEmpty()) {
            return "pages/RegistrationFailure";
        }


        personService.registerPerson(person);

        return "pages/RegistrationSuccess";
    }

    @RequestMapping(value = "/ControlPanel.html")
     public String getControlPanel(HttpServletRequest request) {

        String personName = (String) request.getSession().getAttribute("personName");

        if (personName==null) {
            return "pages/LoginForm";
        }

        request.getSession().setAttribute("person", personService.findPerson(personName));

        return "pages/ControlPanel";
    }

    @RequestMapping(value = "/RegisteredPersons.html")
    public String showRegisteredPersons(HttpServletRequest request) {

        request.getSession().setAttribute("personMap", personService.getPersonMap());

        return "pages/RegisteredPersons";
    }

    @RequestMapping(value = "/CreateEventForm.html")
    public String createEvent() {

        return "pages/CreateEventForm";
    }

    @RequestMapping(value = "/submitCreateEventForm.html")
     public String submitCreateEvent(@ModelAttribute("event") EventClone eventClone,
                                     @RequestParam("allAttenders") String allAttenders,
                                     HttpServletRequest request) {

        List<String> attendersList = Arrays.asList(allAttenders.split(" "));
        for (String login : attendersList) {
            personService.findPerson(login).addEvent(eventClone);
        }

        eventClone.setAttendersLogins(attendersList);

        request.getSession().setAttribute("event", eventClone);

        return "pages/ControlPanel";
    }

    @RequestMapping(value = "/ShowEvents.html")
    public String submitCreateEvent(HttpServletRequest request, HttpServletResponse response) {


        Person person = personService.findPerson((String) request.getSession().getAttribute("personName"));

        request.getSession().setAttribute("person", person);

        return "pages/ShowEvents";
    }
}
