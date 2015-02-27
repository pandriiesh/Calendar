package com.home.mvc;

import com.home.common.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    static Map<String, Person> personMap = new HashMap<String, Person>();

    @ModelAttribute
    public void addingCommonObjects(Model model) {
        model.addAttribute("headerMessage", "ONLINE CALENDAR");
    }

    @RequestMapping(value = "/LoginForm.html", method = RequestMethod.GET)
    public ModelAndView getLoginForm() {

        ModelAndView model = new ModelAndView("pages/LoginForm");
        return model;
    }

    @RequestMapping(value = "/submitLoginForm.html", method = RequestMethod.POST)
    public ModelAndView submitLoginForm(@RequestParam("login") String login,
                                            @RequestParam("password") String password) {

        Person person = personMap.get(login);

        if (personMap.containsKey(login) && person.getPassword().equals(password)) {
            ModelAndView model = new ModelAndView("pages/LoginSuccess");
            model.addObject("person", person);
            return model;
        } else {
            ModelAndView model = new ModelAndView("pages/LoginFailure");
            model.addObject("login", login);
            model.addObject("password", password);
            return model;
        }
    }


    @RequestMapping(value = "/RegistrationForm.html", method = RequestMethod.GET)
    public ModelAndView getRegistrationForm() {

        ModelAndView model = new ModelAndView("pages/RegistrationForm");
        return model;
    }

    @RequestMapping(value = "/submitRegistrationForm.html", method = RequestMethod.POST)
    public ModelAndView submitRegistrationForm(@ModelAttribute("person") Person person) {

        if (person.getPersonName().isEmpty()) {
            ModelAndView model = new ModelAndView("pages/RegistrationFailure");
            model.addObject(person);
            return model;
        }

        personMap.put(person.getPersonName(), person);

        ModelAndView model = new ModelAndView("pages/RegistrationSuccess");
        model.addObject(person);
        return model;
    }

    @RequestMapping(value = "/ControlPanel.html")
    public ModelAndView getAccountView() {

        ModelAndView model = new ModelAndView("pages/ControlPanel");
        return model;
    }
}
