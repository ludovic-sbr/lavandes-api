package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.ReservationStatusEnum;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationRequest {
    Long user_id;
    Long location_id;
    int adult_nbr;
    int child_nbr;
    int animal_nbr;
    int vehicle_nbr;
    Date from;
    Date to;
    ReservationStatusEnum status;
    String user_contact;
    String user_comment;
}
