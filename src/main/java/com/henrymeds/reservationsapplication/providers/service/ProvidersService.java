package com.henrymeds.reservationsapplication.providers.service;

import com.henrymeds.reservationsapplication.providers.dto.ProviderAvailabilityDTO;
import com.henrymeds.reservationsapplication.providers.entity.ProviderAvailabilityEntity;
import com.henrymeds.reservationsapplication.providers.repository.ProviderAvailabilityRepository;
import com.henrymeds.reservationsapplication.providers.resources.ProviderAvailabilityRequest;
import com.henrymeds.reservationsapplication.providers.resources.ProviderResponse;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvidersService {

    private final ProviderAvailabilityRepository providerAvailabilityRepository;

    public ProvidersService(ProviderAvailabilityRepository providerAvailabilityRepository) {
        this.providerAvailabilityRepository = providerAvailabilityRepository;
    }

    public ProviderResponse submitProviderAvailability(ProviderAvailabilityRequest providerAvailabilityRequest) {

        //Null checks for providerAvailabilityRequest
        if(providerAvailabilityRequest == null) {
            throw new IllegalArgumentException("Provider availability request cannot be null");
        }

        val providerId = providerAvailabilityRequest.getProviderId();
        val date = providerAvailabilityRequest.getDate();
        var startTime = providerAvailabilityRequest.getStartTime();
        val endTime = providerAvailabilityRequest.getEndTime();


        //Null checks for providerId, date, startTime, and endTime and validation for startTime and endTime
        if(providerId == null) {
            throw new IllegalArgumentException("Provider ID cannot be null or empty");
        }

        if(date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        if(startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and End Time cannot be null");
        }

        if(startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }

        //start at the start time and increment by 15 minutes until the end time
        while(startTime.isBefore(endTime)) {
            val providerAvailabilityDTO = ProviderAvailabilityDTO.builder()
                    .providerId(providerId)
                    .providerName(providerAvailabilityRequest.getProviderName())
                    .date(date)
                    .startTime(startTime)
                    .endTime(startTime.plusMinutes(15))
                    .build();

            //save the providerAvailabilityDTO
            val providerAvailabilityEntity = toEntity(providerAvailabilityDTO);
            providerAvailabilityRepository.save(providerAvailabilityEntity);
            startTime = startTime.plusMinutes(15);
        }

        return ProviderResponse.builder()
                .providerId(providerId)
                .date(date)
                .startTime(providerAvailabilityRequest.getStartTime())
                .endTime(providerAvailabilityRequest.getEndTime())
                .build();
    }

    private ProviderAvailabilityEntity toEntity(ProviderAvailabilityDTO providerAvailabilityDTO) {
        return ProviderAvailabilityEntity.builder()
                .providerId(providerAvailabilityDTO.getProviderId())
                .providerName(providerAvailabilityDTO.getProviderName())
                .date(providerAvailabilityDTO.getDate())
                .startTime(providerAvailabilityDTO.getStartTime())
                .endTime(providerAvailabilityDTO.getEndTime())
                .confirmed(providerAvailabilityDTO.isConfirmed())
                .reserved(providerAvailabilityDTO.isReserved())
                .build();
    }

    public List<ProviderAvailabilityDTO> getProvidersAvailability(Long providerId) {

        val providerAvailabilities = providerAvailabilityRepository.findByProviderIdAndConfirmed(providerId, false);

        // Transform entities into DTO objects using Java streams and map operation
        List<ProviderAvailabilityDTO> providerAvailabilityDTOS = providerAvailabilities.stream()
                .map(this::mapToDto) // map each entity to DTO
                .collect(Collectors.toList()); // collect DTOs into a list

        return providerAvailabilityDTOS;

    }

    public ProviderAvailabilityDTO mapToDto(ProviderAvailabilityEntity providerAvailabilityEntity) {
        return ProviderAvailabilityDTO.builder()
                .providerAvailabilityId(providerAvailabilityEntity.getProviderAvailabilityId())
                .providerId(providerAvailabilityEntity.getProviderId())
                .providerName(providerAvailabilityEntity.getProviderName())
                .date(providerAvailabilityEntity.getDate())
                .startTime(providerAvailabilityEntity.getStartTime())
                .endTime(providerAvailabilityEntity.getEndTime())
                .confirmed(providerAvailabilityEntity.isConfirmed())
                .reserved(providerAvailabilityEntity.isReserved())
                .build();
    }
}
