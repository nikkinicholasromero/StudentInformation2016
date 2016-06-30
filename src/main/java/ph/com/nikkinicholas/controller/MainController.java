package ph.com.nikkinicholas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by nikkinicholas on 6/24/16.
 */
@Controller
public class MainController {
    @RequestMapping({"", "/", "/home", "/index"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }
}