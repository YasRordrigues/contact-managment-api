package com.example.contactmanagementapi.core.mappers;

public interface IBaseMapper<T,D> {
    T toDto(D entity);
    D toEntity(T dto);
}
