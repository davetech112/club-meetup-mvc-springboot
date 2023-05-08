package com.rungroup.runApp.controller;

import com.rungroup.runApp.dto.ClubDto;
import com.rungroup.runApp.dto.EventDto;
import com.rungroup.runApp.models.Event;
import com.rungroup.runApp.models.UserEntity;
import com.rungroup.runApp.security.SecurityUtil;
import com.rungroup.runApp.service.EventService;
import com.rungroup.runApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EventController {
    private EventService eventService;
    private UserService userService;
    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/events")
    public String eventList(Model model){
        UserEntity user = new UserEntity();
        List<EventDto> events = eventService.findAllEvents();
        if(user != null){
            String userEmail = SecurityUtil.getSessionUser();
            user = userService.findByEmail(userEmail);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("events", events);
        return "events-list";
    }
    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") Long eventId, Model model){
        UserEntity user = new UserEntity();
        if(user != null){
            String userEmail = SecurityUtil.getSessionUser();
            user = userService.findByEmail(userEmail);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        EventDto eventDto = eventService.findByEventId(eventId);
        model.addAttribute("event", eventDto);
        return "events-detail";
    }
    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") Long eventId, Model model){
        EventDto eventDto = eventService.findByEventId(eventId);
        model.addAttribute("event", eventDto);
        return "events-edit";
    }
    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId") Long eventId, @Valid @ModelAttribute("event")
    EventDto eventDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("event", eventDto);
            return "/events-edit";
        }
        EventDto eventDto1 = eventService.findByEventId(eventId);
        eventDto.setId(eventDto1.getId());
        eventDto.setClub(eventDto1.getClub());
        eventService.updateEvent(eventDto);
        return "redirect:/events";
    }

    @GetMapping("/events/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Long clubId, Model model){
        String user = SecurityUtil.getSessionUser();
        if(user == null) {
            return "login";
        }
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }
    @PostMapping("/events/{clubId}")
    public String createEvent(@PathVariable("clubId") Long clubId, @ModelAttribute("event")EventDto eventDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("event", eventDto);
            return "/clubs-create";
        }
        System.out.println("create controller");
        eventService.createEvent(clubId, eventDto);
        return "redirect:/clubs/"+clubId;
    }
    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Long eventId){
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }

}
