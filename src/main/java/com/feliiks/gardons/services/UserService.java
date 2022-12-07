package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.ReservationEntity;
import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByGoogleId(String googleId);

    List<ReservationEntity> findUserReservations(Long id) throws BusinessException;

    UserEntity register(UserEntity user) throws BusinessException;

    UserEntity editUser(Long id, UserEntity user) throws BusinessException;

    Optional<UserEntity> deleteById(Long id);
}
