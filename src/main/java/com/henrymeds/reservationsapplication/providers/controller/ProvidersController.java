package com.henrymeds.reservationsapplication.providers.controller;

import com.henrymeds.reservationsapplication.providers.dto.ProviderAvailabilityDTO;
import com.henrymeds.reservationsapplication.providers.resources.ProviderAvailabilityRequest;
import com.henrymeds.reservationsapplication.providers.resources.ProviderResponse;
import com.henrymeds.reservationsapplication.providers.service.ProvidersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProvidersController {


    private final ProvidersService providersService;

    public ProvidersController(ProvidersService providersService) {
        this.providersService = providersService;
    }

    @PostMapping
    public ProviderResponse submitProviderAvailability(
            @RequestBody ProviderAvailabilityRequest providerAvailabilityRequest) {


            return providersService.submitProviderAvailability(providerAvailabilityRequest);
    }

    @GetMapping("/availability/{providerId}")
    public List<ProviderAvailabilityDTO> getProvidersAvailability(@PathVariable Long providerId) {

        // Validate provider ID
        if (providerId == null) {
            throw new IllegalArgumentException("Provider ID cannot be null");
        }

        return providersService.getProvidersAvailability(providerId);
    }
}
