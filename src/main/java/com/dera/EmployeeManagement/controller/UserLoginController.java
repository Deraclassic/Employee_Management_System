package com.dera.EmployeeManagement.controller;

import com.dera.EmployeeManagement.model.Employee;
import com.dera.EmployeeManagement.repositories.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
@Controller
public class UserLoginController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/user_login")
    public String showLoginForm(Model model) {
        model.addAttribute("user_login", new Employee()); // Note the change here
        return "user_login";
    }

//    @PostMapping("/user_login")
//    public String processLogin(@ModelAttribute("user_login") Employee user_login, Model model) {
//        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeEmail(user_login.getEmployeeEmail());
//        if (employeeOptional.isPresent()) {
//            Employee employee = employeeOptional.get();
//            if (employee.getEmployeePassword().equals(user_login.getEmployeePassword())) {
//                // Redirect to a successful login page or dashboard
//                return "redirect:/dashboard";
//            } else {
//                model.addAttribute("userLoginError", "Invalid password.");
//                return "user_login";
//            }
//        } else {
//            model.addAttribute("userLoginError", "No account found with that email.");
//            return "user_login";
//        }
//    }

    @PostMapping("/user_login")
    public String processLogin(@ModelAttribute("user_login") Employee user_login, Model model, HttpSession session) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeEmail(user_login.getEmployeeEmail());
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (employee.getEmployeePassword().equals(user_login.getEmployeePassword())) {
                session.setAttribute("loggedInEmployee", employee); // Store employee in session
                return "redirect:/dashboard";
            } else {
                model.addAttribute("userLoginError", "Invalid password.");
                return "user_login";
            }
        } else {
            model.addAttribute("userLoginError", "No account found with that email.");
            return "user_login";
        }
    }

}
