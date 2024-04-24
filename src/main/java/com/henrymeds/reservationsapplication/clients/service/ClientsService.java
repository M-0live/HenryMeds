package com.henrymeds.reservationsapplication.clients.service;

import com.henrymeds.reservationsapplication.clients.resources.ReservationResponse;
import com.henrymeds.reservationsapplication.providers.entity.ProviderAvailabilityEntity;
import com.henrymeds.reservationsapplication.providers.repository.ProviderAvailabilityRepository;
import com.henrymeds.reservationsapplication.providers.service.ProvidersService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Service
public class ClientsService {

    private final ProviderAvailabilityRepository providerAvailabilityRepository;
    private final ProvidersService providersService;

    private final ZoneId estZone = ZoneId.of("America/New_York");

    @Autowired
    public ClientsService(ProviderAvailabilityRepository providerAvailabilityRepository, ProvidersService providersService) {
        this.providerAvailabilityRepository = providerAvailabilityRepository;
        this.providersService = providersService;
    }

    public ReservationResponse reserveProviderAvailability(Long providerAvailabilityId) {

        //Null checks for providerAvailabilityId
        if(providerAvailabilityId == null) {
            throw new IllegalArgumentException("Provider availability ID cannot be null");
        }

        val providerAvailabilityEntity = providerAvailabilityRepository.findById(providerAvailabilityId)
                .orElseThrow(() -> new IllegalArgumentException("Provider availability not found"));


        //This uses system time ideally we would want to account for time zones passed in from the user
        val reserveTime = LocalDateTime.now(estZone);

        LocalDateTime availabilityTime = providerAvailabilityEntity.getDate().atTime(providerAvailabilityEntity.getStartTime());

        checkDuration(reserveTime, availabilityTime);

        //Check if provider availability is already reserved and within 30 minutes of current time
        if(providerAvailabilityEntity.isReserved() && availabilityTime.plusMinutes(30).isAfter(reserveTime)) {
            throw new IllegalArgumentException("Provider availability is already reserved");
        }

        if(providerAvailabilityEntity.isConfirmed()) {
            throw new IllegalArgumentException("Provider availability is already confirmed");
        }

        providerAvailabilityEntity.setReserved(true);
        providerAvailabilityEntity.setReserveTime(reserveTime);
        providerAvailabilityRepository.save(providerAvailabilityEntity);

        return ReservationResponse.builder()
                .providerAvailabilityId(providerAvailabilityEntity.getProviderAvailabilityId())
                .providerId(providerAvailabilityEntity.getProviderId())
                .providerName(providerAvailabilityEntity.getProviderName())
                .date(providerAvailabilityEntity.getDate())
                .startTime(providerAvailabilityEntity.getStartTime())
                .endTime(providerAvailabilityEntity.getEndTime())
                .reserved(providerAvailabilityEntity.isReserved())
                .reserveTime(providerAvailabilityEntity.getReserveTime())
                .build();
    }

    private void checkDuration(LocalDateTime reserveTime, LocalDateTime currentTime) {

        //Check if reservation time is within 24 hours from start time
        if(!reserveTime.isBefore(currentTime.minusHours(24))) {
            throw new IllegalArgumentException("Reservation time must be within 24 hours from start Time");
        }

    }

    public ReservationResponse confirmProviderAvailability(Long providerAvailabilityId) {

        //Null checks for providerAvailabilityId
        if(providerAvailabilityId == null) {
            throw new IllegalArgumentException("Provider availability ID cannot be null");
        }

        val providerAvailabilityEntity = providerAvailabilityRepository.findById(providerAvailabilityId)
                .orElseThrow(() -> new IllegalArgumentException("Provider availability not found"));

        checkWithin30Minutes(providerAvailabilityEntity);

        providerAvailabilityEntity.setConfirmed(true);
        providerAvailabilityRepository.save(providerAvailabilityEntity);

        return ReservationResponse.builder()
                .providerAvailabilityId(providerAvailabilityEntity.getProviderAvailabilityId())
                .providerId(providerAvailabilityEntity.getProviderId())
                .providerName(providerAvailabilityEntity.getProviderName())
                .date(providerAvailabilityEntity.getDate())
                .startTime(providerAvailabilityEntity.getStartTime())
                .endTime(providerAvailabilityEntity.getEndTime())
                .reserved(providerAvailabilityEntity.isReserved())
                .reserveTime(providerAvailabilityEntity.getReserveTime())
                .confirmed(providerAvailabilityEntity.isConfirmed())
                .build();
    }

    private void checkWithin30Minutes(ProviderAvailabilityEntity providerAvailabilityEntity) {

        //Check if reservation time is within 30 minutes from now
        if(providerAvailabilityEntity.getReserveTime().plusMinutes(30).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation time must be within 30 minutes from reservation time");
        }
    }
}
