package com.feliiks.gardons.controllers;

import com.feliiks.gardons.entities.Location;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.LocationService;
import com.feliiks.gardons.viewmodels.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {
    public final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<GetLocationsResponse> getAllLocations() {
        List<Location> locations = locationService.findAll();

        return ResponseEntity.status(200).body(new GetLocationsResponse(locations));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GetLocationResponse> getLocationById(@PathVariable("id") Long id) throws BusinessException {
        Optional<Location> location = locationService.findById(id);

        if (location.isEmpty()) {
            String errorMessage = String.format("La location '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetLocationResponse(location.get()));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<PostLocationResponse> saveNewUser(@RequestBody PostLocationRequest postLocationRequest) throws BusinessException {
        Location location = locationService.create(postLocationRequest);

        return ResponseEntity.status(201).body(new PostLocationResponse(location));
    }

    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<PatchLocationResponse> editLocation(@RequestBody PatchLocationRequest patchLocationRequest) throws BusinessException {
        Location location = locationService.editLocation(patchLocationRequest);

        return ResponseEntity.status(200).body(new PatchLocationResponse(location));
    }

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
