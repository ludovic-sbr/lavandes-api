package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.FileEntity;
import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.entities.ReservationEntity;
import com.feliiks.gardons.entities.ReservationStatusEnum;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.LocationRepository;
import com.feliiks.gardons.repositories.ReservationRepository;
import com.feliiks.gardons.services.FileService;
import com.feliiks.gardons.services.LocationService;
import com.feliiks.gardons.services.StripeService;
import com.stripe.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LocationImpl implements LocationService {
    public final LocationRepository locationRepository;
    public final StripeService stripeService;
    public final ReservationRepository reservationRepository;
    public final FileService fileService;

    public LocationImpl(
            LocationRepository locationRepository,
            StripeService stripeService,
            FileService fileService,
            ReservationRepository reservationRepository) {
        this.locationRepository = locationRepository;
        this.stripeService = stripeService;
        this.fileService = fileService;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<LocationEntity> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public List<LocationEntity> findAllByPeriod(Date from, Date to) {
        List<LocationEntity> locations = locationRepository.findAll();

        List<ReservationEntity> reservations = reservationRepository.findAll();

        List<ReservationEntity> completeReservations =
                reservations
                        .stream()
                        .filter(elt -> Objects.equals(elt.getStatus(), ReservationStatusEnum.COMPLETE))
                        .toList();

        List<LocationEntity> locationsInThisPeriod = completeReservations
                .stream()
                .filter(elt -> elt.getFrom().compareTo(to) < 0 && elt.getTo().compareTo(from) > 0).map(ReservationEntity::getLocation).toList();

        return locations
                .stream()
                .filter(loc -> locationsInThisPeriod
                        .stream()
                        .filter(elt -> Objects.equals(elt.getId(), loc.getId())).count() < loc.getSlot_remaining())
                .toList();
    }

    @Override
    public Optional<LocationEntity> findById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public LocationEntity create(LocationEntity location, MultipartFile image) throws BusinessException {
        if (image == null) {
            throw new BusinessException("Vous devez fournir une image pour la location.");
        }

        LocationEntity newLocation = new LocationEntity();

        Optional<Product> product = stripeService.findProductById(location.getStripeProductId());

        if (product.isEmpty()) {
            String errorMessage = String.format("Le produit stripe '%s' n'existe pas.", location.getStripeProductId());

            throw new BusinessException(errorMessage);
        }
        newLocation.setStripeProductId(location.getStripeProductId());

        FileEntity file = fileService.saveFile(image);

        newLocation.setName(location.getName());
        newLocation.setImage(file);
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
        newLocation.setSlot_remaining(location.getSlot_remaining());

        return locationRepository.save(newLocation);
    }

    @Override
    public LocationEntity editLocation(Long id, LocationEntity location, MultipartFile image) throws BusinessException {
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

        if (image != null) {
            FileEntity file = fileService.saveFile(image);

            existingLocation.get().setImage(file);
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

        if (location.getSlot_remaining() != 0) {
            existingLocation.get().setSlot_remaining(location.getSlot_remaining());
        }

        return locationRepository.save(existingLocation.get());
    }

    @Override
    public Optional<LocationEntity> deleteById(Long id) {
        try {
            Optional<LocationEntity> location = this.findById(id);

            locationRepository.deleteById(id);

            return location;
        } catch (EmptyResultDataAccessException err) {
            return Optional.empty();
        }
    }
}