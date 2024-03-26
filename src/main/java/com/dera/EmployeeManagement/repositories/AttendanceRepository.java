package com.dera.EmployeeManagement.repositories;

import com.dera.EmployeeManagement.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findById(Long id);
}
