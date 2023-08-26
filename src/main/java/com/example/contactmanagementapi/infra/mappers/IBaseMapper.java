package com.example.contactmanagementapi.infra.mappers;

public interface IBaseMapper<T,D> {
    T toDto(D entity);
    D toEntity(T dto);
}
