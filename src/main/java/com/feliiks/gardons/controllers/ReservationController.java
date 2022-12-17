package com.feliiks.gardons.controllers;

import com.feliiks.gardons.converters.ReservationConverter;
import com.feliiks.gardons.dtos.*;
import com.feliiks.gardons.entities.ReservationEntity;
import com.feliiks.gardons.entities.ReservationStatusEnum;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.ReservationService;
import com.feliiks.gardons.services.StripeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Reservation")
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    public final ReservationService reservationService;
    public final StripeService stripeService;
    public final ReservationConverter reservationConverter;

    public ReservationController(
            ReservationService reservationService,
            ReservationConverter reservationConverter,
            StripeService stripeService) {
        this.reservationService = reservationService;
        this.reservationConverter = reservationConverter;
        this.stripeService = stripeService;
    }

    @Operation(summary = "List all reservations.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<GetReservationsResponse> getAllReservations() {
        List<ReservationEntity> reservations = reservationService.findAll();

        return ResponseEntity.status(200).body(new GetReservationsResponse(reservations));
    }

    @Operation(summary = "Get a specific reservation.")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable("id") Long id) throws BusinessException {
        Optional<ReservationEntity> reservation = reservationService.findById(id);

        if (reservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new ReservationResponse(reservation.get()));
    }

    @Operation(summary = "Create a new reservation.")
    @PostMapping(produces = "application/json")
    public ResponseEntity<ReservationResponse> saveNewReservation(@RequestBody ReservationRequest reservationRequest) throws BusinessException {
        ReservationEntity reservation = reservationConverter.convertToEntity(reservationRequest);
        ReservationEntity reservationToSave = reservationService.create(reservation);

        return ResponseEntity.status(201).body(new ReservationResponse(reservationToSave));
    }

    @Operation(summary = "Partial update a specific reservation.")
    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ReservationResponse> editReservation(@PathVariable("id") Long id, @RequestBody ReservationRequest reservationRequest) throws BusinessException {
        ReservationEntity reservation = reservationConverter.convertToEntity(reservationRequest);
        ReservationEntity reservationToPatch = reservationService.editReservation(id, reservation);

        return ResponseEntity.status(200).body(new ReservationResponse(reservationToPatch));
    }

    @Operation(summary = "Confirm a specific reservation.")
    @PatchMapping(path = "/{id}/confirm", produces = "application/json")
    public ResponseEntity<ReservationResponse> confirmReservation(@PathVariable("id") Long id, @RequestBody ConfirmReservationRequest confirmReservationRequest) throws BusinessException {
        Optional<ReservationEntity> currentReservation = reservationService.findById(id);

        if (currentReservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        ReservationEntity patch = new ReservationEntity();
        patch.setUser_contact(confirmReservationRequest.getUser_contact());
        patch.setUser_comment(confirmReservationRequest.getUser_comment());

        ReservationEntity reservationToPatch = reservationService.editReservation(currentReservation.get().getId(), patch);

        return ResponseEntity.status(200).body(new ReservationResponse(reservationToPatch));
    }

    @Operation(summary = "Complete/cancel a specific reservation.")
    @PatchMapping(path = "/{id}/complete", produces = "application/json")
    public ResponseEntity<ReservationResponse> completeReservation(@PathVariable("id") Long id, @RequestBody ReservationStatusEnum status) throws BusinessException {
        Optional<ReservationEntity> currentReservation = reservationService.findById(id);

        if (currentReservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        ReservationEntity patch = new ReservationEntity();
        patch.setStatus(status);

        ReservationEntity reservationToPatch = reservationService.editReservation(currentReservation.get().getId(), patch);

        return ResponseEntity.status(200).body(new ReservationResponse(reservationToPatch));
    }

    @Operation(summary = "Delete a specific reservation.")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ReservationResponse> deleteReservation(@PathVariable("id") Long id) throws BusinessException {
        Optional<ReservationEntity> reservation = reservationService.deleteById(id);

        if (reservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new ReservationResponse(reservation.get()));
    }
}
