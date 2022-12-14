package com.feliiks.gardons.controllers;

import com.feliiks.gardons.converters.ReservationConverter;
import com.feliiks.gardons.dtos.*;
import com.feliiks.gardons.entities.ReservationEntity;
import com.feliiks.gardons.entities.ReservationStatusEnum;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.ReservationService;
import com.feliiks.gardons.services.StripeService;
import com.stripe.model.checkout.Session;
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
    public ResponseEntity<GetReservationResponse> getReservationById(@PathVariable("id") Long id) throws BusinessException {
        Optional<ReservationEntity> reservation = reservationService.findById(id);

        if (reservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetReservationResponse(reservation.get()));
    }

    @Operation(summary = "Create a new reservation.")
    @PostMapping(produces = "application/json")
    public ResponseEntity<PostReservationResponse> saveNewReservation(@RequestBody PostReservationRequest postReservationRequest) throws BusinessException {
        ReservationEntity reservation = reservationConverter.convertToEntity(postReservationRequest);
        ReservationEntity reservationToSave = reservationService.create(reservation);
        Session checkoutSession = stripeService.getCheckoutSession(reservationToSave.getStripe_session_id());

        return ResponseEntity.status(201).body(new PostReservationResponse(reservationToSave, checkoutSession.getUrl()));
    }

    @Operation(summary = "Partial update a specific reservation.")
    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<PatchReservationResponse> editReservation(@PathVariable("id") Long id, @RequestBody PatchReservationRequest patchReservationRequest) throws BusinessException {
        ReservationEntity reservation = reservationConverter.convertToEntity(patchReservationRequest);
        ReservationEntity reservationToPatch = reservationService.editReservation(id, reservation);

        return ResponseEntity.status(200).body(new PatchReservationResponse(reservationToPatch));
    }

    @Operation(summary = "Update the status of a specific reservation.")
    @PatchMapping(path = "/{sessionId}/status", produces = "application/json")
    public ResponseEntity<PatchReservationResponse> editReservation(@PathVariable("sessionId") String sessionId, @RequestBody ReservationStatusEnum status) throws BusinessException {
        Optional<ReservationEntity> currentReservation = reservationService.findBySessionId(sessionId);

        if (currentReservation.isEmpty()) {
            String errorMessage = String.format("La réservation avec le session ID '%s' n'existe pas.", sessionId);
            throw new BusinessException(errorMessage);
        }

        ReservationEntity patch = new ReservationEntity();
        patch.setStatus(status);

        ReservationEntity reservationToPatch = reservationService.editReservation(currentReservation.get().getId(), patch);

        return ResponseEntity.status(200).body(new PatchReservationResponse(reservationToPatch));
    }

    @Operation(summary = "Delete a specific reservation.")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<DeleteReservationResponse> deleteReservation(@PathVariable("id") Long id) throws BusinessException {
        Optional<ReservationEntity> reservation = reservationService.deleteById(id);

        if (reservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new DeleteReservationResponse(reservation.get()));
    }
}
