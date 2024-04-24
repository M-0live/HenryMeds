package com.henrymeds.reservationsapplication.providers.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ProviderAvailabilityDTO {

    private Long providerAvailabilityId;
    private Long providerId;
    private String providerName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean confirmed;
    private boolean reserved;
}
