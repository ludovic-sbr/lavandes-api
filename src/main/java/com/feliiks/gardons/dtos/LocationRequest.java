package com.feliiks.gardons.dtos;

import lombok.Data;

@Data
public class LocationRequest {
    String name;
    String stripeProductId;
    String description;
    Boolean parking;
    Boolean kitchen;
    Boolean wifi;
    Boolean sanitary;
    Boolean heater;
    Boolean air_conditioner;
    Boolean terrace;
    Boolean barbecue;
    int surface;
    int max_persons;
    int price_per_night;
    int bedrooms;
    int slot_remaining;
}
