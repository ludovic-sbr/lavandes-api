package com.feliiks.gardons.dtos;

import com.feliiks.gardons.viewmodels.ReservationEntity;

import java.util.List;

public class GetReservationsResponse {
    private List<ReservationEntity> reservations;

    public GetReservationsResponse(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }
}
