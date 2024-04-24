package com.henrymeds.reservationsapplication.providers.repository;

import com.henrymeds.reservationsapplication.providers.entity.ProviderAvailabilityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderAvailabilityRepository extends CrudRepository<ProviderAvailabilityEntity, Long> {

    List<ProviderAvailabilityEntity> findByProviderIdAndConfirmed(Long providerId, boolean confirmed);


}
