package com.dera.EmployeeManagement.controller;

import com.dera.EmployeeManagement.model.LeaveManagement;
import com.dera.EmployeeManagement.pojo.ConfirmationForm;
import com.dera.EmployeeManagement.repositories.LeaveManagementRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
public class LeaveManagementController {
    @Autowired
        private LeaveManagementRepository leaveManagementRepository;
        @GetMapping("/leave")
        public String getIndex(Model model) {
            List<LeaveManagement> leaveList = leaveManagementRepository.findAll();
            model.addAttribute("leave_records", leaveList);
            model.addAttribute("leave_record", new LeaveManagement());
            model.addAttribute("confirmationForm", new ConfirmationForm());
            return "leave";
        }

//        @PostMapping("/createLeave")
//        public String newLeaveRecord(LeaveManagement leave_record, Model model) {
//            model.addAttribute("leave_record", new LeaveManagement());
//
//            // creating dynamic Employee ID
//            Random random = new Random();
//            Long randomNumber = 1000 + random.nextLong(9000);
//            Long leave_Id = randomNumber;
//            leave_record.setId(leave_Id);
//
//            // save the employee
//            leaveManagementRepository.save(leave_record);
//
//            return "redirect:/leave";
//        }
@PostMapping("/createLeave")
public String newLeaveRecord(@ModelAttribute("leaveManagement") LeaveManagement leave_record,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes,
                             Model model) {
    model.addAttribute("leave_record", new LeaveManagement());
    model.addAttribute("redirect", "/dashboard");

    // creating dynamic Employee ID
    Random random = new Random();
    Long randomNumber = 1000 + random.nextLong(9000);
    Long leave_Id = randomNumber;
    leave_record.setId(leave_Id);

    // save the employee
    leaveManagementRepository.save(leave_record);

    // Determine the redirect target based on the source of the request
//    if ("dashboard".equals(sourcePage)) {
//        // If the form was submitted from the Dashboard page, redirect back to the dashboard
//        return "redirect:/dashboard";
//    } else {
//        // Otherwise, redirect to the Leave Management page as before
//        return "redirect:/leave";
//    }
    String referer = request.getHeader("Referer");

    // Logic to decide if you should redirect back to the Apply For Leave page
    if (referer != null && !referer.contains("dashboard")) {
        return "redirect:/leave"; // Adjust the redirect URL as necessary
    }

    // Otherwise, redirect to a default location, such as the leave list page
    return "redirect:/dashboard";
}

    @PostMapping("/updateLeave")
        public String updateLeave(@ModelAttribute LeaveManagement leave_record, Model model) {
            model.addAttribute("leave_record", new LeaveManagement());
            Optional<LeaveManagement> existingLeaveRecord = leaveManagementRepository.findById(leave_record.getId());
            if (existingLeaveRecord.isPresent()) {
                leaveManagementRepository.save(leave_record);
            } else {
                model.addAttribute("errorMessage", "Leave Record with ID " + leave_record.getId() + " not found.");
            }
            return "redirect:/leave";
        }

        @PostMapping("/removeLeave")
        public String removeLeaveRecord(LeaveManagement leave_record, Model model) {
            model.addAttribute("leave_record", new LeaveManagement());
            Optional<LeaveManagement> existingLeaveRecord = leaveManagementRepository.findById(leave_record.getId());
            if (existingLeaveRecord.isPresent()) {
                leaveManagementRepository.deleteById(leave_record.getId());
            }
            return "redirect:/leave";
        }
        @PostMapping("/remove/allLeave")
        public String removeAll(@ModelAttribute ConfirmationForm confirmationForm, Model model) {
            String confirmation = confirmationForm.getConfirmation();
            if ("Yes".equalsIgnoreCase(confirmation)) {
                leaveManagementRepository.deleteAll();
            } else {
                return "redirect:/leave";
            }
            return "redirect:/leave";
        }
}
