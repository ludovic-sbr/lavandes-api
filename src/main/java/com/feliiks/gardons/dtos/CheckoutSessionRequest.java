package com.feliiks.gardons.dtos;

import lombok.Data;

@Data
public class CheckoutSessionRequest {
    String productId;
    Long userId;
    Long quantity;
}
