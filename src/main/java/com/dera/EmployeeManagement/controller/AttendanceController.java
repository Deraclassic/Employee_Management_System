package com.dera.EmployeeManagement.controller;

import com.dera.EmployeeManagement.exceptions.AttendanceNotFoundException;
import com.dera.EmployeeManagement.model.Attendance;
import com.dera.EmployeeManagement.dto.ConfirmationForm;
import com.dera.EmployeeManagement.repositories.AttendanceRepository;
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
public class AttendanceController {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @GetMapping("/attendance")
    public String getIndex(Model model) {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        model.addAttribute("attendance_records", attendanceList);
        model.addAttribute("attendance_record", new Attendance());
        model.addAttribute("confirmationForm", new ConfirmationForm());
        return "/attendance";
    }
    @PostMapping("/createAttendance")
    public String newAttendanceRecord(@ModelAttribute("attendance") Attendance attendance_record,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        model.addAttribute("attendance_record", new Attendance());
        model.addAttribute("redirect", "/dashboard");

        Random random = new Random();
        Long randomNumber = 1000 + random.nextLong(9000);
        Long leave_Id = randomNumber;
        attendance_record.setId(leave_Id);

        attendanceRepository.save(attendance_record);
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.contains("attendance?openModal=addAttendance")) {
            return "redirect:/attendance";
        }

        return "redirect:/dashboard";
    }

    @PostMapping("/updateAttendance")
    public String updateLeave(@ModelAttribute Attendance attendance_record, Model model) throws AttendanceNotFoundException {
        model.addAttribute("attendance_record", new Attendance());
        Optional<Attendance> existingAttendanceRecord = attendanceRepository.findById(attendance_record.getId());
        if (existingAttendanceRecord.isPresent()) {
            attendanceRepository.save(attendance_record);
        } else {
            model.addAttribute("errorMessage", "Attendance Record with ID " + attendance_record.getId() + " not found.");
        }
        return "redirect:/attendance";
    }
    @PostMapping("/removeAttendance")
    public String removeLeaveRecord(Attendance attendance_record, Model model) {
        model.addAttribute("attendance_record", new Attendance());
        Optional<Attendance> existingAttendanceRecord = attendanceRepository.findById(attendance_record.getId());
        if (existingAttendanceRecord.isPresent()) {
            attendanceRepository.deleteById(attendance_record.getId());
        }
        return "redirect:/attendance";
    }

    @PostMapping("/remove/allAllAttendance")
    public String removeAll(@ModelAttribute ConfirmationForm confirmationForm, Model model) {
        String confirmation = confirmationForm.getConfirmation();
        if ("Yes".equalsIgnoreCase(confirmation)) {
            attendanceRepository.deleteAll();
        } else {
            return "redirect:/attendance";
        }
        return "redirect:/attendance";
    }

}
