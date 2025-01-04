package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import java.util.ArrayList;
//import java.util.List;

//import com.example.entity.Event;
import com.example.repository.EventDao;

@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private EventDao eventDao;

    @GetMapping("/displayEvents")
    public String displayEvents(Model model) {
        // List of sample events
        //List<Event> events = new ArrayList<Event>();
        //events.add(new Event(1, "Book Fair", "2024-12-10", "Skudai: A grand book fair with many great authors attending.", "/images/bookfair.jpeg"));
        //events.add(new Event(2, "Tech Conference", "2024-12-15", "Batu Pahat: A conference showcasing the latest technology trends.", null)); 
        //events.add(new Event(3, "Art Exhibition", "2024-12-20", "Tangkak: An exhibition of modern and traditional art.", "/images/artexhibition.jpg"));

        model.addAttribute("events", eventDao.findAll());
        return "eventDisplay"; // Refers to eventDisplay.jsp
    }
   
}
