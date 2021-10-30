package com.example.parking.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingAreaResponse {
    private String message;
    private int twoWheelerCapacity;
    private int fourWheelerCapacity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTwoWheelerCapacity() {
        return twoWheelerCapacity;
    }

    public void setTwoWheelerCapacity(int twoWheelerCapacity) {
        this.twoWheelerCapacity = twoWheelerCapacity;
    }

    public int getFourWheelerCapacity() {
        return fourWheelerCapacity;
    }

    public void setFourWheelerCapacity(int fourWheelerCapacity) {
        this.fourWheelerCapacity = fourWheelerCapacity;
    }
}
