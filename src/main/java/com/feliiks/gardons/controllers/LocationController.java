package com.feliiks.gardons.controllers;

import com.feliiks.gardons.entities.Location;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.LocationService;
import com.feliiks.gardons.viewmodels.*;
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

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "List all locations.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<GetLocationsResponse> getAllLocations() {
        List<Location> locations = locationService.findAll();

        return ResponseEntity.status(200).body(new GetLocationsResponse(locations));
    }

    @Operation(summary = "Get a specific location.")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GetLocationResponse> getLocationById(@PathVariable("id") Long id) throws BusinessException {
        Optional<Location> location = locationService.findById(id);

        if (location.isEmpty()) {
            String errorMessage = String.format("La location '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetLocationResponse(location.get()));
    }

    @Operation(summary = "Create a new location.")
    @PostMapping(produces = "application/json")
    public ResponseEntity<PostLocationResponse> saveNewLocation(@RequestBody PostLocationRequest postLocationRequest) throws BusinessException {
        Location location = locationService.create(postLocationRequest);

        return ResponseEntity.status(201).body(new PostLocationResponse(location));
    }

    @Operation(summary = "Partial update a specific location.")
    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<PatchLocationResponse> editLocation(@PathVariable("id") Long id, @RequestBody PatchLocationRequest patchLocationRequest) throws BusinessException {
        Location location = locationService.editLocation(id, patchLocationRequest);

        return ResponseEntity.status(200).body(new PatchLocationResponse(location));
    }

    @Operation(summary = "Delete a specific location.")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<DeleteLocationResponse> deleteLocation(@PathVariable("id") Long id) throws BusinessException {
        Optional<Location> location = locationService.deleteById(id);

        if (location.isEmpty()) {
            String errorMessage = String.format("La location '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new DeleteLocationResponse(location.get()));
    }
}
