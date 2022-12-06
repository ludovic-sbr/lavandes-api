package com.feliiks.gardons.converters;

import com.stripe.model.Product;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeConverter {
    public SessionCreateParams.LineItem convertToLineItem(Product product, Long quantity) {

        return SessionCreateParams.LineItem.builder()
                .setPrice(product.getDefaultPrice())
                .setQuantity(quantity)
                .build();
    }
}
