package com.dera.EmployeeManagement.controller;//package com.dera.EmployeeManagement.controller;
//
//import com.dera.EmployeeManagement.model.Employee;
//import com.dera.EmployeeManagement.repositories.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Optional;
//
//@Controller
//public class AdminLoginController {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @GetMapping("/admin_login")
//    public String showLoginForm(Model model) {
//        model.addAttribute("admin_login", new Employee()); // Note the change here
//        return "admin_login";
//    }
//
//    @PostMapping("/admin_login")
//    public String processLogin(@ModelAttribute("admin_login") Employee admin_login, Model model) {
//        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeEmail(admin_login.getEmployeeEmail());
//        if (employeeOptional.isPresent()) {
//            Employee employee = employeeOptional.get();
//            if (employee.getEmployeePassword().equals(admin_login.getEmployeePassword())) {
//                // Redirect to a successful login page or dashboard
//                return "redirect:/";
//            } else {
//                model.addAttribute("loginError", "Invalid password.");
//                return "login";
//            }
//        } else {
//            model.addAttribute("loginError", "No account found with that email.");
//            return "login";
//        }
//    }


import com.dera.EmployeeManagement.model.Employee;
import com.dera.EmployeeManagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

    @Controller
    public class AdminLoginController {

        @Autowired
        private EmployeeRepository employeeRepository;
        @Value("${ADMIN_TOKEN}")
        private String adminToken;

        @GetMapping("/admin_login")
        public String showLoginForm(Model model) {
            model.addAttribute("admin_login", new Employee());
            return "admin_login";
        }

        @PostMapping("/admin_login")
        public String processLogin(@ModelAttribute("admin_login") Employee admin_login,
                                   @RequestParam("adminToken") String adminToken,
                                   Model model) {
            if (!adminToken.equals(adminToken)) {
                model.addAttribute("loginError", "Invalid admin token.");
                return "admin_login";
            }

            Optional<Employee> employeeOptional = employeeRepository.findByEmployeeEmail(admin_login.getEmployeeEmail());
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                if (employee.getEmployeePassword().equals(admin_login.getEmployeePassword())) {
                    return "redirect:/";
                } else {
                    model.addAttribute("loginError", "Invalid password.");
                    return "admin_login";
                }
            } else {
                model.addAttribute("loginError", "No account found with that email.");
                return "admin_login";
            }
        }




}