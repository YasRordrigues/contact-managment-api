package com.example.contactmanagementapi.application.service;

import com.example.contactmanagementapi.application.dto.ContactDto;
import com.example.contactmanagementapi.domain.interfaces.services.IAddressService;
import com.example.contactmanagementapi.domain.entities.ContactEntity;
import com.example.contactmanagementapi.domain.interfaces.repositories.IContactRepository;
import com.example.contactmanagementapi.domain.interfaces.services.IContactService;
import com.example.contactmanagementapi.infra.mappers.IBaseMapper;
import com.example.contactmanagementapi.domain.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ContactService implements IContactService {
    private final IContactRepository iContactRepository;
    private final IAddressService iAddressService;
    private final IBaseMapper<ContactDto, ContactEntity> contactMapper;

    @Override
    public Mono<Page<ContactDto>> listAllContacts(Pageable pageable) {
        return this.iContactRepository.count()
                .flatMap(totalCount -> {
                    if (totalCount == 0) {
                        return Mono.error(new ResourceNotFoundException());
                    }
                    return this.iContactRepository.findAll()
                            .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                            .take(pageable.getPageSize())
                            .flatMap(contactEntity -> iAddressService.findAddressesByContactId(contactEntity.getId())
                                    .collectList()
                                    .map(addressDtos -> ContactDto.builder()
                                            .id(contactEntity.getId())
                                            .name(contactEntity.getName())
                                            .email(contactEntity.getEmail())
                                            .phone(contactEntity.getPhone())
                                            .birthDate(contactEntity.getBirthDate())
                                            .addresses(addressDtos)
                                            .build()))
                            .collectList()
                            .map(contactDtos -> new PageImpl<>(contactDtos, pageable, totalCount));
                });
    }

    @Override
    public Mono<ContactDto> findContactById(Long id) {
        return iContactRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()))
                .flatMap(contactEntity -> iAddressService.findAddressesByContactId(contactEntity.getId())
                        .collectList()
                        .map(addressDtos -> ContactDto.builder()
                                .id(contactEntity.getId())
                                .name(contactEntity.getName())
                                .email(contactEntity.getEmail())
                                .phone(contactEntity.getPhone())
                                .birthDate(contactEntity.getBirthDate())
                                .addresses(addressDtos)
                                .build()));
    }


    @Override
    public Mono<ContactDto> createContact(ContactDto contactDto) {
        ContactEntity contactEntity = contactMapper.toEntity(contactDto);

        return iContactRepository.save(contactEntity)
                .flatMap(savedContact -> iAddressService.saveAll(contactDto.getAddresses(), savedContact.getId())
                        .collectList()
                        .map(savedAddresses -> ContactDto.builder()
                                .id(savedContact.getId())
                                .name(savedContact.getName())
                                .email(savedContact.getEmail())
                                .phone(savedContact.getPhone())
                                .birthDate(savedContact.getBirthDate())
                                .addresses(savedAddresses)
                                .build()));
    }

    @Override
    public Mono<ContactDto> updateContact(Long id, ContactDto dto) {
        return iContactRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()))
                .flatMap(contactEntity -> {
                    contactEntity.setName(dto.getName());
                    contactEntity.setEmail(dto.getEmail());
                    contactEntity.setPhone(dto.getPhone());
                    contactEntity.setBirthDate(dto.getBirthDate());

                    return iContactRepository.save(contactEntity)
                            .flatMap(savedContact -> iAddressService.deleteByContactId(savedContact.getId())
                                    .thenMany(iAddressService.saveAll(dto.getAddresses(), savedContact.getId()))
                                    .collectList()
                                    .map(savedAddresses -> ContactDto.builder()
                                            .id(savedContact.getId())
                                            .name(savedContact.getName())
                                            .email(savedContact.getEmail())
                                            .phone(savedContact.getPhone())
                                            .birthDate(savedContact.getBirthDate())
                                            .addresses(savedAddresses)
                                            .build()));
                });
    }

    @Override
    public Mono<ContactDto> patchContact(Long id, ContactDto dto) {
        return iContactRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()))
                .map(existingContact -> {
                    Optional.ofNullable(dto.getName()).ifPresent(existingContact::setName);
                    Optional.ofNullable(dto.getEmail()).ifPresent(existingContact::setEmail);
                    Optional.ofNullable(dto.getPhone()).ifPresent(existingContact::setPhone);
                    Optional.of(dto.getBirthDate()).ifPresent(existingContact::setBirthDate);
                    return existingContact;
                })
                .flatMap(iContactRepository::save)
                .flatMap(savedContact ->
                        Optional.of(dto.getAddresses())
                                .map(addresses -> iAddressService.deleteByContactId(savedContact.getId())
                                        .thenMany(iAddressService.saveAll(addresses, savedContact.getId()))
                                        .collectList()
                                        .map(savedAddresses -> ContactDto.builder()
                                                .id(savedContact.getId())
                                                .name(savedContact.getName())
                                                .email(savedContact.getEmail())
                                                .phone(savedContact.getPhone())
                                                .birthDate(savedContact.getBirthDate())
                                                .addresses(savedAddresses)
                                                .build()))
                                .orElseGet(() -> Mono.just(contactMapper.toDto(savedContact)))
                );
    }

    @Override
    public Mono<Void> deleteContact(Long id) {
        return iAddressService.deleteByContactId(id)
                 .then(iContactRepository.deleteById(id));
    }
}
