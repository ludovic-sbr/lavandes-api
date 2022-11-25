package com.feliiks.gardons.controllers;

import com.feliiks.gardons.entities.Reservation;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.ReservationService;
import com.feliiks.gardons.viewmodels.*;
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

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "List all reservations.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<GetReservationsResponse> getAllReservations() {
        List<Reservation> reservations = reservationService.findAll();

        return ResponseEntity.status(200).body(new GetReservationsResponse(reservations));
    }

    @Operation(summary = "Get a specific reservation.")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GetReservationResponse> getReservationById(@PathVariable("id") Long id) throws BusinessException {
        Optional<Reservation> reservation = reservationService.findById(id);

        if (reservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetReservationResponse(reservation.get()));
    }

    @Operation(summary = "Create a new reservation.")
    @PostMapping(produces = "application/json")
    public ResponseEntity<PostReservationResponse> saveNewReservation(@RequestBody PostReservationRequest postReservationRequest) throws BusinessException {
        Reservation reservation = reservationService.create(postReservationRequest);

        return ResponseEntity.status(201).body(new PostReservationResponse(reservation));
    }

    @Operation(summary = "Partial update a specific reservation.")
    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<PatchReservationResponse> editReservation(@PathVariable("id") Long id, @RequestBody PatchReservationRequest patchReservationRequest) throws BusinessException {
        Reservation reservation = reservationService.editReservation(id, patchReservationRequest);

        return ResponseEntity.status(200).body(new PatchReservationResponse(reservation));
    }

    @Operation(summary = "Delete a specific reservation.")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<DeleteReservationResponse> deleteReservation(@PathVariable("id") Long id) throws BusinessException {
        Optional<Reservation> reservation = reservationService.deleteById(id);

        if (reservation.isEmpty()) {
            String errorMessage = String.format("La réservation '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new DeleteReservationResponse(reservation.get()));
    }
}
