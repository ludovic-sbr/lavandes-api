package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.ReservationStatusEnum;
import lombok.Data;

@Data
public class CompleteReservationRequest {
    ReservationStatusEnum status;
}
