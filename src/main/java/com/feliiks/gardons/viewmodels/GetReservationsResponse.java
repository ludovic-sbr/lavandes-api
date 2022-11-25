package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Reservation;

import java.util.List;

public class GetReservationsResponse {
    private List<Reservation> reservations;

    public GetReservationsResponse(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
