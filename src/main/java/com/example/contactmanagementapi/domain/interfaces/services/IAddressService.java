package com.example.contactmanagementapi.domain.interfaces.services;

import com.example.contactmanagementapi.application.dto.AddressDto;
import com.example.contactmanagementapi.domain.valueobjects.AddressEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IAddressService {

    Flux<AddressDto> findAddressesByContactId(Long contactId);

    Mono<Void> deleteByContactId(Long contactId);

    Flux<AddressDto> saveAll(List<AddressDto> addresses, Long contactId);
}