package com.example.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.*;
import com.example.repository.*;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PostMapping("/login")
	public String login(@RequestParam("email") String email, 
	                    @RequestParam("password") String password, 
	                    RedirectAttributes redirectAttributes) {
	    // Find user by email
	    User user = userDao.findByEmail(email);

	    if (user == null) {
	        redirectAttributes.addFlashAttribute("error", "Invalid email or password.");
	        return "redirect:/login"; // Redirect back to login page
	    }

	    // Verify password using BCrypt
	    //if (!passwordEncoder.matches(password, user.getPassword())) {
	    if ( !password.equals(passwordEncoder.encode( user.getPassword() )) ) {
	        redirectAttributes.addFlashAttribute("error", "Invalid email or password.");
	        return "redirect:/login"; // Redirect back to login page
	    }

	    // Save user in session (or implement proper session management)
	    //session.setAttribute("loggedInUser", user);

	    return "redirect:/dashboard"; // Redirect to the dashboard
	}
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; 
    }
	
	@GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to TVPSS Information Management System.");
        return "index"; // Corresponds to /templates/home.html
    }
	
    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
    	
        // Fetch user from database 
        User user = fetchLoggedInUser();
        
        if (user != null) {
        	Set<Authority> authorities = user.getAuthorities();
            model.addAttribute("message", "Welcome, " + user.getFullname() + "!");
            for (Authority authority : authorities) {
            	model.addAttribute("user_authority", authority.getAuthority()); // To differentiate the provided options
            }
        } else {
            model.addAttribute("message", "Welcome, Guest!");
        }
        return "dashboard"; // Maps to dashboard
    }
    
    @GetMapping("/viewProfile")
    public String userProfile(Model model) {

        // Fetch user details from the database
        User user = fetchLoggedInUser();

        if (user != null) {
            model.addAttribute("user", user);

            // Fetch user roles from authorities table 
            Set<Authority> authorities = user.getAuthorities();
            for (Authority authority : authorities) {
            	model.addAttribute("role", authority.getAuthority()); // To differentiate the provided options
            }
        }

        return "viewProfile"; // Render the profile.html template
    }
    
    @GetMapping("/changePassword")
    public String changePassPage() {
        return "changePasswordPage"; // Render the changePassword.html template
    }
    @PostMapping("/changePassword")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        User user = fetchLoggedInUser();

        // Check if current password matches the one in the database
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("message", "Current password is incorrect.");
            return "changePassword";
        }

        // Check if new password matches confirm password
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("message", "New password and confirm password do not match.");
            return "changePassword";
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(newPassword));
        userDao.save(user); // Update the user in the database

        model.addAttribute("message", "Password changed successfully.");
        return "redirect:/viewProfile";
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
	
	private User fetchLoggedInUser() {
        String email = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return userDao.findByEmail(email);
    }

}
