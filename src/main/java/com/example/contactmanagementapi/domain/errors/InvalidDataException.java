package com.example.contactmanagementapi.domain.errors;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException() {
        super("Invalid data provided.");
    }
}