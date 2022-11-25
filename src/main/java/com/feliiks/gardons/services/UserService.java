package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.Reservation;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.viewmodels.PatchUserRequest;
import com.feliiks.gardons.viewmodels.PostUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<Reservation> findUserReservations(Long id) throws BusinessException;

    User register(PostUserRequest registerUserRequest) throws BusinessException;

    User editUser(Long id, PatchUserRequest patchUserRequest) throws BusinessException;

    Optional<User> deleteById(Long id);
}
