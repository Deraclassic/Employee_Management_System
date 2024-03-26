package com.dera.EmployeeManagement.repositories;

import com.dera.EmployeeManagement.model.LeaveManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveManagementRepository extends JpaRepository<LeaveManagement, Long> {
    Optional<LeaveManagement> findById(Long id);
}
