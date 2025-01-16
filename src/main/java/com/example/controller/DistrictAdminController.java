//DistrictAdminController.java
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
@RequestMapping("/districtProgram")
public class DistrictAdminController {

    private final SchoolProgramInfoDAO schoolProgramInfoDAO;

    @Autowired
    public DistrictAdminController(SchoolProgramInfoDAO schoolProgramInfoDAO) {
        this.schoolProgramInfoDAO = schoolProgramInfoDAO;
    }

    @GetMapping("/programs")
    public String listSchoolPrograms(Model model) {
        List<SchoolProgramInfo> schoolProgramInfo = schoolProgramInfoDAO.findAll();
        model.addAttribute("message", "Admin: TV PSS Program Information");
        model.addAttribute("schoolProgramInfo", schoolProgramInfo);
        return "district-listprogram";
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
        return "district-listprogram";
    }

    @GetMapping("/view/{schoolCode}")
    public String viewSchoolProgram(@PathVariable String schoolCode, Model model) {
        SchoolProgramInfo school = schoolProgramInfoDAO.findBySchoolCode(schoolCode);
        if (school == null) {
            model.addAttribute("errorMessage", "School Program with School Code " + schoolCode + " not found.");
            return "errorPage"; // Redirect to an error page or display an error message
        }
        model.addAttribute("school", school);
        return "district-viewprogram";
    }

    @GetMapping("/approve/{id}")
    public String approveSchoolProgram(@PathVariable int id) {
        SchoolProgramInfo school = schoolProgramInfoDAO.findById(id);
        if (school != null) {
            school.setStatus("Approved");
            school.setValidationDate(new Date());
            schoolProgramInfoDAO.update(school);
        }
        return "redirect:/districtProgram/programs";
    }

    @GetMapping("/reject/{id}")
    public String rejectSchoolProgram(@PathVariable int id) {
        SchoolProgramInfo school = schoolProgramInfoDAO.findById(id);
        if (school != null) {
            school.setStatus("Rejected");
            school.setValidationDate(new Date());
            schoolProgramInfoDAO.update(school);
        }
        return "redirect:/districtProgram/programs";
    }
}