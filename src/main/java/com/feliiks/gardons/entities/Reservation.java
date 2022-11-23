package com.feliiks.gardons.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;
    private String reservation_key;
    private int adult_nbr;
    private int child_nbr;
    private int animal_nbr;
    private int vehicle_nbr;
    private Date from;
    private Date to;

    public Reservation() {
        super();
    }

    public Reservation(User user, Location location, String reservation_key, int adult_nbr, int child_nbr, int animal_nbr, int vehicle_nbr, Date from, Date to) {
        this.user = user;
        this.location = location;
        this.reservation_key = reservation_key;
        this.adult_nbr = adult_nbr;
        this.child_nbr = child_nbr;
        this.animal_nbr = animal_nbr;
        this.vehicle_nbr = vehicle_nbr;
        this.from = from;
        this.to = to;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
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
                '}';
    }
}
