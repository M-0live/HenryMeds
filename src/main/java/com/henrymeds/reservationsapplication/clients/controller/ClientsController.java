package com.henrymeds.reservationsapplication.clients.controller;

import com.henrymeds.reservationsapplication.clients.resources.ReservationResponse;
import com.henrymeds.reservationsapplication.clients.service.ClientsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clients")
public class ClientsController {

    private final ClientsService clientsService;

    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }


    @PostMapping("/reservation/{providerAvailabilityId}")
    public ReservationResponse reserveProviderAvailability(
            @PathVariable Long providerAvailabilityId) {
        return clientsService.reserveProviderAvailability(providerAvailabilityId);
    }

    @PostMapping("/confirmation/{providerAvailabilityId}")
    public ReservationResponse confirmProviderAvailability(
            @PathVariable Long providerAvailabilityId) {
        return clientsService.confirmProviderAvailability(providerAvailabilityId);
    }
}
