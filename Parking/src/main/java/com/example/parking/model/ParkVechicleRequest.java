package com.example.parking.model;

public class ParkVechicleRequest {

    private Vechicle vechicle;
    private String parkingName;

    public Vechicle getVechicle() {
        return vechicle;
    }

    public void setVechicle(Vechicle vechicle) {
        this.vechicle = vechicle;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }
}
