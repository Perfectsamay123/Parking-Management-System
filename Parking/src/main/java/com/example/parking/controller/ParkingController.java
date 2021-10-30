package com.example.parking.controller;

import com.example.parking.Exception.ParkingManagementException;
import com.example.parking.model.*;
import com.example.parking.service.ParkingManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ParkingController {

    @Autowired
    ParkingManagementService parkingManagementService;

    @PostMapping(value ="/newRegistration", consumes="application/json",produces="application/json")
    public @ResponseBody
    ParkingAreaResponse newParkingRegistration(@RequestBody ParkingAreaRequest parkingAreaRequest) throws ParkingManagementException {
        log.info("Registering new Parking Area  is in progress");
        return parkingManagementService.registerNewParkingArea(parkingAreaRequest);
    }

    @PostMapping(value ="/parkVechicle", consumes="application/json",produces="application/json")
    public @ResponseBody
    ParkVechicleResponse parkVechicle(@RequestBody ParkVechicleRequest parkVechicleRequest) throws ParkingManagementException {
        log.info("Parking vechicle in parking Area :: " + parkVechicleRequest.getParkingName() + " is in progress");
        return parkingManagementService.parkVechicle(parkVechicleRequest);
    }

    @PostMapping(value ="/calculateAmount", consumes="application/json",produces="application/json")
    public @ResponseBody CalculateAmountResponse  calculateAmount(@RequestBody CalculateAmountRequest calculateAmountRequest) throws ParkingManagementException {
        log.info("Calculating amount for  vechicle  parked in :: " + calculateAmountRequest.getParkingName() + " in progress");
        return parkingManagementService.calculateAmount(calculateAmountRequest);
    }

    @PostMapping(value ="/maintainsHistory", consumes="application/json",produces="application/json")
    public @ResponseBody MaintainHistoryResponse  maintainHistory(@RequestBody MaintainHistoryRequest maintainHistoryRequest) throws ParkingManagementException {
        log.info("Displaying records of vechicle :: " + maintainHistoryRequest.getVechicleNumber() + " in progress");
        return parkingManagementService.maintainsHistory(maintainHistoryRequest);
    }


}
