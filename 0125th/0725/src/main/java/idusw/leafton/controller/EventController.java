package idusw.leafton.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/event")
@Controller
public class EventController {
    //index page mapping
    @GetMapping(value="/index")
    public String goIndex() { return "event/index";}

    //sale page mapping
    @GetMapping(value="/sale")
    public String goSale() { return "event/sale";}
}
