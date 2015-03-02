package com.home.mvc;

import com.home.common.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("myRequestObject")
public class MainController {

    static Map<String, Person> personMap = new HashMap<String, Person>();

    @ModelAttribute
    public void addingCommonObjects(Model model) {
        model.addAttribute("headerMessage", "ONLINE CALENDAR");
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

        Person person = personMap.get(login);

        if (personMap.containsKey(login) && person.getPassword().equals(password)) {
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
    public String submitRegistrationForm(@ModelAttribute("person") Person person, HttpServletRequest request) {


        if (person.getPersonName().isEmpty()) {
            return "pages/RegistrationFailure";
        }


        personMap.put(person.getPersonName(), person);

        return "pages/RegistrationSuccess";
    }

    @RequestMapping(value = "/ControlPanel.html")
     public String getControlPanel(HttpServletRequest request) {

        String personName = (String) request.getSession().getAttribute("personName");

        if (personName==null) {
            return "pages/LoginForm";
        }

        return "pages/ControlPanel";
    }
}
