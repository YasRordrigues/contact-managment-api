package com.example.contactmanagementapi.domain.interfaces.repositories;

import com.example.contactmanagementapi.domain.valueobjects.AddressEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface IAddressRepository extends ReactiveCrudRepository<AddressEntity, Long> {
    Flux<AddressEntity> findAddressesByContactId(Long contactId);

    Flux<AddressEntity> deleteByContactId(Long id);
}
