package com.feliiks.gardons.implementations;

import com.feliiks.gardons.converters.StripeConverter;
import com.feliiks.gardons.dtos.CheckoutSessionRequest;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.StripeService;
import com.feliiks.gardons.services.UserService;
import com.feliiks.gardons.viewmodels.UserEntity;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class StripeImpl implements StripeService {
    public final UserService userService;
    private final StripeConverter stripeConverter;
    String failedUrl = "http://localhost:3000/payment/canceled?session_id={CHECKOUT_SESSION_ID}";
    String successUrl = "http://localhost:3000/payment/success?session_id={CHECKOUT_SESSION_ID}";
    @Value("${app.stripeSecretKey}")
    private String stripeSecretKey;

    public StripeImpl(
            StripeConverter stripeConverter,
            UserService userService) {
        this.stripeConverter = stripeConverter;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public Optional<Product> findProductById(String productId) throws BusinessException {
        try {
            Product product = Product.retrieve(productId);

            if (product == null) return Optional.empty();

            return Optional.of(product);
        } catch (StripeException e) {
            String errorMessage = String.format("Le produit stripe '%s' n'existe pas.", productId);

            throw new BusinessException(errorMessage);
        }
    }

    public Session getCheckoutSession(String sessionId) throws BusinessException {
        try {
            return Session.retrieve(sessionId);
        } catch (StripeException e) {
            String errorMessage = String.format("Aucune session trouvée pour l'id '%s'.", sessionId);

            throw new BusinessException(errorMessage);
        }
    }

    public Session createCheckoutSession(CheckoutSessionRequest checkoutSessionRequest) throws BusinessException {
        try {
            Optional<UserEntity> customer = userService.findById(checkoutSessionRequest.getUserId());

            if (customer.isEmpty()) {
                String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", checkoutSessionRequest.getUserId());

                throw new BusinessException(errorMessage);
            }

            Optional<Product> product = findProductById(checkoutSessionRequest.getProductId());

            if (product.isEmpty()) {
                String errorMessage = String.format("Le produit stripe '%s' n'existe pas.", checkoutSessionRequest.getProductId());

                throw new BusinessException(errorMessage);
            }

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .addLineItem(stripeConverter.convertToLineItem(product.get(), checkoutSessionRequest.getQuantity()))
                            .setCustomerEmail(customer.get().getEmail())
                            .setCancelUrl(failedUrl)
                            .setSuccessUrl(successUrl)
                            .build();
            return Session.create(params);
        } catch (StripeException e) {
            throw new BusinessException(e.getUserMessage());
        }

    }
}
