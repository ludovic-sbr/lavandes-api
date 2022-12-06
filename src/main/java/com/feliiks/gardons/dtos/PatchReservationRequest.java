package com.feliiks.gardons.dtos;

import com.feliiks.gardons.viewmodels.ReservationEntity;
import lombok.Data;

@Data
public class PatchReservationRequest {
    Long user_id;
    Long location_id;
    int adult_nbr;
    int child_nbr;
    int animal_nbr;
    int vehicle_nbr;
    ReservationEntity.ReservationStatusEnum status;
}