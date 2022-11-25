package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Location;
import com.feliiks.gardons.entities.Reservation;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.ReservationRepository;
import com.feliiks.gardons.services.LocationService;
import com.feliiks.gardons.services.ReservationService;
import com.feliiks.gardons.services.UserService;
import com.feliiks.gardons.viewmodels.PatchReservationRequest;
import com.feliiks.gardons.viewmodels.PostReservationRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationImpl implements ReservationService {
    private final static SecureRandom sr = new SecureRandom();
    public final ReservationRepository reservationRepository;
    public final UserService userService;
    public final LocationService locationService;

    public ReservationImpl(ReservationRepository reservationRepository, UserService userService, LocationService locationService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.locationService = locationService;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Optional<Reservation> findByReservationKey(String reservationKey) {
        return reservationRepository.findByReservationKey(reservationKey);
    }

    @Override
    public List<Reservation> findByLocation(Location location) {
        return reservationRepository.findByLocation(location);
    }

    @Override
    public Reservation create(PostReservationRequest postReservationRequest) throws BusinessException {
        // Validité de la date
        if (postReservationRequest.getFrom().compareTo(postReservationRequest.getTo()) > 0) {
            String errorMessage = "La date de début ne peut être supérieure à la date de fin.";
            throw new BusinessException(errorMessage);
        }

        // Validité de l'user et de la location
        Optional<User> user = userService.findById(postReservationRequest.getUser_id());
        Optional<Location> location = locationService.findById(postReservationRequest.getLocation_id());

        if (user.isEmpty() || location.isEmpty()) {
            String errorMessage = "Utilisateur ou location inexistant.";
            throw new BusinessException(errorMessage);
        }

        // Validité de la période choisie
        List<Reservation> existingReservations = this.findByLocation(location.get());
        long nonConflictualReservations =
                existingReservations
                        .stream()
                        .filter(elt ->
                                (elt.getFrom().compareTo(postReservationRequest.getFrom()) < 0 && elt.getTo().compareTo(postReservationRequest.getFrom()) < 0)
                                        || (elt.getFrom().compareTo(postReservationRequest.getTo()) > 0 && elt.getTo().compareTo(postReservationRequest.getTo()) > 0))
                        .count();

        if (nonConflictualReservations < existingReservations.toArray().length) {
            String errorMessage = "La location sélectionnée n'est pas disponible sur la période choisie.";
            throw new BusinessException(errorMessage);
        }

        // Création de la réservation
        Reservation newReservation = new Reservation();
        newReservation.setUser(user.get());
        newReservation.setLocation(location.get());
        newReservation.setReservation_key(StringUtils.substring("RES" + sr.nextLong(), 0, 12));
        newReservation.setAdult_nbr(postReservationRequest.getAdult_nbr());
        newReservation.setChild_nbr(postReservationRequest.getChild_nbr());
        newReservation.setAnimal_nbr(postReservationRequest.getAnimal_nbr());
        newReservation.setVehicle_nbr(postReservationRequest.getVehicle_nbr());
        newReservation.setFrom(postReservationRequest.getFrom());
        newReservation.setTo(postReservationRequest.getTo());
        int nightNumber = this.totalNights(postReservationRequest.getFrom(), postReservationRequest.getTo());
        newReservation.setNight_number(nightNumber);
        newReservation.setTotal_price(this.totalPrice(location.get().getPrice_per_night(), nightNumber));

        return reservationRepository.save(newReservation);
    }

    @Override
    public Reservation editReservation(Long id, PatchReservationRequest patchReservationRequest) throws BusinessException {
        Optional<Reservation> reservation = this.findById(id);

        // Check de l'existence de la résa
        if (reservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        // Update des champs
        if (patchReservationRequest.getUser_id() != null) {
            Optional<User> user = userService.findById(patchReservationRequest.getUser_id());

            if (user.isEmpty()) {
                String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", patchReservationRequest.getUser_id());
                throw new BusinessException(errorMessage);
            }

            reservation.get().setUser(user.get());
        }

        if (patchReservationRequest.getLocation_id() != null) {
            Optional<Location> location = locationService.findById(patchReservationRequest.getLocation_id());

            if (location.isEmpty()) {
                String errorMessage = String.format("La location '%s' n'existe pas.", patchReservationRequest.getLocation_id());
                throw new BusinessException(errorMessage);
            }

            List<Reservation> existingReservations = this.findByLocation(location.get());
            long nonConflictualReservations =
                    existingReservations
                            .stream()
                            .filter(elt ->
                                    (elt.getFrom().compareTo(reservation.get().getFrom()) < 0 && elt.getTo().compareTo(reservation.get().getFrom()) < 0)
                                            || (elt.getFrom().compareTo(reservation.get().getTo()) > 0 && elt.getTo().compareTo(reservation.get().getTo()) > 0))
                            .count();

            if (nonConflictualReservations < existingReservations.toArray().length) {
                String errorMessage = "La location sélectionnée n'est pas disponible sur la période choisie.";
                throw new BusinessException(errorMessage);
            }

            reservation.get().setTotal_price(this.totalPrice(location.get().getPrice_per_night(), reservation.get().getNight_number()));
            reservation.get().setLocation(location.get());
        }

        if (patchReservationRequest.getAdult_nbr() != 0) {
            reservation.get().setAdult_nbr(patchReservationRequest.getAdult_nbr());
        }

        if (patchReservationRequest.getChild_nbr() != 0) {
            reservation.get().setChild_nbr(patchReservationRequest.getChild_nbr());
        }

        if (patchReservationRequest.getAnimal_nbr() != 0) {
            reservation.get().setAnimal_nbr(patchReservationRequest.getAnimal_nbr());

        }

        if (patchReservationRequest.getVehicle_nbr() != 0) {
            reservation.get().setVehicle_nbr(patchReservationRequest.getVehicle_nbr());

        }

        return reservationRepository.save(reservation.get());
    }


    @Override
    public Optional<Reservation> deleteById(Long id) {
        try {
            Optional<Reservation> reservation = reservationRepository.findById(id);

            reservationRepository.deleteById(id);

            return reservation;
        } catch (EmptyResultDataAccessException err) {
            return Optional.empty();
        }
    }

    public int totalNights(Date from, Date to) {
        return (int) ChronoUnit.DAYS.between(from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public int totalPrice(int nightNumber, int pricePerNight) {
        return nightNumber * pricePerNight;
    }
}