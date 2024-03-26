package com.dera.EmployeeManagement.repositories;

import com.dera.EmployeeManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//    Optional<Employee> findById(Integer leaveId);
    Optional<Employee> findByEmployeeEmail(String employeeEmail);
}


