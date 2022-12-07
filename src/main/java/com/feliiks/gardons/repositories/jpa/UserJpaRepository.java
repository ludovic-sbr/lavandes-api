package com.feliiks.gardons.repositories.jpa;

import com.feliiks.gardons.models.ReservationModel;
import com.feliiks.gardons.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "rest")
public interface UserJpaRepository extends JpaRepository<UserModel, Long> {
    @Query("select user from UserModel user where user.email = :email")
    Optional<UserModel> findByEmail(@Param("email") String email);

    @Query("select user from UserModel user where user.google_id = :googleId")
    Optional<UserModel> findByGoogleId(@Param("googleId") String googleId);

    @Query("select reservation from ReservationModel reservation where reservation.user.id = :id")
    List<ReservationModel> findUserReservations(Long id);

    void deleteById(Long id);
}
