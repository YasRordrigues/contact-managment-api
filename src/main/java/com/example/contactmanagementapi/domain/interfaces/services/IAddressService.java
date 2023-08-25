package com.example.contactmanagementapi.domain.interfaces.services;

import com.example.contactmanagementapi.application.dto.AddressDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IAddressService {
    Flux<AddressDto> getAddressesForContact(Long contactId);
    Mono<Void> saveAddressesForContact(List<AddressDto> addressDtos, Long contactId);
}
