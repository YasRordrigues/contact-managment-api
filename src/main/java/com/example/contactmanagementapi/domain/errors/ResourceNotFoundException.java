package com.example.contactmanagementapi.domain.errors;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Resource not found.");
    }
}