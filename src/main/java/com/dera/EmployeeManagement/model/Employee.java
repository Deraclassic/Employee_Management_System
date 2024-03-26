package com.dera.EmployeeManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee extends BaseClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_email", unique = true)
    private String employeeEmail;

    @Column(name = "employee_phone")
    private Long employeePhone;

    @Column(name = "employee_gender")
    private String employeeGender;

    @Column(name = "employee_salary")
    private String employeeSalary;

    @Column(name = "employee_role")
    private String employeeRole;

    @Column(name = "employee_password")
    private String employeePassword;
}

