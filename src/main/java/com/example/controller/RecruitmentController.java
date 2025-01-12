package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.*;
//import com.example.repository.EventDao;
import com.example.repository.UserDao;
import com.example.repository.RecruitmentDao;

@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {
	
	//@Autowired
	//private User loggedInUser = fetchLoggedInUser();
	@Autowired
	private UserDao userDao;
	@Autowired
	private RecruitmentDao recruitmentDao;
    
    //Controller:
    
    @GetMapping("/myRecruitments")
    public String getMyRecruitments(Model model) {
    	
    	User loggedInUser = fetchLoggedInUser();
    	
        Recruitment myRecruitmentForm = recruitmentDao.findByUser(loggedInUser);
        
        if (myRecruitmentForm != null) {
        	model.addAttribute("message", "You have submitted your application. You can check the status here.");
            model.addAttribute("myRecruitmentForm", myRecruitmentForm);
        } else {
            model.addAttribute("message", "No form submitted yet.");
        }
        
        //model.addAttribute("myRecruitmentForm", myRecruitmentForm);
        return "recruitment/myRecruitment";
    }

    @GetMapping("/recruitmentList")
    public String displayRecruitments(Model model) {
    	List<Recruitment> recruitmentForms = recruitmentDao.findAll();
    	System.out.println("Recruitments: " + recruitmentForms); // Debug output
        model.addAttribute("recruitmentForms", recruitmentForms);
        return "recruitment/recruitmentList";
    }
    
    // Show form for new recruitment
    @GetMapping("/recruitmentForm")
    public String recruitmentForm(Model model) {

    	User loggedInUser = fetchLoggedInUser();

        // Add recruitment object and user details to the model
        model.addAttribute("email", loggedInUser.getEmail());
        model.addAttribute("fullname", loggedInUser.getFullname());

        return "recruitment/recruitmentForm"; // Render the recruitment form
    }

    // Handle form submission for new recruitment
    @PostMapping("/submitForm")
    public String recruitmentForm(
            @RequestParam("class") String className,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam(value = "parentPhoneNumber", required = false) String parentPhoneNumber,
            @RequestParam("reasonForApply") String reasonForApply,
            Model model) {

    	User loggedInUser = fetchLoggedInUser();

        // Create and populate Recruitment entity
        Recruitment recruitment = new Recruitment();
        recruitment.setUser(loggedInUser); // Set the logged-in user
        recruitment.setStudentClass(className);
        recruitment.setPhoneNumber(phoneNumber);
        recruitment.setParentPhoneNumber(parentPhoneNumber);
        recruitment.setReasonForApply(reasonForApply);
        recruitment.setStatus("Pending");

        // Save Recruitment entity
        recruitmentDao.save(recruitment);

        //model.addAttribute("success", "Your application has been successfully submitted!");
        return "recruitment/myRecruitment"; 
    }
    
    @PostMapping("/updateStatus")
    public String updateRecruitmentStatus(@RequestParam("id") Long id, @RequestParam("status") String status) {
        Recruitment recruitment = recruitmentDao.findById(id);
        if (recruitment != null) {
            recruitment.setStatus(status);
            recruitmentDao.save(recruitment);
        }
        return "redirect:/recruitment/recruitmentList";
    }

    // Show form for editing recruitment
    @GetMapping("/edit/{id}")
    public String showEditRecruitment(@PathVariable Long id, Model model) {
        Recruitment recruitment = recruitmentDao.findById(id);
        if (recruitment != null) {
        	//User loggedInUser = fetchLoggedInUser();
            model.addAttribute("recruitment", recruitment);
            //model.addAttribute("email", loggedInUser.getEmail());
            //model.addAttribute("fullname", loggedInUser.getFullname());
            return "recruitment/recruitmentUpdate"; 
        }
        return "redirect:/recruitment/myRecruitment"; // Redirect if not found
    }
    @PostMapping("/edit")
    public String editRecruitment(@ModelAttribute Recruitment recruitment) {
        recruitmentDao.save(recruitment); // Call the update method 
        return "redirect:/recruitment/myRecruitment";
    }

    // Delete a recruitment
    @PostMapping("/delete/{id}")
    public String deleteRecruitment(@PathVariable Long id) {
        recruitmentDao.deleteById(id);
        return "redirect:/recruitment/recruitmentList";
    }
    
    @PostMapping("/deleteByStudent/{id}")
    public String deleteRecruitmentStudent(@PathVariable Long id) {
        recruitmentDao.deleteById(id);
        return "redirect:/recruitment/myRecruitment";
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