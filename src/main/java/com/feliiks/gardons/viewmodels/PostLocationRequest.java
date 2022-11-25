package com.feliiks.gardons.viewmodels;

import lombok.Data;

@Data
public class PostLocationRequest {
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
    int slot_number;
}
