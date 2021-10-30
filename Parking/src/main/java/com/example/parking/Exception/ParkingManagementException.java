package com.example.parking.Exception;

public class ParkingManagementException extends Exception {

    private String message;

    public ParkingManagementException(String message) {
        super(message);
        this.message = message;
    }
}
