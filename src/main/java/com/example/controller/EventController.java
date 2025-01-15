package com.example.controller;

import org.springframework.stereotype.Controller;



import com.example.entity.Event;
import com.example.repository.EventDao;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/event")
public class EventController {
	
    @Autowired
    private EventDao eventDao;
       
    @GetMapping("/view")
    public String getAllEvents(Model model) {
        List<Event> events = eventDao.findAll();
        model.addAttribute("event", events);
        model.addAttribute("message", "New Event has been updated, Check It Out Now!!!");
        return "event/viewEventUser"; 
    }
    
    @GetMapping("/out")
    public String logoutPage(Model model) {
        model.addAttribute("title", "Logout Successful");
        model.addAttribute("message", "You have been successfully logged out. Come back soon!");
        return "recruitment/out"; // Ensure this matches the Thymeleaf file name
    }	
    
//    ADMIN CONTROLLER
    
    @GetMapping("/admin/view")
    public String getAllEventsAdmin(Model model) {
        List<Event> events = eventDao.findAll();
        model.addAttribute("event", events);
        return "event/viewEvent"; 
    }
    
    @GetMapping("/admin/create")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event/createEvent"; 
    }

    @PostMapping
    public String saveEvent(@ModelAttribute("event") Event event) {
        eventDao.saveOrUpdate(event);
        return "redirect:/event/admin/view";
    }

    @GetMapping("/admin/edit/{id}")
    public String showUpdateEventForm(@PathVariable("id") int id, Model model) {
    	 Event event = eventDao.findById(id); // Find the event by ID
         model.addAttribute("event", event);
        return "event/editEvent"; 
    }

    @PostMapping("/admin/edit/{id}")
    public String updateEvent(@PathVariable("id") int id, @ModelAttribute("event") Event event) {
        event.setId(id);
        eventDao.saveOrUpdate(event);
        return "redirect:/event/admin/view";
    }
    
    // DELETE: Remove an event
    @GetMapping("/admin/delete/{id}")
    public String deleteEvent(@PathVariable("id") int id) {
    	eventDao.deleteById(id);
        return "redirect:/event/admin/view";
    }
}    