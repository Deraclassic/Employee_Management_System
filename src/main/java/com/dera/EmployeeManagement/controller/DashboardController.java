package com.dera.EmployeeManagement.controller;

import com.dera.EmployeeManagement.model.Employee;
import com.dera.EmployeeManagement.repositories.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Employee loggedInEmployee = (Employee) session.getAttribute("loggedInEmployee");
        if (loggedInEmployee != null) {
            model.addAttribute("employee", loggedInEmployee);
            return "dashboard";
        } else {
            return "redirect:/user_login";
        }
    }
}
