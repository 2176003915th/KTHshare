package idusw.leafton.controller;

import com.mysql.cj.log.Log;
import idusw.leafton.model.DTO.EventDTO;
import idusw.leafton.model.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventController {
    @Autowired
    EventService eventService;

    //index page mapping
    @GetMapping(value="/event/index")
    public String goIndex(HttpServletRequest request) {
        request.setAttribute("eventList", eventService.getAll());

        return "/event/index";
    }

    //admin event register page mapping
    @GetMapping(value = "/admin/event/register")
    public String goAdminRegister(HttpServletRequest request) {
        return "/admin/event/register";
    }

    //admin event list page mapping
    @GetMapping(value = "/admin/event/list")
    public String goAdminList(HttpServletRequest request) {
        request.setAttribute("eventList", eventService.getAll());
        return "/admin/event/list";
    }

    //admin event edit page mapping
    @GetMapping(value = "/admin/event/edit")
    public String goEdit(HttpServletRequest request) {
        EventDTO eventDTO = eventService.getEventById(Long.valueOf(request.getParameter("eventId")));
        request.setAttribute("event", eventDTO);

        return "/admin/event/edit";
    }

    //이벤트 등록
    @PostMapping(value = "/admin/event/register")
    public String register(HttpServletRequest request, @ModelAttribute EventDTO eventDTO) {
        //eventService.insert();
        return "redirect:/admin/event/list";
    }
}