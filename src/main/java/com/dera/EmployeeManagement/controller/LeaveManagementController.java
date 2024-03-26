package com.dera.EmployeeManagement.controller;

import com.dera.EmployeeManagement.exceptions.LeaveRecordNotFoundException;
import com.dera.EmployeeManagement.model.LeaveManagement;
import com.dera.EmployeeManagement.dto.ConfirmationForm;
import com.dera.EmployeeManagement.repositories.LeaveManagementRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

@PostMapping("/createLeave")
public String newLeaveRecord(@ModelAttribute("leaveManagement") LeaveManagement leave_record,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes,
                             Model model) {
    model.addAttribute("leave_record", new LeaveManagement());
    model.addAttribute("redirect", "/dashboard");
    Random random = new Random();
    Long randomNumber = 1000 + random.nextLong(9000);
    Long leave_Id = randomNumber;
    leave_record.setId(leave_Id);
    leaveManagementRepository.save(leave_record);
    String referer = request.getHeader("Referer");
    System.out.println("ref " + referer);

    if (referer != null && !referer.contains("leave?openModal=addLeave")) {
        return "redirect:/leave"; // Adjust the redirect URL as necessary
    }
    return "redirect:/dashboard";
}

    @PostMapping("/updateLeave")
        public String updateLeave(@ModelAttribute LeaveManagement leave_record, Model model) throws LeaveRecordNotFoundException {
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
