package com.feliiks.gardons.services;

import com.feliiks.gardons.dtos.CheckoutSessionRequest;
import com.feliiks.gardons.exceptions.BusinessException;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface StripeService {
    Optional<Product> findProductById(String productId) throws BusinessException;

    Session createCheckoutSession(CheckoutSessionRequest checkoutSessionRequest) throws BusinessException;

    Session getCheckoutSession(String sessionId) throws BusinessException;
}
