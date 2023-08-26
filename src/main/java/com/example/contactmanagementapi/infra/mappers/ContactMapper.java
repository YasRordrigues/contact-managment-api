package com.example.contactmanagementapi.infra.mappers;

import com.example.contactmanagementapi.application.dto.ContactDto;
import com.example.contactmanagementapi.domain.entities.ContactEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper implements IBaseMapper<ContactDto, ContactEntity> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ContactDto toDto(ContactEntity contact) {
        return modelMapper.map(contact, ContactDto.class);
    }

    @Override
    public ContactEntity toEntity(ContactDto contactDto) {
        return modelMapper.map(contactDto, ContactEntity.class);
    }
}
