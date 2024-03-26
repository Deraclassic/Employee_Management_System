package com.dera.EmployeeManagement.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.dera.EmployeeManagement.exceptions.EntityNotFoundException;
import com.dera.EmployeeManagement.dto.ConfirmationForm;
import com.dera.EmployeeManagement.model.Employee;
import com.dera.EmployeeManagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping("/")
    public String getIndex(Model model) {
        List<Employee> employeeList = employeeRepository.findAll();
        model.addAttribute("employees", employeeList);
        model.addAttribute("employee", new Employee());
        model.addAttribute("confirmationForm", new ConfirmationForm());
        return "index";
    }

    // Insert employee data
    @PostMapping("/create")
    public String newEmployee(Employee employee, Model model) throws EntityNotFoundException {
        model.addAttribute("employee", new Employee());

        // creating dynamic Employee ID
        Random random = new Random();
        long randomNumber = 1000 + random.nextInt(9000);
        Long empId = randomNumber;
        employee.setId(empId);

        // save the employee
        employeeRepository.save(employee);

        return "redirect:/";
    }
    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute Employee employee, Model model) throws EntityNotFoundException{
        model.addAttribute("employee", new Employee());
        Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());
        if (existingEmployee.isPresent()) {
            employeeRepository.save(employee);
        } else {
            model.addAttribute("errorMessage", "Employee with ID " + employee.getId() + " not found.");
        }
        return "redirect:/";
    }

    @PostMapping("/remove")
    public String removeEmployee(Employee employee, Model model) {
        model.addAttribute("employee", new Employee());
        Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());
        if (existingEmployee.isPresent()) {
            employeeRepository.deleteById(employee.getId());
        }
        return "redirect:/";
    }

    @PostMapping("/remove/all")
    public String removeAll(@ModelAttribute ConfirmationForm confirmationForm, Model model) {
        String confirmation = confirmationForm.getConfirmation();
        if ("Yes".equalsIgnoreCase(confirmation)) {
            employeeRepository.deleteAll();
        } else {
            return "redirect:/";
        }
        return "redirect:/";
    }

}

