package com.example.parking.model;
import java.util.LinkedHashMap;

public class ParkingAreaRequest {
   private String parkingName;
   private int twoWheelerCapacity;
   private int fourWheelerCapacity;
   private LinkedHashMap<Object,Integer> slotPrice;


    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
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

    public LinkedHashMap<Object, Integer> getSlotPrice() {
        return slotPrice;
    }

    public void setSlotPrice(LinkedHashMap<Object, Integer> slotPrice) {
        this.slotPrice = slotPrice;
    }
}
