package com.home.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @ModelAttribute
    public void addingCommonObjects(Model model) {
        model.addAttribute("headerMessage", "THE BEST ONLINE CALENDAR");
    }

    @RequestMapping(value = "/LoginForm.html", method = RequestMethod.GET)
    public ModelAndView getAdmissionForm() {

        ModelAndView model = new ModelAndView("pages/LoginForm");
        return model;
    }

    @RequestMapping(value = "/submitLoginForm.html", method = RequestMethod.POST)
    public ModelAndView submitAdmissionForm(@RequestParam("login") String login,
                                            @RequestParam("password") String password) {

        if (login.equalsIgnoreCase(password)) {
            ModelAndView model = new ModelAndView("pages/LoginSuccess");
            model.addObject("login", login);
            return model;
        } else {
            ModelAndView model = new ModelAndView("pages/LoginFailure");
            model.addObject("login", login);
            model.addObject("password", password);
            return model;
        }
    }
}
