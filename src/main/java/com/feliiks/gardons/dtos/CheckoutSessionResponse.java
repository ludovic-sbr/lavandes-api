package com.feliiks.gardons.dtos;

public class CheckoutSessionResponse {
    private String customerEmail;
    private String paymentStatus;
    private String mode;
    private String sessionUrl;

    public CheckoutSessionResponse(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }

    public String getSessionUrl() {
        return sessionUrl;
    }

    public void setSessionUrl(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }
}
