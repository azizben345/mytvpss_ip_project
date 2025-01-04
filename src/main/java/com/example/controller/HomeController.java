package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.*;
import com.example.repository.*;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Thymeleaf!");
        return "home"; // Corresponds to /templates/home.html
    }
	
    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        // Get the logged-in user's email
        String email = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
    	
        // Fetch user from database (replace with logged-in user logic)
        User user = userDao.findByEmail(email);
        if (user != null) {
            model.addAttribute("message", "Welcome, " + user.getFullname() + "!");
        } else {
            model.addAttribute("message", "Welcome, Guest!");
        }
        return "dashboard"; // Maps to dashboard.html in templates folder
    }

	 @GetMapping("/out")
	    public String logoutPage(Model model) {
	        model.addAttribute("title", "Logout Successful");
	        model.addAttribute("message", "You have been successfully logged out. Come back soon!");
	        return "out"; // Ensure this matches the Thymeleaf file name
	    }
	
	@RequestMapping("/test")
	@ResponseBody()
	public String test() {
		return "test";
	}

}
