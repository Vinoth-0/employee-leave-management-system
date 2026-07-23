package com.assessment.leavemgmt.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Employee with email '" + email + "' already exists");
    }
}
