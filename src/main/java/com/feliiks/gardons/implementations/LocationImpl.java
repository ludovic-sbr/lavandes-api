package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Location;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.LocationRepository;
import com.feliiks.gardons.services.LocationService;
import com.feliiks.gardons.viewmodels.PatchLocationRequest;
import com.feliiks.gardons.viewmodels.PostLocationRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return locationRepository.findById(id);
    }

    @Override
    public Location create(PostLocationRequest postLocationRequest) throws BusinessException {
        Optional<Location> existingLocation = this.findById(2L);

        if (existingLocation.isPresent()) {
            String errorMessage = String.format("La location '%s' existe déjà.", postLocationRequest.getDescription());

            throw new BusinessException(errorMessage);
        }

        Location newLocation = new Location();
        newLocation.setDescription(postLocationRequest.getDescription());
        newLocation.setParking(postLocationRequest.getParking());
        newLocation.setKitchen(postLocationRequest.getKitchen());
        newLocation.setWifi(postLocationRequest.getWifi());
        newLocation.setSanitary(postLocationRequest.getSanitary());
        newLocation.setHeater(postLocationRequest.getHeater());
        newLocation.setAir_conditioner(postLocationRequest.getAir_conditioner());
        newLocation.setTerrace(postLocationRequest.getTerrace());
        newLocation.setBarbecue(postLocationRequest.getBarbecue());
        newLocation.setSurface(postLocationRequest.getSurface());
        newLocation.setMax_persons(postLocationRequest.getMax_persons());
        newLocation.setPrice_per_night(postLocationRequest.getPrice_per_night());
        newLocation.setBedrooms(postLocationRequest.getBedrooms());
        newLocation.setAvailable(true);

        return locationRepository.save(newLocation);
    }

    @Override
    public Location editLocation(PatchLocationRequest patchLocationRequest) throws BusinessException {
        Optional<Location> location = this.findById(patchLocationRequest.getId());

        if (location.isEmpty()) {
            String errorMessage = String.format("La location '%s' n'existe pas.", patchLocationRequest.getId());

            throw new BusinessException(errorMessage);
        }

        if (patchLocationRequest.getDescription() != null) {
            location.get().setDescription(patchLocationRequest.getDescription());
        }

        if (patchLocationRequest.getParking() != null) {
            location.get().setParking(patchLocationRequest.getParking());
        }

        if (patchLocationRequest.getKitchen() != null) {
            location.get().setKitchen(patchLocationRequest.getKitchen());
        }

        if (patchLocationRequest.getWifi() != null) {
            location.get().setWifi(patchLocationRequest.getWifi());
        }

        if (patchLocationRequest.getSanitary() != null) {
            location.get().setSanitary(patchLocationRequest.getSanitary());
        }

        if (patchLocationRequest.getHeater() != null) {
            location.get().setHeater(patchLocationRequest.getHeater());
        }

        if (patchLocationRequest.getAir_conditioner() != null) {
            location.get().setAir_conditioner(patchLocationRequest.getAir_conditioner());
        }

        if (patchLocationRequest.getTerrace() != null) {
            location.get().setTerrace(patchLocationRequest.getTerrace());
        }

        if (patchLocationRequest.getBarbecue() != null) {
            location.get().setBarbecue(patchLocationRequest.getBarbecue());
        }

        if (patchLocationRequest.getSurface() != 0) {
            location.get().setSurface(patchLocationRequest.getSurface());
        }

        if (patchLocationRequest.getMax_persons() != 0) {
            location.get().setMax_persons(patchLocationRequest.getMax_persons());
        }

        if (patchLocationRequest.getPrice_per_night() != 0) {
            location.get().setPrice_per_night(patchLocationRequest.getPrice_per_night());
        }

        if (patchLocationRequest.getBedrooms() != 0) {
            location.get().setBedrooms(patchLocationRequest.getBedrooms());
        }

        if (patchLocationRequest.getAvailable() != null) {
            location.get().setAvailable(patchLocationRequest.getAvailable());
        }

        return locationRepository.save(location.get());
    }

    @Override
    public Optional<Location> deleteById(Long id) {
        try {
            Optional<Location> location = locationRepository.findById(id);

            locationRepository.deleteById(id);

            return location;
        } catch (EmptyResultDataAccessException err) {
            return Optional.empty();
        }
    }
}