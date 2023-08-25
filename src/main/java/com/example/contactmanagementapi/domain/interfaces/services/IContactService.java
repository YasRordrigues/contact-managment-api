package com.example.contactmanagementapi.domain.interfaces.services;

import com.example.contactmanagementapi.application.dto.ContactDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface IContactService {
    Flux<ContactDto> getAllContacts(Pageable pageable, Map<String, String> queryParams);
    Mono<ContactDto> getContactById(Long id);
    Mono<ContactDto> createContact(ContactDto contactDto);
    Mono<ContactDto> updateContact(Long id, ContactDto contactDto);
    Mono<Void> deleteContact(Long id);
}
