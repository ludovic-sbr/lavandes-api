package com.feliiks.gardons.repositories;

import com.feliiks.gardons.converters.ReservationConverter;
import com.feliiks.gardons.converters.UserConverter;
import com.feliiks.gardons.entities.ReservationEntity;
import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.models.ReservationModel;
import com.feliiks.gardons.models.UserModel;
import com.feliiks.gardons.repositories.jpa.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserConverter userConverter;
    private final ReservationConverter reservationConverter;

    public UserRepository(
            UserJpaRepository userJpaRepository,
            UserConverter userConverter,
            ReservationConverter reservationConverter) {
        this.userJpaRepository = userJpaRepository;
        this.userConverter = userConverter;
        this.reservationConverter = reservationConverter;
    }

    public List<UserEntity> findAll() {
        List<UserModel> allUsers = userJpaRepository.findAll();

        return allUsers.stream().map(userConverter::convertToEntity).toList();
    }

    public Optional<UserEntity> findById(Long id) {
        Optional<UserModel> user = userJpaRepository.findById(id);

        if (user.isEmpty()) return Optional.empty();

        return Optional.of(userConverter.convertToEntity(user.get()));
    }

    public Optional<UserEntity> findByEmail(String email) {
        Optional<UserModel> user = userJpaRepository.findByEmail(email);

        if (user.isEmpty()) return Optional.empty();

        return Optional.of(userConverter.convertToEntity(user.get()));
    }

    public Optional<UserEntity> findByGoogleId(String googleId) {
        Optional<UserModel> user = userJpaRepository.findByGoogleId(googleId);

        if (user.isEmpty()) return Optional.empty();

        return Optional.of(userConverter.convertToEntity(user.get()));
    }

    public List<ReservationEntity> findUserReservations(Long id) {
        List<ReservationModel> userReservations = userJpaRepository.findUserReservations(id);

        return userReservations.stream().map(reservationConverter::convertToEntity).toList();
    }

    public UserEntity save(UserEntity user) {
        UserModel mappedUser = userConverter.convertToModel(user);

        UserModel savedUser = userJpaRepository.save(mappedUser);

        return userConverter.convertToEntity(savedUser);
    }

    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }
}
