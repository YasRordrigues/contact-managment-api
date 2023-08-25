package com.example.contactmanagementapi.application.service;

import com.example.contactmanagementapi.application.dto.AddressDto;
import com.example.contactmanagementapi.application.dto.ContactDto;
import com.example.contactmanagementapi.core.mappers.AddressMapper;
import com.example.contactmanagementapi.core.mappers.ContactMapper;
import com.example.contactmanagementapi.domain.entities.ContactEntity;
import com.example.contactmanagementapi.domain.interfaces.repositories.IAddressRepository;
import com.example.contactmanagementapi.domain.interfaces.repositories.IContactRepository;
import com.example.contactmanagementapi.domain.valueobjects.AddressEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ContactService {
    private final IContactRepository iContactRepository;
    private final IAddressRepository iAddressRepository;
    private final ContactMapper contactMapper;
    private final AddressMapper addressMapper;

    public Mono<ContactDto> createContact(ContactDto contactDto) {
        ContactEntity contactEntity = contactMapper.toEntity(contactDto);

        return iContactRepository.save(contactEntity)
                .flatMap(savedContact -> {
                    List<AddressDto> addressesDto = contactDto.getAddresses();

                    List<AddressEntity> addressEntities = addressesDto.stream()
                            .map(dto -> {
                                AddressEntity addressEntity = addressMapper.toEntity(dto);
                                addressEntity.setContactId(savedContact.getId());
                                return addressEntity;
                            }).collect(Collectors.toList());

                    return iAddressRepository.saveAll(addressEntities)
                            .collectList()
                            .flatMap(savedAddresses -> {
                                ContactDto responseDto = contactMapper.toDto(savedContact);
                                responseDto.setAddresses(savedAddresses.stream()
                                        .map(addressMapper::toDto)
                                        .collect(Collectors.toList()));
                                return Mono.just(responseDto);
                            });
                });
    }

    public Flux<ContactDto> listAllContacts() {
        return iContactRepository.findAll()
                .flatMap(contactEntity -> {
                    return iAddressRepository.findAddressesByContactId(contactEntity.getId())
                            .collectList()
                            .map(addressEntities -> {
                                ContactDto contactDto = new ContactDto();
                                contactDto.setId(contactEntity.getId());
                                contactDto.setName(contactEntity.getName());
                                contactDto.setEmail(contactEntity.getEmail());
                                contactDto.setPhone(contactEntity.getPhone());
                                contactDto.setBirthDate(contactEntity.getBirthDate());

                                List<AddressDto> addressDtos = addressEntities.stream()
                                        .map(addressEntity -> {
                                            AddressDto addressDto = new AddressDto();
                                            addressDto.setId(addressEntity.getId());
                                            addressDto.setStreet(addressEntity.getStreet());
                                            addressDto.setNumber(addressEntity.getNumber());
                                            addressDto.setPostalCode(addressEntity.getPostalCode());

                                            return addressDto;
                                        })
                                        .collect(Collectors.toList());

                                contactDto.setAddresses(addressDtos);
                                return contactDto;
                            });
                });
    }



    public Mono<ContactDto> findContactById(Long id) {
        return iContactRepository.findById(id)
                .flatMap(contactEntity -> {
                    return iAddressRepository.findAddressesByContactId(contactEntity.getId())
                            .collectList()
                            .map(addressEntities -> {
                                ContactDto contactDto = new ContactDto();
                                contactDto.setId(contactEntity.getId());
                                contactDto.setName(contactEntity.getName());
                                // Adicione quaisquer outros campos que você tenha no seu DTO e entidade

                                List<AddressDto> addressDtos = addressEntities.stream()
                                        .map(addressEntity -> {
                                            AddressDto addressDto = new AddressDto();
                                            addressDto.setId(addressEntity.getId());
                                            addressDto.setStreet(addressEntity.getStreet());
                                            // E qualquer outro campo que você tenha

                                            return addressDto;
                                        })
                                        .collect(Collectors.toList());

                                contactDto.setAddresses(addressDtos);
                                return contactDto;
                            });
                });
    }


    public Mono<ContactDto> contactUpdate(Long id, ContactDto dto) {
        return iContactRepository.findById(id).flatMap(contactEntity -> {
            contactEntity.setName(dto.getName());
            contactEntity.setEmail(dto.getEmail());
            contactEntity.setPhone(dto.getPhone());
            contactEntity.setBirthDate(dto.getBirthDate());

            return iContactRepository.save(contactEntity)
                    .flatMap(savedContact -> {
                        return iAddressRepository.deleteByContactId(savedContact.getId())
                                .thenMany(
                                        Flux.fromIterable(dto.getAddresses())
                                                .map(addressDto -> {
                                                    AddressEntity addressEntity = addressMapper.toEntity(addressDto);
                                                    addressEntity.setContactId(savedContact.getId());
                                                    return addressEntity;
                                                })
                                                .flatMap(iAddressRepository::save)
                                )
                                .collectList()
                                .flatMap(savedAddresses -> {
                                    ContactDto responseDto = contactMapper.toDto(savedContact);
                                    responseDto.setAddresses(savedAddresses.stream()
                                            .map(addressMapper::toDto)
                                            .collect(Collectors.toList()));
                                    return Mono.just(responseDto);
                                });
                    });
        });
    }


    public Mono<Void> contactDelete(Long id) {
        return iContactRepository.deleteById(id);
    }

}
