package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.entities.ReservationEntity;
import com.feliiks.gardons.entities.ReservationStatusEnum;
import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.ReservationRepository;
import com.feliiks.gardons.services.LocationService;
import com.feliiks.gardons.services.ReservationService;
import com.feliiks.gardons.services.StripeService;
import com.feliiks.gardons.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationImpl implements ReservationService {
    private final static SecureRandom sr = new SecureRandom();
    public final ReservationRepository reservationRepository;
    public final UserService userService;
    public final LocationService locationService;
    public final StripeService stripeService;

    public ReservationImpl(
            ReservationRepository reservationRepository,
            UserService userService,
            LocationService locationService,
            StripeService stripeService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.locationService = locationService;
        this.stripeService = stripeService;
    }

    @Override
    public List<ReservationEntity> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<ReservationEntity> findById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Optional<ReservationEntity> findBySessionId(String sessionId) {
        return reservationRepository.findBySessionId(sessionId);
    }

    @Override
    public List<ReservationEntity> findByStatus(ReservationStatusEnum status) {
        List<ReservationEntity> reservations = this.findAll();

        return reservations.stream().filter(elt -> elt.getStatus() == status).toList();
    }

    @Override
    public Optional<ReservationEntity> findByReservationKey(String reservationKey) {
        return reservationRepository.findByReservationKey(reservationKey);
    }

    @Override
    public List<ReservationEntity> findByLocation(LocationEntity location) {
        return reservationRepository.findByLocation(location);
    }

    @Override
    public ReservationEntity create(ReservationEntity reservation) throws BusinessException {
        // Validité de la date
        if (reservation.getFrom().compareTo(reservation.getTo()) > 0) {
            String errorMessage = "La date de début ne peut être supérieure à la date de fin.";
            throw new BusinessException(errorMessage);
        }

        // Calcul du nombre de nuits
        int nightNumber = this.totalNights(reservation.getFrom(), reservation.getTo());

        // Validité de l'user et de la location
        Optional<UserEntity> user = userService.findById(reservation.getUser().getId());
        Optional<LocationEntity> location = locationService.findById(reservation.getLocation().getId());

        if (user.isEmpty() || location.isEmpty()) {
            String errorMessage = "Utilisateur ou location inexistant.";
            throw new BusinessException(errorMessage);
        }

        // Validité de la période choisie
        List<ReservationEntity> existingReservations = this.findByLocation(location.get());

        List<ReservationEntity> completeReservations =
                existingReservations
                        .stream()
                        .filter(elt -> Objects.equals(elt.getStatus(), ReservationStatusEnum.COMPLETE))
                        .toList();

        long conflictualReservations =
                completeReservations
                        .stream()
                        .filter(elt -> elt.getFrom().compareTo(reservation.getTo()) < 0 && elt.getTo().compareTo(reservation.getFrom()) > 0)
                        .count();

        if (conflictualReservations >= location.get().getSlot_remaining()) {
            String errorMessage = "La location sélectionnée n'est pas disponible sur la période choisie.";
            throw new BusinessException(errorMessage);
        }

        // Création de la réservation
        ReservationEntity newReservation = new ReservationEntity();
        newReservation.setUser(user.get());
        newReservation.setLocation(location.get());
        newReservation.setReservation_key(StringUtils.substring(String.valueOf(sr.nextLong()), 2, 10));
        newReservation.setAdult_nbr(reservation.getAdult_nbr());
        newReservation.setChild_nbr(reservation.getChild_nbr());
        newReservation.setAnimal_nbr(reservation.getAnimal_nbr());
        newReservation.setVehicle_nbr(reservation.getVehicle_nbr());
        newReservation.setFrom(reservation.getFrom());
        newReservation.setTo(reservation.getTo());
        newReservation.setNight_number(nightNumber);
        newReservation.setTotal_price(this.totalPrice(location.get().getPrice_per_night(), nightNumber));
        newReservation.setStatus(ReservationStatusEnum.OPEN);
        newReservation.setUser_contact(null);
        newReservation.setUser_comment(null);

        return reservationRepository.save(newReservation);
    }

    @Override
    public ReservationEntity editReservation(Long id, ReservationEntity reservation) throws BusinessException {
        Optional<ReservationEntity> existingReservation = this.findById(id);

        // Check de l'existence de la résa
        if (existingReservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        // Update des champs
        if (reservation.getUser() != null && reservation.getUser().getId() != null) {
            Optional<UserEntity> user = userService.findById(reservation.getUser().getId());

            if (user.isEmpty()) {
                String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", reservation.getUser().getId());
                throw new BusinessException(errorMessage);
            }

            existingReservation.get().setUser(user.get());
        }

        if (reservation.getLocation() != null && reservation.getLocation().getId() != null) {
            Optional<LocationEntity> location = locationService.findById(reservation.getLocation().getId());

            if (location.isEmpty()) {
                String errorMessage = String.format("La location '%s' n'existe pas.", reservation.getLocation().getId());
                throw new BusinessException(errorMessage);
            }

            List<ReservationEntity> existingReservations = this.findByLocation(location.get());

            List<ReservationEntity> completeReservations =
                    existingReservations
                            .stream()
                            .filter(elt -> Objects.equals(elt.getStatus(), ReservationStatusEnum.COMPLETE))
                            .toList();

            long conflictualReservations =
                    completeReservations
                            .stream()
                            .filter(elt -> elt.getFrom().compareTo(existingReservation.get().getTo()) < 0 && elt.getTo().compareTo(existingReservation.get().getFrom()) > 0)
                            .count();

            if (conflictualReservations > 0) {
                String errorMessage = "La location sélectionnée n'est pas disponible sur la période choisie.";
                throw new BusinessException(errorMessage);
            }

            existingReservation.get().setTotal_price(this.totalPrice(location.get().getPrice_per_night(), existingReservation.get().getNight_number()));
            existingReservation.get().setLocation(location.get());
        }

        if (reservation.getAdult_nbr() != 0) {
            existingReservation.get().setAdult_nbr(reservation.getAdult_nbr());
        }

        if (reservation.getChild_nbr() != 0) {
            existingReservation.get().setChild_nbr(reservation.getChild_nbr());
        }

        if (reservation.getAnimal_nbr() != 0) {
            existingReservation.get().setAnimal_nbr(reservation.getAnimal_nbr());
        }

        if (reservation.getVehicle_nbr() != 0) {
            existingReservation.get().setVehicle_nbr(reservation.getVehicle_nbr());
        }

        if (reservation.getStripe_session_id() != null) {
            existingReservation.get().setStripe_session_id((reservation.getStripe_session_id()));
        }

        if (reservation.getStatus() != null) {
            existingReservation.get().setStatus((reservation.getStatus()));
        }

        if (reservation.getUser_contact() != null) {
            existingReservation.get().setUser_contact(reservation.getUser_contact());
        }

        if (reservation.getUser_comment() != null) {
            existingReservation.get().setUser_comment((reservation.getUser_comment()));
        }

        return reservationRepository.save(existingReservation.get());
    }


    @Override
    public Optional<ReservationEntity> deleteById(Long id) {
        try {
            Optional<ReservationEntity> reservation = reservationRepository.findById(id);

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