package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.LocationRepository;
import com.feliiks.gardons.services.LocationService;
import com.feliiks.gardons.services.StripeService;
import com.stripe.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LocationImpl implements LocationService {
    public final LocationRepository locationRepository;
    public final StripeService stripeService;

    public LocationImpl(
            LocationRepository locationRepository,
            StripeService stripeService) {
        this.locationRepository = locationRepository;
        this.stripeService = stripeService;
    }

    @Override
    public List<LocationEntity> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public List<LocationEntity> findAllByPeriod(Date from, Date to) {
        List<LocationEntity> locations = locationRepository.findAll();

        System.out.println(from);
        System.out.println(to);

        // filtre sur les locations et renvoie des locations disponibles

         return locations;
    }

    @Override
    public Optional<LocationEntity> findById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Optional<LocationEntity> findBySlotNumber(int slotNumber) {
        return locationRepository.findBySlotNumber(slotNumber);
    }

    @Override
    public LocationEntity create(LocationEntity location) throws BusinessException {
        Optional<LocationEntity> existingLocation = this.findBySlotNumber(location.getSlot_number());

        if (existingLocation.isPresent()) {
            String errorMessage = String.format("Une location existe déjà pour le numéro d'emplacement '%s'.", location.getSlot_number());

            throw new BusinessException(errorMessage);
        }

        LocationEntity newLocation = new LocationEntity();

        Optional<Product> product = stripeService.findProductById(location.getStripeProductId());

        if (product.isEmpty()) {
            String errorMessage = String.format("Le produit stripe '%s' n'existe pas.", location.getStripeProductId());

            throw new BusinessException(errorMessage);
        }
        newLocation.setStripeProductId(location.getStripeProductId());

        newLocation.setName(location.getName());
        newLocation.setDescription(location.getDescription());
        newLocation.setParking(location.getParking());
        newLocation.setKitchen(location.getKitchen());
        newLocation.setWifi(location.getWifi());
        newLocation.setSanitary(location.getSanitary());
        newLocation.setHeater(location.getHeater());
        newLocation.setAir_conditioner(location.getAir_conditioner());
        newLocation.setTerrace(location.getTerrace());
        newLocation.setBarbecue(location.getBarbecue());
        newLocation.setSurface(location.getSurface());
        newLocation.setMax_persons(location.getMax_persons());
        newLocation.setPrice_per_night(location.getPrice_per_night());
        newLocation.setBedrooms(location.getBedrooms());
        newLocation.setSlot_number(location.getSlot_number());
        newLocation.setAvailable(true);

        return locationRepository.save(newLocation);
    }

    @Override
    public LocationEntity editLocation(Long id, LocationEntity location) throws BusinessException {
        Optional<LocationEntity> existingLocation = this.findById(id);

        if (existingLocation.isEmpty()) {
            String errorMessage = String.format("La location '%s' n'existe pas.", id);

            throw new BusinessException(errorMessage);
        }

        if (location.getStripeProductId() != null) {
            Optional<Product> product = stripeService.findProductById(location.getStripeProductId());

            if (product.isEmpty()) {
                String errorMessage = String.format("Le produit stripe '%s' n'existe pas.", location.getStripeProductId());

                throw new BusinessException(errorMessage);
            }

            existingLocation.get().setStripeProductId(location.getStripeProductId());
        }

        if (location.getName() != null) {
            existingLocation.get().setName(location.getName());
        }

        if (location.getDescription() != null) {
            existingLocation.get().setDescription(location.getDescription());
        }

        if (location.getParking() != null) {
            existingLocation.get().setParking(location.getParking());
        }

        if (location.getKitchen() != null) {
            existingLocation.get().setKitchen(location.getKitchen());
        }

        if (location.getWifi() != null) {
            existingLocation.get().setWifi(location.getWifi());
        }

        if (location.getSanitary() != null) {
            existingLocation.get().setSanitary(location.getSanitary());
        }

        if (location.getHeater() != null) {
            existingLocation.get().setHeater(location.getHeater());
        }

        if (location.getAir_conditioner() != null) {
            existingLocation.get().setAir_conditioner(location.getAir_conditioner());
        }

        if (location.getTerrace() != null) {
            existingLocation.get().setTerrace(location.getTerrace());
        }

        if (location.getBarbecue() != null) {
            existingLocation.get().setBarbecue(location.getBarbecue());
        }

        if (location.getSurface() != 0) {
            existingLocation.get().setSurface(location.getSurface());
        }

        if (location.getMax_persons() != 0) {
            existingLocation.get().setMax_persons(location.getMax_persons());
        }

        if (location.getPrice_per_night() != 0) {
            existingLocation.get().setPrice_per_night(location.getPrice_per_night());
        }

        if (location.getBedrooms() != 0) {
            existingLocation.get().setBedrooms(location.getBedrooms());
        }

        if (location.getAvailable() != null) {
            existingLocation.get().setAvailable(location.getAvailable());
        }

        return locationRepository.save(existingLocation.get());
    }

    @Override
    public Optional<LocationEntity> deleteById(Long id) {
        try {
            Optional<LocationEntity> location = locationRepository.findById(id);

            locationRepository.deleteById(id);

            return location;
        } catch (EmptyResultDataAccessException err) {
            return Optional.empty();
        }
    }
}