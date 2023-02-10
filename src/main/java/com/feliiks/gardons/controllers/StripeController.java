package com.feliiks.gardons.controllers;

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
@RequestMapping(value = "/stripe", produces = "application/json; charset=utf-8")
public class StripeController {
    public final StripeService stripeService;
    public final ReservationService reservationService;

    public StripeController(
            StripeService stripeService,
            ReservationService reservationService) {
        this.stripeService = stripeService;
        this.reservationService = reservationService;
    }

    @Operation(summary = "Get a specific stripe session.")
    @GetMapping(path = "/checkout-session/{sessionId}")
    public ResponseEntity<CheckoutSessionResponse> findCheckoutSessionById(@PathVariable("sessionId") String sessionId) throws BusinessException {
        Session checkoutSession = stripeService.getCheckoutSession(sessionId);

        return ResponseEntity.status(200).body(new CheckoutSessionResponse(checkoutSession.getUrl()));
    }

    @Operation(summary = "Create new charge.")
    @PostMapping(path = "/checkout-session")
    public ResponseEntity<CheckoutSessionResponse> charge(@RequestBody CheckoutSessionRequest checkoutSessionRequest) throws BusinessException {
        Session checkoutSession = stripeService.createCheckoutSession(checkoutSessionRequest);

        return ResponseEntity.status(200).body(new CheckoutSessionResponse(checkoutSession.getUrl()));
    }
}
