package com.feliiks.gardons.sqlmodels;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class ReservationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    @OneToOne
    @JoinColumn(name = "location_id")
    private LocationModel location;
    private String reservation_key;
    private int adult_nbr;
    private int child_nbr;
    private int animal_nbr;
    private int vehicle_nbr;
    private Date from;
    private Date to;
    private int total_price;
    private int night_number;

    public ReservationModel() {
        super();
    }

    public ReservationModel(UserModel user, LocationModel location, String reservation_key, int adult_nbr, int child_nbr, int animal_nbr, int vehicle_nbr, Date from, Date to, int total_price, int night_number) {
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
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
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

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", location=" + location +
                ", reservation_key='" + reservation_key + '\'' +
                ", adult_nbr=" + adult_nbr +
                ", child_nbr=" + child_nbr +
                ", animal_nbr=" + animal_nbr +
                ", vehicle_nbr=" + vehicle_nbr +
                ", from=" + from +
                ", to=" + to +
                ", night_number=" + night_number +
                ", total_price=" + total_price +
                '}';
    }
}
