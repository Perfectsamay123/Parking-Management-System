package com.example.parking.service;

import com.example.parking.Exception.ParkingManagementException;
import com.example.parking.model.*;
import org.springframework.stereotype.Service;


@Service
public interface ParkingManagementService {
     ParkingAreaResponse registerNewParkingArea(ParkingAreaRequest parkingAreaRequest) throws ParkingManagementException;
     ParkVechicleResponse parkVechicle(ParkVechicleRequest parkVechicleRequest) throws ParkingManagementException;
     CalculateAmountResponse calculateAmount(CalculateAmountRequest calculateAmountRequest) throws  ParkingManagementException;
     MaintainHistoryResponse  maintainsHistory(MaintainHistoryRequest maintainHistoryRequest) throws ParkingManagementException;
}
