package com.home.mvc;

import com.home.common.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @ModelAttribute
    public void addingCommonObjects(Model model) {
        model.addAttribute("headerMessage", "THE BEST ONLINE CALENDAR");
    }

    @RequestMapping(value = "/RegistrationForm.html", method = RequestMethod.GET)
    public ModelAndView getAdmissionForm() {

        ModelAndView model = new ModelAndView("pages/RegistrationForm");
        return model;
    }

    @RequestMapping(value = "/submitRegistrationForm.html", method = RequestMethod.POST)
    public ModelAndView submitAdmissionForm(@RequestParam("personName") String personName,
                                            @RequestParam("personEmail") String personEmail) {

        Person person = new Person();
        person.setPersonName(personName);
        person.setPersonEmail(personEmail);
        if (personName.equalsIgnoreCase("Pavlo")) {
            ModelAndView model = new ModelAndView("pages/RegistrationSuccess");
            model.addObject(person);
            return model;
        } else {
            ModelAndView model = new ModelAndView("pages/RegistrationFailure");
            model.addObject(person);
            return model;
        }
    }
}
