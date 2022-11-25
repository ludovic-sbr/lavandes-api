package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Reservation;

public class PatchReservationResponse {
    private Reservation reservation;

    public PatchReservationResponse(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
