package com.feliiks.gardons.dtos;

import com.feliiks.gardons.viewmodels.ReservationEntity;

public class GetReservationResponse {
    private ReservationEntity reservation;

    public GetReservationResponse(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }
}
