package com.example.contactmanagementapi.core.mappers;

import com.example.contactmanagementapi.application.dto.AddressDto;
import com.example.contactmanagementapi.application.dto.ContactDto;
import com.example.contactmanagementapi.domain.entities.ContactEntity;
import com.example.contactmanagementapi.domain.valueobjects.AddressEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements IBaseMapper<AddressDto, AddressEntity> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public AddressDto toDto(AddressEntity entity) {
        return modelMapper.map(entity, AddressDto.class);
    }

    @Override
    public AddressEntity toEntity(AddressDto dto) {
        return modelMapper.map(dto, AddressEntity.class);
    }
}
