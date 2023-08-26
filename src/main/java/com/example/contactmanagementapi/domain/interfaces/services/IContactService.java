package com.example.contactmanagementapi.domain.interfaces.services;

import com.example.contactmanagementapi.application.dto.ContactDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface IContactService {

    Mono<Page<ContactDto>> listAllContacts(Pageable pageable);

    Mono<ContactDto> findContactById(Long id);

    Mono<ContactDto> createContact(ContactDto contactDto);

    Mono<ContactDto> updateContact(Long id, ContactDto dto);

    Mono<ContactDto> patchContact(Long id, ContactDto dto);

    Mono<Void> deleteContact(Long id);
}
