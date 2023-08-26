package com.example.contactmanagementapi.presentener.controllers;

import com.example.contactmanagementapi.application.dto.ContactDto;
import com.example.contactmanagementapi.domain.interfaces.services.IContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contacts")
@Slf4j
@Tag(name = "Contact Management", description = "APIs related to Contact Management")
public class ContactController {

    private final IContactService iContactService;

    @PostMapping
    @Operation(summary = "Create a new contact", description = "Creates a new contact and returns the created contact details")
    @ApiResponse(responseCode = "201", description = "Contact successfully created", content = @Content(schema = @Schema(implementation = ContactDto.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    public Mono<ResponseEntity<ContactDto>> createContact(@Valid @RequestBody ContactDto contactDto) {
        log.info("Creating a new contact: {}", contactDto);
        return iContactService.createContact(contactDto)
                .doOnSuccess(createdContact -> log.info("Contact created: {}", createdContact))
                .map(createdContact -> new ResponseEntity<>(createdContact, HttpStatus.CREATED));
    }

    @GetMapping
    @Operation(summary = "List all contacts", description = "Returns a list of all contacts with pagination support")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of contacts list", content = @Content(schema = @Schema(implementation = ContactDto.class)))
    public Mono<Page<ContactDto>> listAllContacts(
            @Parameter(description = "Page number for pagination", required = false) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Size of each page", required = false) @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @RequestParam Map<String, String> queryParams) {
        log.info("Listing all contacts with page: {} and size: {}", page, size);
        return iContactService.listAllContacts(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find contact by ID", description = "Returns the contact details for a given ID")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of contact details", content = @Content(schema = @Schema(implementation = ContactDto.class)))
    @ApiResponse(responseCode = "404", description = "Contact not found", content = @Content)
    public Mono<ResponseEntity<ContactDto>> findContactById(@Parameter(description = "ID of the contact to retrieve") @PathVariable Long id) {
        log.info("Finding contact by ID: {}", id);
        return iContactService.findContactById(id)
                .doOnSuccess(contact -> log.info("Contact found: {}", contact))
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found")));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch a contact", description = "Update specific fields of a contact and return the updated contact details")
    @ApiResponse(responseCode = "200", description = "Contact successfully patched", content = @Content(schema = @Schema(implementation = ContactDto.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "404", description = "Contact not found", content = @Content)
    public Mono<ResponseEntity<ContactDto>> patchContact(@Parameter(description = "ID of the contact to patch") @PathVariable Long id, @Valid @RequestBody ContactDto contactDto) {
        log.info("Patching contact with ID: {} with data: {}", id, contactDto);
        return iContactService.patchContact(id, contactDto)
                .doOnSuccess(updatedContact -> log.info("Contact patched: {}", updatedContact))
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a contact", description = "Update a contact and return the updated contact details")
    @ApiResponse(responseCode = "200", description = "Contact successfully updated", content = @Content(schema = @Schema(implementation = ContactDto.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "404", description = "Contact not found", content = @Content)
    public Mono<ResponseEntity<ContactDto>> updateContact(@Parameter(description = "ID of the contact to update") @PathVariable Long id, @Valid @RequestBody ContactDto contactDto) {
        log.info("Updating contact with ID: {} with data: {}", id, contactDto);
        return iContactService.updateContact(id, contactDto)
                .doOnSuccess(updatedContact -> log.info("Contact updated: {}", updatedContact))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a contact", description = "Deletes a contact based on the given ID")
    @ApiResponse(responseCode = "204", description = "Contact successfully deleted", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "404", description = "Contact not found", content = @Content)
    public Mono<ResponseEntity<Void>> deleteContact(@Parameter(description = "ID of the contact to delete") @PathVariable Long id) {
        log.info("Deleting contact with ID: {}", id);
        return iContactService.deleteContact(id)
                .doOnSuccess(deleted -> log.info("Contact with ID: {} deleted", id))
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found")));
    }
}
