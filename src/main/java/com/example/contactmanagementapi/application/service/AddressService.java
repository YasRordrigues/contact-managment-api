package com.example.contactmanagementapi.application.service;

import com.example.contactmanagementapi.application.dto.AddressDto;
import com.example.contactmanagementapi.domain.interfaces.services.IAddressService;
import com.example.contactmanagementapi.infra.mappers.AddressMapper;
import com.example.contactmanagementapi.domain.interfaces.repositories.IAddressRepository;
import com.example.contactmanagementapi.domain.valueobjects.AddressEntity;
import com.example.contactmanagementapi.infra.mappers.IBaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressService implements IAddressService {
    private final IAddressRepository iAddressRepository;
    private final IBaseMapper<AddressDto, AddressEntity> addressMapper;

    public Flux<AddressDto> findAddressesByContactId(Long contactId) {
        return iAddressRepository.findAddressesByContactId(contactId)
                .map(addressMapper::toDto);
    }

    public Mono<Void> deleteByContactId(Long contactId) {
        return iAddressRepository.deleteByContactId(contactId).then();
    }

    public Flux<AddressDto> saveAll(List<AddressDto> addresses, Long contactId) {
        return Flux.fromIterable(addresses)
                .map(addressDto -> {
                    AddressEntity addressEntity = addressMapper.toEntity(addressDto);
                    addressEntity.setContactId(contactId);
                    return addressEntity;
                })
                .flatMap(iAddressRepository::save)
                .map(addressMapper::toDto);
    }
}