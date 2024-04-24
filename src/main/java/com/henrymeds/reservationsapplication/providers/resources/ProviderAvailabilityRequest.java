package com.henrymeds.reservationsapplication.providers.resources;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ProviderAvailabilityRequest {

    // This class is used to represent the request of a provider availability

    // The ID of the provider
    private Long providerId;

    // The name of the provider
    private String providerName;

    // The date of the availability
    private LocalDate date;

    // The start time of the availability
    private LocalTime startTime;

    // The end time of the availability
    private LocalTime endTime;
}
