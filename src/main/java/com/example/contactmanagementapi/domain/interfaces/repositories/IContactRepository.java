package com.example.contactmanagementapi.domain.interfaces.repositories;

import com.example.contactmanagementapi.domain.entities.ContactEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IContactRepository extends ReactiveCrudRepository<ContactEntity, Long> {
    
}
