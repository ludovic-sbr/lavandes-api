package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Reservation;

public class DeleteReservationResponse {
    private Reservation reservation;

    public DeleteReservationResponse(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
