package com.example.contactmanagementapi.presentener.controllers;

import com.example.contactmanagementapi.application.dto.ContactDto;
import com.example.contactmanagementapi.application.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public Mono<ResponseEntity<ContactDto>> createContact(@RequestBody ContactDto contactDto) {
        return contactService.createContact(contactDto)
                .map(createdContact -> new ResponseEntity<>(createdContact, HttpStatus.CREATED));
    }

    @GetMapping
    public Flux<ContactDto> listAllContacts() {
        return contactService.listAllContacts();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ContactDto>> findContactById(@PathVariable Long id) {
        return contactService.findContactById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ContactDto>> updateContact(@PathVariable Long id, @RequestBody ContactDto contactDto) {
        return contactService.contactUpdate(id, contactDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteContact(@PathVariable Long id) {
        return contactService.contactDelete(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
