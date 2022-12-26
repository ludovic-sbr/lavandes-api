package com.feliiks.gardons.entities;

public class LocationEntity {
    private Long id;
    private String name;
    private FileEntity image;
    private String stripeProductId;
    private String description;
    private Boolean parking;
    private Boolean kitchen;
    private Boolean wifi;
    private Boolean sanitary;
    private Boolean heater;
    private Boolean air_conditioner;
    private Boolean terrace;
    private Boolean barbecue;
    private int surface;
    private int max_persons;
    private int price_per_night;
    private int bedrooms;
    private boolean available;
    private int slot_number;

    public LocationEntity() {
    }

    public LocationEntity(
            String name,
            FileEntity image,
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
            boolean available,
            int slot_number) {
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
        this.available = available;
        this.slot_number = slot_number;
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

    public FileEntity getImage() {
        return image;
    }

    public void setImage(FileEntity image) {
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

    public Boolean getParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public Boolean getKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getSanitary() {
        return sanitary;
    }

    public void setSanitary(boolean sanitary) {
        this.sanitary = sanitary;
    }

    public Boolean getHeater() {
        return heater;
    }

    public void setHeater(boolean heater) {
        this.heater = heater;
    }

    public Boolean getAir_conditioner() {
        return air_conditioner;
    }

    public void setAir_conditioner(boolean air_conditioner) {
        this.air_conditioner = air_conditioner;
    }

    public Boolean getTerrace() {
        return terrace;
    }

    public void setTerrace(boolean terrace) {
        this.terrace = terrace;
    }

    public Boolean getBarbecue() {
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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getSlot_number() {
        return slot_number;
    }

    public void setSlot_number(int slot_number) {
        this.slot_number = slot_number;
    }
}
