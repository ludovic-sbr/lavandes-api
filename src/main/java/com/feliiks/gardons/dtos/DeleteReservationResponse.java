package com.feliiks.gardons.dtos;

import com.feliiks.gardons.viewmodels.ReservationEntity;

public class DeleteReservationResponse {
    private ReservationEntity reservation;

    public DeleteReservationResponse(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }
}
