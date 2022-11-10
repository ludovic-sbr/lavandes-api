package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Location;
import com.feliiks.gardons.repositories.LocationRepository;
import com.feliiks.gardons.services.LocationService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class LocationImpl implements LocationService {
    public final LocationRepository locationRepository;

    public LocationImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Long id) {
        Optional<Location> location;

        try {
            location = locationRepository.findById(id);
            if (location.isEmpty()) throw new ResponseStatusException(NOT_FOUND);
        } catch (ResponseStatusException err) {
            String errorMessage = String.format("Une erreur est survenue lors de la récupération de la location '%s'.", id);
            throw new ResponseStatusException(NOT_FOUND, errorMessage);
        }

        return location;
    }
}