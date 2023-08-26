package com.example.contactmanagementapi.domain.errors;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException() {
        super("Resource already exists.");
    }
}