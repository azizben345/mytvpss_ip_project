package com.example.controller;

import org.springframework.stereotype.Controller;


import com.example.entity.Event;
import com.example.repository.EventDao;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventDao eventDao;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Thymeleaf!");
        return "index"; // Corresponds to templates/index.html
    }
    
    @GetMapping("/view")
    public String getAllEvents(Model model) {
        List<Event> events = eventDao.findAll();
        model.addAttribute("event", events);
        return "viewEvent"; 
    }
    
    @GetMapping("/create")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "createEvent"; 
    }

    @PostMapping
    public String saveEvent(@ModelAttribute("event") Event event) {
        eventDao.saveOrUpdate(event);
        return "redirect:/event/view";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateEventForm(@PathVariable("id") int id, Model model) {
    	 Event event = eventDao.findById(id); // Find the event by ID
         model.addAttribute("event", event);
        return "editEvent"; 
    }

    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable("id") int id, @ModelAttribute("event") Event event) {
        event.setId(id);
        eventDao.saveOrUpdate(event);
        return "redirect:/event/view";
    }

//    // DELETE: Remove an event
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") int id) {
    	eventDao.deleteById(id);
        return "redirect:/event/view";
    }
}    