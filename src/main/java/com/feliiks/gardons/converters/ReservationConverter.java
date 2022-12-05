package com.feliiks.gardons.converters;

import com.feliiks.gardons.dtos.PatchReservationRequest;
import com.feliiks.gardons.dtos.PostReservationRequest;
import com.feliiks.gardons.sqlmodels.ReservationModel;
import com.feliiks.gardons.viewmodels.LocationEntity;
import com.feliiks.gardons.viewmodels.ReservationEntity;
import com.feliiks.gardons.viewmodels.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationConverter {

    @Autowired
    private ModelMapper mapper;

    public ReservationEntity convertToEntity(PostReservationRequest postReservationRequest) {
        mapper.typeMap(PostReservationRequest.class, ReservationEntity.class).addMappings(elt -> {
            elt.skip(ReservationEntity::setId);
            elt.skip(ReservationEntity::setReservation_key);
            elt.skip(ReservationEntity::setTotal_price);
            elt.skip(ReservationEntity::setNight_number);
        });

        ReservationEntity reservationEntity = mapper.map(postReservationRequest, ReservationEntity.class);

        UserEntity user = new UserEntity();
        user.setId(postReservationRequest.getUser_id());
        reservationEntity.setUser(user);

        LocationEntity location = new LocationEntity();
        location.setId(postReservationRequest.getLocation_id());
        reservationEntity.setLocation(location);

        return reservationEntity;
    }

    public ReservationEntity convertToEntity(PatchReservationRequest patchReservationRequest) {
        mapper.typeMap(PatchReservationRequest.class, ReservationEntity.class).addMappings(elt -> {
            elt.skip(ReservationEntity::setId);
            elt.skip(ReservationEntity::setReservation_key);
            elt.skip(ReservationEntity::setTotal_price);
            elt.skip(ReservationEntity::setNight_number);
            elt.skip(ReservationEntity::setFrom);
            elt.skip(ReservationEntity::setTo);
        });

        ReservationEntity reservationEntity = mapper.map(patchReservationRequest, ReservationEntity.class);

        UserEntity user = new UserEntity();
        user.setId(patchReservationRequest.getUser_id());
        reservationEntity.setUser(user);

        LocationEntity location = new LocationEntity();
        location.setId(patchReservationRequest.getLocation_id());
        reservationEntity.setLocation(location);

        return reservationEntity;
    }

    public ReservationEntity convertToEntity(ReservationModel reservation) {
        return mapper.map(reservation, ReservationEntity.class);
    }

    public ReservationModel convertToModel(ReservationEntity reservation) {
        return mapper.map(reservation, ReservationModel.class);
    }
}
