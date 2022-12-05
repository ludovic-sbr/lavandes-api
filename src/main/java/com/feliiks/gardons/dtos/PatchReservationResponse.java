package com.feliiks.gardons.dtos;

import com.feliiks.gardons.viewmodels.ReservationEntity;

public class PatchReservationResponse {
    private ReservationEntity reservation;

    public PatchReservationResponse(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }
}
