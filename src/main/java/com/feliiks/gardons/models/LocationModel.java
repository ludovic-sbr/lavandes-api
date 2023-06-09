package com.feliiks.gardons.models;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class LocationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "image_id")
    private FileModel image;
    private String stripeProductId;
    private String description;
    private boolean parking;
    private boolean kitchen;
    private boolean wifi;
    private boolean sanitary;
    private boolean heater;
    private boolean air_conditioner;
    private boolean terrace;
    private boolean barbecue;
    private int surface;
    private int max_persons;
    private int price_per_night;
    private int bedrooms;
    private int slot_remaining;

    public LocationModel() {
        super();
    }

    public LocationModel(
            String name,
            FileModel image,
            String stripeProductId,
            String description,
            boolean parking,
            boolean kitchen,
            boolean wifi,
            boolean sanitary,
            boolean heater,
            boolean air_conditioner,
            boolean terrace,
            boolean barbecue,
            int surface,
            int max_persons,
            int price_per_night,
            int bedrooms,
            int slot_remaining) {
        this.name = name;
        this.image = image;
        this.stripeProductId = stripeProductId;
        this.description = description;
        this.parking = parking;
        this.kitchen = kitchen;
        this.wifi = wifi;
        this.sanitary = sanitary;
        this.heater = heater;
        this.air_conditioner = air_conditioner;
        this.terrace = terrace;
        this.barbecue = barbecue;
        this.surface = surface;
        this.max_persons = max_persons;
        this.price_per_night = price_per_night;
        this.bedrooms = bedrooms;
        this.slot_remaining = slot_remaining;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileModel getImage() {
        return image;
    }

    public void setImage(FileModel image) {
        this.image = image;
    }

    public String getStripeProductId() {
        return stripeProductId;
    }

    public void setStripeProductId(String stripeProductId) {
        this.stripeProductId = stripeProductId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isSanitary() {
        return sanitary;
    }

    public void setSanitary(boolean sanitary) {
        this.sanitary = sanitary;
    }

    public boolean isHeater() {
        return heater;
    }

    public void setHeater(boolean heater) {
        this.heater = heater;
    }

    public boolean isAir_conditioner() {
        return air_conditioner;
    }

    public void setAir_conditioner(boolean air_conditioner) {
        this.air_conditioner = air_conditioner;
    }

    public boolean isTerrace() {
        return terrace;
    }

    public void setTerrace(boolean terrace) {
        this.terrace = terrace;
    }

    public boolean isBarbecue() {
        return barbecue;
    }

    public void setBarbecue(boolean barbecue) {
        this.barbecue = barbecue;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getMax_persons() {
        return max_persons;
    }

    public void setMax_persons(int max_persons) {
        this.max_persons = max_persons;
    }

    public int getPrice_per_night() {
        return price_per_night;
    }

    public void setPrice_per_night(int price_per_night) {
        this.price_per_night = price_per_night;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getSlot_remaining() {
        return slot_remaining;
    }

    public void setSlot_remaining(int slot_remaining) {
        this.slot_remaining = slot_remaining;
    }
}
