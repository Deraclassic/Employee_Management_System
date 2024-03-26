package com.dera.EmployeeManagement.controller;

import com.dera.EmployeeManagement.model.Employee;
import com.dera.EmployeeManagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/dashboard")
    public String showLoginForm(Model model) {
        model.addAttribute("dashboard", new Employee()); // Note the change here
        return "dashboard";
    }
}
