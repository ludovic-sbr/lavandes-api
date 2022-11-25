package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Reservation;

public class GetReservationResponse {
    private Reservation reservation;

    public GetReservationResponse(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
