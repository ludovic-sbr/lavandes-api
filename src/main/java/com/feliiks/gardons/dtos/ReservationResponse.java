package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.ReservationEntity;

public class ReservationResponse {
    private ReservationEntity reservation;

    public ReservationResponse(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }
}