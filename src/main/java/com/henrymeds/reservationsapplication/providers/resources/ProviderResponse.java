package com.henrymeds.reservationsapplication.providers.resources;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ProviderResponse {

    private Long providerId;
    private String providerName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
