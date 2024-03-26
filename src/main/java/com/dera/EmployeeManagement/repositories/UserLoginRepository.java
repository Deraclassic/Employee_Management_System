package com.dera.EmployeeManagement.repositories;

import com.dera.EmployeeManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<Employee, Long>{
    Optional<Employee> findById(Long id);
}
