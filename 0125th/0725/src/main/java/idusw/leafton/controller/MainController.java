package idusw.leafton.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/main")
@Controller
public class MainController {
    //home page mapping
    @GetMapping(value="/index")
    public String goIndex() {
        return "/main/index";
    }

    @GetMapping(value="/location")
    public String goLocation() {
        return "/main/location";
    }

}
