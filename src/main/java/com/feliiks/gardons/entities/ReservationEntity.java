package com.feliiks.gardons.entities;

import java.util.Date;

public class ReservationEntity {
    private Long id;
    private UserEntity user;
    private LocationEntity location;
    private String reservation_key;
    private int adult_nbr;
    private int child_nbr;
    private int animal_nbr;
    private int vehicle_nbr;
    private Date from;
    private Date to;
    private int total_price;
    private int night_number;
    private String stripe_session_id;
    private ReservationStatusEnum status;
    private String user_contact;
    private String user_comment;

    public ReservationEntity() {
    }

    public ReservationEntity(
            UserEntity user,
            LocationEntity location,
            String reservation_key,
            int adult_nbr,
            int child_nbr,
            int animal_nbr,
            int vehicle_nbr,
            Date from,
            Date to,
            int total_price,
            int night_number,
            String stripe_session_id,
            ReservationStatusEnum status,
            String user_contact,
            String user_comment) {
        this.user = user;
        this.location = location;
        this.reservation_key = reservation_key;
        this.adult_nbr = adult_nbr;
        this.child_nbr = child_nbr;
        this.animal_nbr = animal_nbr;
        this.vehicle_nbr = vehicle_nbr;
        this.from = from;
        this.to = to;
        this.total_price = total_price;
        this.night_number = night_number;
        this.stripe_session_id = stripe_session_id;
        this.status = status;
        this.user_contact = user_contact;
        this.user_comment = user_comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public String getReservation_key() {
        return reservation_key;
    }

    public void setReservation_key(String reservation_key) {
        this.reservation_key = reservation_key;
    }

    public int getAdult_nbr() {
        return adult_nbr;
    }

    public void setAdult_nbr(int adult_nbr) {
        this.adult_nbr = adult_nbr;
    }

    public int getChild_nbr() {
        return child_nbr;
    }

    public void setChild_nbr(int child_nbr) {
        this.child_nbr = child_nbr;
    }

    public int getAnimal_nbr() {
        return animal_nbr;
    }

    public void setAnimal_nbr(int animal_nbr) {
        this.animal_nbr = animal_nbr;
    }

    public int getVehicle_nbr() {
        return vehicle_nbr;
    }

    public void setVehicle_nbr(int vehicle_nbr) {
        this.vehicle_nbr = vehicle_nbr;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getNight_number() {
        return night_number;
    }

    public void setNight_number(int night_number) {
        this.night_number = night_number;
    }

    public String getStripe_session_id() {
        return stripe_session_id;
    }

    public void setStripe_session_id(String stripe_session_id) {
        this.stripe_session_id = stripe_session_id;
    }

    public ReservationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ReservationStatusEnum status) {
        this.status = status;
    }

    public String getUser_contact() {
        return user_contact;
    }

    public void setUser_contact(String user_contact) {
        this.user_contact = user_contact;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }
}
