package com.example.parking.Exception;

import com.example.parking.controller.ParkingController;
import com.example.parking.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(assignableTypes = {ParkingController.class})
@Slf4j
public class ParkingManagementExceptionHandler {


    @ExceptionHandler({ParkingManagementException.class })
    public @ResponseBody ErrorResponse handleParkingManagementException(ParkingManagementException ex) {
        log.error("Exception occurred because of :: {} ",ex.getMessage());
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }
}
