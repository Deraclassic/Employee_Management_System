package com.dera.EmployeeManagement.exceptions;

import com.dera.EmployeeManagement.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(AttendanceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleAttendanceNotFoundException(final AttendanceNotFoundException e) {
        ErrorDetails errorResponse = new ErrorDetails();
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setErrorDetails(String.valueOf(HttpStatus.BAD_REQUEST));
        errorResponse.setErrorMessage(e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(LeaveRecordNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleLeaveRecordNotFoundException(final LeaveRecordNotFoundException e){
        ErrorDetails errorResponse = new ErrorDetails();
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setErrorDetails(String.valueOf(HttpStatus.BAD_REQUEST));
        errorResponse.setErrorMessage(e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
