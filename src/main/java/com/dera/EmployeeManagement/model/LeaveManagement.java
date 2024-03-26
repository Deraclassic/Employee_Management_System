package com.dera.EmployeeManagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "leave_records")
public class LeaveManagement extends BaseClass{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "employee_id")
        private String employeeName;

        @Column(name = "leave_type")
        private String leaveType;

        @Column(name = "home_phone")
        private Long homeLine;

        @Column(name = "status")
        private String leaveStatus;

        @Column(name = "start_date")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        private LocalDate leaveStartDate;

        @Column(name = "end_date")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        private LocalDate leaveEndDate;

}
