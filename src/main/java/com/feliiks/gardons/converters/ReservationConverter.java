package com.feliiks.gardons.converters;

import com.feliiks.gardons.dtos.PatchReservationRequest;
import com.feliiks.gardons.dtos.PostReservationRequest;
import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.entities.ReservationEntity;
import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.models.ReservationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationConverter {

    @Autowired
    private ModelMapper mapper;

    public ReservationEntity convertToEntity(PostReservationRequest reservationRequest) {
        mapper.typeMap(PostReservationRequest.class, ReservationEntity.class).addMappings(elt -> {
            elt.skip(ReservationEntity::setId);
            elt.skip(ReservationEntity::setReservation_key);
            elt.skip(ReservationEntity::setTotal_price);
            elt.skip(ReservationEntity::setNight_number);
        });

        ReservationEntity reservationEntity = mapper.map(reservationRequest, ReservationEntity.class);

        if (reservationRequest.getUser_id() != null) {
            UserEntity user = new UserEntity();
            user.setId(reservationRequest.getUser_id());
            reservationEntity.setUser(user);
        }

        if (reservationRequest.getLocation_id() != null) {
            LocationEntity location = new LocationEntity();
            location.setId(reservationRequest.getLocation_id());
            reservationEntity.setLocation(location);
        }

        return reservationEntity;
    }

    public ReservationEntity convertToEntity(PatchReservationRequest reservationRequest) {
        mapper.typeMap(PatchReservationRequest.class, ReservationEntity.class).addMappings(elt -> {
            elt.skip(ReservationEntity::setId);
            elt.skip(ReservationEntity::setReservation_key);
            elt.skip(ReservationEntity::setTotal_price);
            elt.skip(ReservationEntity::setNight_number);
            elt.skip(ReservationEntity::setFrom);
            elt.skip(ReservationEntity::setTo);
        });

        ReservationEntity reservationEntity = mapper.map(reservationRequest, ReservationEntity.class);

        if (reservationRequest.getUser_id() != null) {
            UserEntity user = new UserEntity();
            user.setId(reservationRequest.getUser_id());
            reservationEntity.setUser(user);
        }

        if (reservationRequest.getLocation_id() != null) {
            LocationEntity location = new LocationEntity();
            location.setId(reservationRequest.getLocation_id());
            reservationEntity.setLocation(location);
        }

        return reservationEntity;
    }

    public ReservationEntity convertToEntity(ReservationModel reservation) {
        return mapper.map(reservation, ReservationEntity.class);
    }

    public ReservationModel convertToModel(ReservationEntity reservation) {
        return mapper.map(reservation, ReservationModel.class);
    }
}
