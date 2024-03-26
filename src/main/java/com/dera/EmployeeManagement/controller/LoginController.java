package com.dera.EmployeeManagement.controller;

import com.dera.EmployeeManagement.model.Employee;
import com.dera.EmployeeManagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new Employee()); // Note the change here
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("login") Employee login, Model model) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeEmail(login.getEmployeeEmail());
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (employee.getEmployeePassword().equals(login.getEmployeePassword())) {
                // Redirect to a successful login page or dashboard
                return "redirect:/";
            } else {
                model.addAttribute("loginError", "Invalid password.");
                return "login";
            }
        } else {
            model.addAttribute("loginError", "No account found with that email.");
            return "login";
        }
    }

}