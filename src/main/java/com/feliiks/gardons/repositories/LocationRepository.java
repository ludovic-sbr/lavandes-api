package com.feliiks.gardons.repositories;

import com.feliiks.gardons.converters.LocationConverter;
import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.models.LocationModel;
import com.feliiks.gardons.repositories.jpa.LocationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class LocationRepository {

    private final LocationJpaRepository locationJpaRepository;
    private final LocationConverter locationConverter;

    public LocationRepository(
            LocationJpaRepository locationJpaRepository,
            LocationConverter locationConverter) {
        this.locationJpaRepository = locationJpaRepository;
        this.locationConverter = locationConverter;
    }

    public List<LocationEntity> findAll() {
        List<LocationModel> allLocations = locationJpaRepository.findAll();

        return allLocations.stream().map(locationConverter::convertToEntity).toList();
    }

    public Optional<LocationEntity> findById(Long id) {
        Optional<LocationModel> location = locationJpaRepository.findById(id);

        if (location.isEmpty()) return Optional.empty();

        return Optional.of(locationConverter.convertToEntity(location.get()));
    }

    public LocationEntity save(LocationEntity location) {
        LocationModel currentLocation = locationConverter.convertToModel(location);

        locationJpaRepository.save(currentLocation);

        return locationConverter.convertToEntity(currentLocation);
    }

    public void deleteById(Long id) {
        locationJpaRepository.deleteById(id);
    }
}
