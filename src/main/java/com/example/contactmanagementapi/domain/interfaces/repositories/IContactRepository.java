package com.example.contactmanagementapi.domain.interfaces.repositories;

import com.example.contactmanagementapi.domain.entities.ContactEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactRepository extends ReactiveCrudRepository<ContactEntity, Long> {
    
}
