package com.henrymeds.reservationsapplication.clients.resources;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class ReservationResponse {

    // This class is used to represent the response of a reservation request

    // The ID of the provider availability
    private Long providerAvailabilityId;

    // The ID of the provider
    private Long providerId;

    // The name of the provider
    private String providerName;

    // The date of the reservation
    private LocalDate date;

    // The start time of the reservation
    private LocalTime startTime;

    // The end time of the reservation
    private LocalTime endTime;

    // Whether the reservation is reserved
    private boolean reserved;

    // Whether the reservation is confirmed
    private boolean confirmed;

    // The time the reservation was made
    private LocalDateTime reserveTime;
}
