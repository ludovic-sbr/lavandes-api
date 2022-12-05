package com.feliiks.gardons.dtos;

import com.feliiks.gardons.viewmodels.ReservationEntity;

public class PostReservationResponse {
    private ReservationEntity reservation;

    public PostReservationResponse(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }
}
