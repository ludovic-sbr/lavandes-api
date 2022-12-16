package com.feliiks.gardons.dtos;

import lombok.Data;

@Data
public class ConfirmReservationRequest {
    String user_contact;
    String user_comment;
}
