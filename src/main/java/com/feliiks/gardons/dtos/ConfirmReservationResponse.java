package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.ReservationEntity;

public class ConfirmReservationResponse {
    private ReservationEntity reservation;
    private String stripe_session_url;

    public ConfirmReservationResponse(ReservationEntity reservation, String stripe_session_url) {
        this.reservation = reservation;
        this.stripe_session_url = stripe_session_url;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public String getStripe_session_url() {
        return stripe_session_url;
    }

    public void setStripe_session_url(String stripe_session_url) {
        this.stripe_session_url = stripe_session_url;
    }
}
