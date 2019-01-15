package com.glovoapp.backender.web.rest.vm;

/**
 * To be used for exposing order information through the API
 */
public class OrderVM {
    private String id;
    private String description;
    private double distance;
    private boolean isFood;
    private boolean vip;

    public OrderVM(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public OrderVM(String id, String description, double distance, boolean isFood, boolean vip) {
        this.id = id;
        this.description = description;
        this.distance = distance;
        this.isFood = isFood;
        this.vip = vip;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isFood() {
        return isFood;
    }

    public boolean isVip() {
        return vip;
    }
}
