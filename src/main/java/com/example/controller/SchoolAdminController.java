//SchoolController.java
package com.example.controller;

import com.example.entity.SchoolProgramInfo;
import com.example.repository.SchoolProgramInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/schoolProgram")
public class SchoolAdminController {

    private final SchoolProgramInfoDAO schoolProgramInfoDAO;

    @Autowired
    public SchoolAdminController(SchoolProgramInfoDAO schoolProgramInfoDAO) {
        this.schoolProgramInfoDAO = schoolProgramInfoDAO;
    }

    @GetMapping("/programs")
    public String listSchoolPrograms(Model model) {
        List<SchoolProgramInfo> schoolProgramInfo = schoolProgramInfoDAO.findAll();
        model.addAttribute("message", "TV PSS Program Information");
        model.addAttribute("schoolProgramInfo", schoolProgramInfo);
        return "school-listprogram";
    }

    @GetMapping("/search")
    public String searchSchoolPrograms(@RequestParam(value = "query", required = false) String query, Model model) {
        List<SchoolProgramInfo> filteredPrograms = (query == null || query.isEmpty()) ?
                schoolProgramInfoDAO.findAll() :
                schoolProgramInfoDAO.findAll().stream()
                        .filter(school -> school.getSchoolName().toLowerCase().contains(query.toLowerCase()) ||
                                school.getSchoolCode().toLowerCase().contains(query.toLowerCase()))
                        .toList();

        model.addAttribute("message", "Search Results for: " + (query == null ? "All" : query));
        model.addAttribute("schoolProgramInfo", filteredPrograms);
        return "school-listprogram";
    }

    @GetMapping("/create")
    public String createSchoolProgram(Model model) {
        model.addAttribute("school", new SchoolProgramInfo());
        return "createprogram";
    }

    @PostMapping("/create")
    public String createSchoolProgram(@ModelAttribute SchoolProgramInfo school) {
        school.setSubmissionDate(new Date()); // Set the submission date to the current date
        school.setStatus("Submitted"); // Set the status to "Submitted"
        schoolProgramInfoDAO.save(school);
        return "redirect:/schoolProgram/programs";
    }
    
    @GetMapping("/update/{schoolCode}")
    public String showUpdateForm(@PathVariable String schoolCode, Model model) {
        SchoolProgramInfo school = schoolProgramInfoDAO.findBySchoolCode(schoolCode);
        if (school == null) {
            model.addAttribute("errorMessage", "School Program with School Code " + schoolCode + " not found.");
            return "errorPage"; // Redirect to an error page or display an error message
        }
        model.addAttribute("school", school);
        return "updateprogram";
    }

    @PostMapping("/update")
    public String updateSchoolProgram(@ModelAttribute SchoolProgramInfo school) {
        school.setSubmissionDate(new Date());
        if (school.getStatus() == null) {
            school.setStatus("Submitted"); 
        }
        schoolProgramInfoDAO.update(school);
        return "redirect:/schoolProgram/programs";
    }
}
