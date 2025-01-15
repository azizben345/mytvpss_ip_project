package com.example.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Authority;
import com.example.entity.User;
import com.example.repository.UserDao;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserDao userDAO; // field-based
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; // Autowire the encoder, field-based
	
	@GetMapping("/register")
	public String register() {
		return "admin/register";
	}
	@PostMapping("/register")
	public String register(String email, String fullname, String password, String confirmPassword, String role, Model model) {
	    if (!password.equals(confirmPassword)) {
	        model.addAttribute("error", "Passwords do not match!");
	        model.addAttribute("email", email); // To retain entered data
	        return "register"; // Return the registration form with an error
	    }

	    if (userDAO.findByEmail(email) != null) {
	        model.addAttribute("error", "Email already exists!");
	        model.addAttribute("username", email); // To retain entered data
	        return "register"; // Return the registration form with an error
	    }

	    // Create User entity
	    User user = new User();
	    user.setEmail(email);
	    user.setFullname(fullname);
	    user.setPassword(passwordEncoder.encode(password));
	    user.setEnabled(true);

	    // Assign Role
	    Authority authority = new Authority();
	    authority.setAuthority(role);
	    authority.setUser(user);

	    Set<Authority> authorities = new HashSet<>();
	    authorities.add(authority);
	    user.setAuthorities(authorities);

	    // Save User
	    userDAO.save(user);

	    return "redirect:/admin/viewAllUsers"; // Redirect to user list
	}
	
	@GetMapping("/viewAllUsers")
    public String viewAllUsers(Model model) {
        List<User> users = userDAO.findAll();
        model.addAttribute("users", users);
        return "admin/userList"; 
    }
	
	// Ban a user (set enabled = false)
    @PostMapping("/updateEnabled")
    public String updateUserEnabled(@RequestParam("email") String email, @RequestParam("enabled") boolean enabled) {
        User user = userDAO.findByEmail(email);
        if (user != null) {
            user.setEnabled(enabled);
            userDAO.save(user); // Save the updated user
        }
        return "redirect:/admin/viewAllUsers"; // Redirect back to user list
    }
    
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String email) {
        userDAO.delete(email); 
        return "redirect:/admin/viewAllUsers"; 
    }
}
