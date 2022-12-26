package com.feliiks.gardons.controllers;

import com.feliiks.gardons.converters.LocationConverter;
import com.feliiks.gardons.dtos.*;
import com.feliiks.gardons.entities.FileEntity;
import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.FileService;
import com.feliiks.gardons.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Tag(name = "Location")
@RestController
@RequestMapping("/location")
public class LocationController {
    public final LocationService locationService;
    public final FileService fileService;
    private final LocationConverter locationConverter;

    public LocationController(
            LocationService locationService,
            FileService fileService,
            LocationConverter locationConverter) {
        this.locationService = locationService;
        this.fileService = fileService;
        this.locationConverter = locationConverter;
    }

    @Operation(summary = "List all locations.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<GetLocationsResponse> getAllLocations() {
        List<LocationEntity> locations = locationService.findAll();

        return ResponseEntity.status(200).body(new GetLocationsResponse(locations));
    }

    @Operation(summary = "List all locations by period.")
    @GetMapping(path = "/period")
    public ResponseEntity<GetLocationsResponse> getLocationsByPeriod(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to) {
        List<LocationEntity> locations = locationService.findAllByPeriod(from, to);

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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationResponse> saveNewLocation(LocationRequest locationRequest, @RequestPart("image") final MultipartFile image) throws BusinessException {
        LocationEntity location = locationConverter.convertToEntity(locationRequest);

        FileEntity file = fileService.saveFile(image);

        location.setImage(file);

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
