package idusw.leafton.controller;

import com.mysql.cj.log.Log;
import idusw.leafton.model.DTO.EventDTO;
import idusw.leafton.model.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class EventController {
    @Autowired
    EventService eventService;

    //index page mapping
    @GetMapping(value="event/index")
    public String goIndex(HttpServletRequest request) {
        request.setAttribute("eventList", eventService.getAll());

        return "event/index";
    }

    //admin event register page mapping
    @GetMapping(value = "admin/event/register")
    public String goAdminRegister(HttpServletRequest request) {
        return "admin/event/register";
    }

    //admin event list page mapping
    @GetMapping(value = "admin/event/list")
    public String goAdminList(HttpServletRequest request) {
        request.setAttribute("eventList", eventService.getAll());
        return "admin/event/list";
    }

    //admin event edit page mapping
    @GetMapping(value = "admin/event/edit")
    public String goEdit(HttpServletRequest request) {
        System.out.println(request.getParameter("eventId"));
        EventDTO eventDTO = eventService.getEventById(Long.valueOf(request.getParameter("eventId")));
        request.setAttribute("event", eventDTO);
        return "admin/event/edit";
    }

    //이벤트 등록
    @PostMapping(value = "admin/event/register")
    public String register(HttpServletRequest request,
                           @RequestParam("event-main-image") MultipartFile mainImage,
                           @RequestParam("event-sub-image") MultipartFile subImage,
                           @RequestParam("event-thumb-image") MultipartFile thumbImage,
                           @ModelAttribute EventDTO eventDTO) throws IOException {
        System.out.println(eventDTO.getTitle());
        eventService.save(eventDTO, mainImage, subImage, thumbImage);
        return "redirect:/admin/event/list";
    }

    @PostMapping(value = "admin/event/edit")
    public String edit(HttpServletRequest request,
                           @RequestParam("event-main-image") MultipartFile mainImage,
                           @RequestParam("event-sub-image") MultipartFile subImage,
                           @RequestParam("event-thumb-image") MultipartFile thumbImage,
                           @ModelAttribute EventDTO eventDTO) throws IOException {
        eventService.save(eventDTO, mainImage, subImage, thumbImage);
        return "redirect:/admin/event/list";
    }

}