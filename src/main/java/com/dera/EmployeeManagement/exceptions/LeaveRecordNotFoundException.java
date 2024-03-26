package com.dera.EmployeeManagement.exceptions;

public class LeaveRecordNotFoundException extends RuntimeException{
    public LeaveRecordNotFoundException(String message) {
        super(message);
    }
}
