package com.feliiks.gardons.controllers;

import com.feliiks.gardons.converters.StripeConverter;
import com.feliiks.gardons.dtos.CheckoutSessionRequest;
import com.feliiks.gardons.dtos.CheckoutSessionResponse;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.ReservationService;
import com.feliiks.gardons.services.StripeService;
import com.stripe.model.checkout.Session;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment")
@RestController
@RequestMapping("/stripe")
public class StripeController {
    public final StripeService stripeService;
    public final ReservationService reservationService;
    private final StripeConverter stripeConverter;

    public StripeController(
            StripeService stripeService,
            StripeConverter stripeConverter,
            ReservationService reservationService) {
        this.stripeService = stripeService;
        this.stripeConverter = stripeConverter;
        this.reservationService = reservationService;
    }

    @Operation(summary = "Get a specific stripe session.")
    @GetMapping(path = "/checkout-session/{sessionId}", produces = "application/json")
    public ResponseEntity<CheckoutSessionResponse> findCheckoutSessionById(@PathVariable("sessionId") String sessionId) throws BusinessException {
        Session checkoutSession = stripeService.getCheckoutSession(sessionId);

        return ResponseEntity.status(200).body(new CheckoutSessionResponse(checkoutSession.getUrl()));
    }

    @Operation(summary = "Create new charge.")
    @PostMapping(path = "/checkout-session", produces = "application/json")
    public ResponseEntity<CheckoutSessionResponse> charge(CheckoutSessionRequest checkoutSessionRequest) throws BusinessException {
        Session checkoutSession = stripeService.createCheckoutSession(checkoutSessionRequest);

        return ResponseEntity.status(200).body(new CheckoutSessionResponse(checkoutSession.getUrl()));
    }
}
