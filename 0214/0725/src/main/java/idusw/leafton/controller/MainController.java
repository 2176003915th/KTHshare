package idusw.leafton.controller;

import idusw.leafton.model.service.EventService;

import idusw.leafton.model.service.MainCategoryService;
import idusw.leafton.model.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/main")
@Controller
public class MainController {
    @Autowired
    EventService eventService;
    @Autowired
    MainCategoryService mainCategoryService;

    @GetMapping(value="/index")
    public String goIndex(HttpServletRequest request) {
        request.setAttribute("mainCategoryList", mainCategoryService.viewAllMainCategory());
        request.setAttribute("eventList", eventService.getAll());

        return "/main/index";
    }

    @GetMapping(value="/location")
    public String goLocation() {
        return "/main/location";
    }

}
