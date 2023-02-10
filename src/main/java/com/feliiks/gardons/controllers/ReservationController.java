package com.feliiks.gardons.controllers;

import com.feliiks.gardons.converters.ReservationConverter;
import com.feliiks.gardons.dtos.*;
import com.feliiks.gardons.entities.ReservationEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.ReservationService;
import com.feliiks.gardons.services.StripeService;
import com.stripe.model.checkout.Session;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Tag(name = "Reservation")
@RestController
@RequestMapping(value = "/reservation", produces = "application/json; charset=utf-8")
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
    @GetMapping()
    public ResponseEntity<GetReservationsResponse> getAllReservations() {
        List<ReservationEntity> reservations = reservationService.findAll();

        return ResponseEntity.status(200).body(new GetReservationsResponse(reservations));
    }

    @Operation(summary = "Get a specific reservation.")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable("id") Long id) throws BusinessException {
        Optional<ReservationEntity> reservation = reservationService.findById(id);

        if (reservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new ReservationResponse(reservation.get()));
    }

    @Operation(summary = "Get a specific reservation by session id.")
    @GetMapping(path = "/sessionId/{sessionId}")
    public ResponseEntity<ReservationResponse> getReservationBySessionId(@PathVariable("sessionId") String sessionId) throws BusinessException {
        Optional<ReservationEntity> reservation = reservationService.findBySessionId(sessionId);

        if (reservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", sessionId);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new ReservationResponse(reservation.get()));
    }

    @Operation(summary = "Create a new reservation.")
    @PostMapping()
    public ResponseEntity<ReservationResponse> saveNewReservation(@RequestBody ReservationRequest reservationRequest) throws BusinessException {
        ReservationEntity reservation = reservationConverter.convertToEntity(reservationRequest);
        ReservationEntity reservationToSave = reservationService.create(reservation);

        return ResponseEntity.status(201).body(new ReservationResponse(reservationToSave));
    }

    @Operation(summary = "Partial update a specific reservation.")
    @PatchMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> editReservation(@PathVariable("id") Long id, @RequestBody ReservationRequest reservationRequest) throws BusinessException {
        ReservationEntity reservation = reservationConverter.convertToEntity(reservationRequest);
        ReservationEntity reservationToPatch = reservationService.editReservation(id, reservation);

        return ResponseEntity.status(200).body(new ReservationResponse(reservationToPatch));
    }

    @Operation(summary = "Confirm a specific reservation.")
    @PatchMapping(path = "/{id}/confirm")
    public ResponseEntity<ConfirmReservationResponse> confirmReservation(@PathVariable("id") Long id, @RequestBody ConfirmReservationRequest confirmReservationRequest) throws BusinessException {
        Optional<ReservationEntity> currentReservation = reservationService.findById(id);

        if (currentReservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        if (Objects.equals(confirmReservationRequest.getUser_contact(), "")) {
            throw new BusinessException("Vous devez fournir un numéro de téléphone pour la réservation.");
        }

        // Récupération du contact et comment de l'user
        ReservationEntity patch = new ReservationEntity();
        patch.setUser_contact(confirmReservationRequest.getUser_contact());
        patch.setUser_comment(confirmReservationRequest.getUser_comment());

        // Création de la checkout session stripe
        CheckoutSessionRequest csr = new CheckoutSessionRequest();
        csr.setProductId(currentReservation.get().getLocation().getStripeProductId());
        csr.setUserId(currentReservation.get().getUser().getId());
        csr.setQuantity((long) currentReservation.get().getNight_number());
        Session checkoutSession = stripeService.createCheckoutSession(csr);
        patch.setStripe_session_id(checkoutSession.getId());

        // Patch de la réservation
        ReservationEntity reservationToPatch = reservationService.editReservation(currentReservation.get().getId(), patch);

        return ResponseEntity.status(200).body(new ConfirmReservationResponse(reservationToPatch, checkoutSession.getUrl()));
    }

    @Operation(summary = "Complete/cancel a specific reservation.")
    @PatchMapping(path = "/{id}/complete")
    public ResponseEntity<ReservationResponse> completeReservation(@PathVariable("id") Long id, @RequestBody CompleteReservationRequest completeReservationRequest) throws BusinessException {
        Optional<ReservationEntity> currentReservation = reservationService.findById(id);

        if (currentReservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        ReservationEntity patch = new ReservationEntity();
        patch.setStatus(completeReservationRequest.getStatus());

        ReservationEntity reservationToPatch = reservationService.editReservation(currentReservation.get().getId(), patch);

        return ResponseEntity.status(200).body(new ReservationResponse(reservationToPatch));
    }

    @Operation(summary = "Delete a specific reservation.")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> deleteReservation(@PathVariable("id") Long id) throws BusinessException {
        Optional<ReservationEntity> reservation = reservationService.deleteById(id);

        if (reservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new ReservationResponse(reservation.get()));
    }
}
