package com.feliiks.gardons.controllers;

import com.feliiks.gardons.converters.LocationConverter;
import com.feliiks.gardons.dtos.*;
import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Location")
@RestController
@RequestMapping("/location")
public class LocationController {
    public final LocationService locationService;
    private final LocationConverter locationConverter;

    public LocationController(
            LocationService locationService,
            LocationConverter locationConverter) {
        this.locationService = locationService;
        this.locationConverter = locationConverter;
    }

    @Operation(summary = "List all locations.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<GetLocationsResponse> getAllLocations() {
        List<LocationEntity> locations = locationService.findAll();

        return ResponseEntity.status(200).body(new GetLocationsResponse(locations));
    }

    @Operation(summary = "Get a specific location.")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable("id") Long id) throws BusinessException {
        Optional<LocationEntity> location = locationService.findById(id);

        if (location.isEmpty()) {
            String errorMessage = String.format("La location '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new LocationResponse(location.get()));
    }

    @Operation(summary = "Create a new location.")
    @PostMapping(produces = "application/json")
    public ResponseEntity<LocationResponse> saveNewLocation(@RequestBody LocationRequest locationRequest) throws BusinessException {
        LocationEntity location = locationConverter.convertToEntity(locationRequest);
        LocationEntity savedLocation = locationService.create(location);

        return ResponseEntity.status(201).body(new LocationResponse(savedLocation));
    }

    @Operation(summary = "Partial update a specific location.")
    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<LocationResponse> editLocation(@PathVariable("id") Long id, @RequestBody LocationRequest locationRequest) throws BusinessException {
        LocationEntity location = locationConverter.convertToEntity(locationRequest);
        LocationEntity patchedLocation = locationService.editLocation(id, location);

        return ResponseEntity.status(200).body(new LocationResponse(patchedLocation));
    }

    @Operation(summary = "Delete a specific location.")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<LocationResponse> deleteLocation(@PathVariable("id") Long id) throws BusinessException {
        Optional<LocationEntity> location = locationService.deleteById(id);

        if (location.isEmpty()) {
            String errorMessage = String.format("La location '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new LocationResponse(location.get()));
    }
}
