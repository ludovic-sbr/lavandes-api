package com.feliiks.gardons.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PostReservationRequest {
    Long user_id;
    Long location_id;
    int adult_nbr;
    int child_nbr;
    int animal_nbr;
    int vehicle_nbr;
    Date from;
    Date to;
}
