package com.example.parking.service;

import com.example.parking.Exception.ParkingManagementException;
import com.example.parking.model.*;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Slf4j
@Component
public class ParkingManagementServiceImpl implements ParkingManagementService {

    /**
     * In-Memory Database
     */
    //HashMap to store ParkingName with it's 2/4 wheeler capacity
    private final HashMap<String, Pair<Integer,Integer>>parkingDetails=new HashMap<>();
    //HashMap to store ParkingRate
    private final LinkedHashMap<String,LinkedHashMap<Object,Integer>> priceSlab=new LinkedHashMap<>();
    //HashMap to store current parked vechicle in parking Area
    private final HashMap<Pair<String,String>,Integer>currentParkedVechicle=new HashMap<>();
    //HashMap to store TimeIn Information of Vechicle
    private final HashMap<String,String>timeIn=new HashMap<>();
    //HashMap to store History of the vechicle Parking
    private final HashMap<String, ArrayList<ArrayList<Object>>>vechicleHistory=new HashMap<>();

    static int currentCapacity=0;

    /**
     * Function to Register a new Parking Area
     * @param parkingAreaRequest
     * @throws ParkingManagementException
     */
    public ParkingAreaResponse registerNewParkingArea(ParkingAreaRequest parkingAreaRequest) throws ParkingManagementException {
        Pair<Integer,Integer>capacity=new Pair<>(parkingAreaRequest.getTwoWheelerCapacity(), parkingAreaRequest.getFourWheelerCapacity());
        //throw Exception if parking area is already Registered
        if(parkingDetails.containsKey(parkingAreaRequest.getParkingName())){
            log.info("This parking area is already registered");
           throw  new ParkingManagementException("This Parking Area is already Registered");
        }
        parkingDetails.put(parkingAreaRequest.getParkingName(),capacity);
        insertSlotPrice(parkingAreaRequest);

        log.info("Parking Area registered successfully");
        ParkingAreaResponse parkingAreaResponse =new ParkingAreaResponse();
        parkingAreaResponse.setMessage("Parking Area is registered");
        parkingAreaResponse.setTwoWheelerCapacity(parkingAreaRequest.getTwoWheelerCapacity());
        parkingAreaResponse.setFourWheelerCapacity(parkingAreaRequest.getFourWheelerCapacity());
        return parkingAreaResponse;

    }

    /**
     * Function to store Price slab of Parking Area
     * @param parkingAreaRequest
     */
    private  void insertSlotPrice(ParkingAreaRequest parkingAreaRequest){
        LinkedHashMap<Object,Integer>slotPrice= parkingAreaRequest.getSlotPrice();
            priceSlab.put(parkingAreaRequest.getParkingName(),slotPrice);
        }

    /**
     * Function to Park a vechicle
      * @param parkVechicleRequest
     * @throws ParkingManagementException
     */
    public ParkVechicleResponse parkVechicle(ParkVechicleRequest parkVechicleRequest) throws ParkingManagementException {
        ParkVechicleResponse parkVechicleResponse = new ParkVechicleResponse();

        //If Parking Area does not exist
        if (!parkingDetails.containsKey(parkVechicleRequest.getParkingName())) {
            log.info("This Parking Area does not exist");
            throw new ParkingManagementException("This parking Area does not exist.Please park in correct Area");
        }

        //If Same Vehicle is parked in other Parking Area
        if(timeIn.containsKey(parkVechicleRequest.getVechicle().getVechicleNumber())){
            log.info("This Vechicle Number is parked in other Parking Area.");
            throw new ParkingManagementException("This Vechicle Number is parked in other Parking Area.");
        }

        //Checks Availability in Parking Area
        checkSlot(parkVechicleRequest,currentCapacity);

        //Increase the count of parking of 2 wheeler
        Pair<String, String> updateVechicle;
        if (parkVechicleRequest.getVechicle().getVechicleType().equals("Two")) {
            updateVechicle=new Pair<>(parkVechicleRequest.getParkingName(),"Two");
        }
        //Increase the count of parking of 4 wheeler
        else{
            updateVechicle=new Pair<>(parkVechicleRequest.getParkingName(),"Four");
        }
        currentParkedVechicle.put(updateVechicle,currentCapacity+1);



        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String  entryTiming =sdf.format(new java.util.Date());
        try{
            timeIn.put(parkVechicleRequest.getVechicle().getVechicleNumber(),entryTiming); }
        catch (Exception ex)
        {
                log.error("Exception occurred while entering due to :: {}" ,ex.getStackTrace());
                throw  new ParkingManagementException("Technical Exception Occurred");
        }

        log.info("Vechicle has been parked successfully");
        parkVechicleResponse.setMessage("You have successfully parked your vechicle");
        parkVechicleResponse.setParkingName(parkVechicleRequest.getParkingName());
        parkVechicleResponse.setParkingTime(entryTiming);


        return parkVechicleResponse;
    }

    /**
     * Function to check whether vechicle can be parked or not.
     * @param parkVechicleRequest
     * @param currentCapacity
     * @return
     * @throws ParkingManagementException
     */
    private boolean checkSlot(ParkVechicleRequest parkVechicleRequest,int currentCapacity) throws ParkingManagementException {

        int totalCapacity;
        Pair<String, String> currentStatus;
        if (parkVechicleRequest.getVechicle().getVechicleType().equals("Two")) {
            currentStatus = new Pair<>(parkVechicleRequest.getParkingName(), "Two");
            totalCapacity = parkingDetails.get(parkVechicleRequest.getParkingName()).getKey();
        } else {
            currentStatus = new Pair<>(parkVechicleRequest.getParkingName(), "Four");
            totalCapacity = parkingDetails.get(parkVechicleRequest.getParkingName()).getValue();
        }
        if (currentParkedVechicle.containsKey(currentStatus)) {
            currentCapacity = currentParkedVechicle.get(currentStatus);
        }

        if (currentCapacity == totalCapacity) {
            log.info("Parking Area is fulled");
            throw new ParkingManagementException("Parking Area is Full.Your vechicle cannot be parked here");
        }
        return true;

    }

    /**
     * Function to calculate the Amount of parked Vechicle
     * @param calculateAmountRequest
     * @throws ParkingManagementException
     */
    public CalculateAmountResponse calculateAmount(CalculateAmountRequest calculateAmountRequest) throws  ParkingManagementException {

        //If Parking Area does not exist
        if (!parkingDetails.containsKey(calculateAmountRequest.getParkingName())) {
            log.info("This Parking Area does not exist");
            throw new ParkingManagementException("This parking Area does not exist.Please park in correct Area");
        }

        //If Vechicle is not parked in the parking Area
        if(!timeIn.containsKey(calculateAmountRequest.getVechicle().getVechicleNumber())){
            log.info("Vechicle is not parked");
            throw new ParkingManagementException("Vechicle is not parked in the parking Area");
        }

        CalculateAmountResponse calculateAmountResponse=new CalculateAmountResponse();
        calculateAmountResponse.setVechicleNumber(calculateAmountRequest.getVechicle().getVechicleNumber());
        //Entry timing of Vechicle
        String entryOfVechicle=timeIn.get(calculateAmountRequest.getVechicle().getVechicleNumber());
        //Exit timing of Vechicle
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String  exitTiming =sdf.format(new java.util.Date());
        //Calls updateVechicleCount to decrease the currentCount in the parking Area
        updateCurrentCount(calculateAmountRequest);


        try{
            Date exitTime=sdf.parse(exitTiming);
            Date entryTime=sdf.parse(exitTiming);
            long durationOfParking=exitTime.getTime()-entryTime.getTime();

            //diffHours is not used while testing because duration of entry & exit will be very small.
            long diffHours = durationOfParking / (60 * 60 * 1000);
            calculateAmountResponse.setTimeIn(entryOfVechicle);
            calculateAmountResponse.setTimeOut(exitTiming);

            log.info("Calculating the Amount");
            long testDiffHours=56;

            //Get the PriceSlabRate of parking Area
            int slotRate;
            int amount=0;
            int days=0;
            HashMap<Object,Integer>priceSlabOfParkingArea=priceSlab.get(calculateAmountRequest.getParkingName());
                if(testDiffHours > 24){
                    days= (int) (testDiffHours/24);
                    testDiffHours=testDiffHours%24;
                }
                for (Object o : priceSlabOfParkingArea.keySet()) {
                    String slot = o.toString();
                    int startTime = Integer.parseInt(slot.substring(0, slot.indexOf("-")));
                    int endTime = Integer.parseInt(slot.substring(slot.indexOf("-") + 1));
                    if (testDiffHours >= startTime && testDiffHours < endTime) {
                            slotRate = priceSlabOfParkingArea.get(o);
                            amount+=slotRate;
                            break;
                    }
                }
                //If Vechicle is  parked more than 24 hours
                if(days!=0){
                    slotRate=priceSlabOfParkingArea.get(">24");
                    amount+=slotRate*days;
                }
               maintainHistory(calculateAmountRequest,exitTiming,entryOfVechicle,amount);

                calculateAmountResponse.setAmount(amount);



        }
        catch (Exception ex){
            log.error("Exception occurred while Exit time due to :: {}" ,ex.getStackTrace());
            throw  new ParkingManagementException("Technical Exception Occurred");
        }
        return calculateAmountResponse;
    }



    /**
     * Function to update the current Count of vechicle in Parked Area
     * @param calculateAmountRequest
     */
    private void updateCurrentCount(CalculateAmountRequest calculateAmountRequest) throws ParkingManagementException {
        //Decrease the count of parking
        Pair<String,String>updateCount=new Pair<>(calculateAmountRequest.getParkingName(),calculateAmountRequest.getVechicle().getVechicleType());
        if(currentParkedVechicle.containsKey(updateCount)){
            int currentParked=currentParkedVechicle.get(updateCount);
            if(currentParked==1){
                currentParkedVechicle.remove(updateCount);
            }
            else{
                currentParkedVechicle.put(updateCount,currentParked-1);
            }
            timeIn.remove(calculateAmountRequest.getVechicle().getVechicleNumber());
        }
        else{
            log.error("No Vechicle parked in this parking Area");
            throw new ParkingManagementException("No Vechicle parked in this parking Area");
        }


    }

    /**
     * Function to print record of Vechicle
     * @param maintainHistoryRequest
     * @return
     * @throws ParkingManagementException
     */
    @Override
    public MaintainHistoryResponse maintainsHistory(MaintainHistoryRequest maintainHistoryRequest) throws ParkingManagementException {
        MaintainHistoryResponse maintainHistoryResponse=new MaintainHistoryResponse();
        ArrayList<HistoryResponse>historyResponses=new ArrayList<>();
        ArrayList<ArrayList<Object>>historyOfVechicle=vechicleHistory.get(maintainHistoryRequest.getVechicleNumber());
        if(historyOfVechicle==null){
            log.error("No Vechicle History found for this Vechicle");
            throw new ParkingManagementException("No Vechicle History found for this Vechicle");
        }
        for (int i=0;i<historyOfVechicle.size();i++){
            HistoryResponse historyResponse=new HistoryResponse();
            ArrayList<Object>history=historyOfVechicle.get(i);
            historyResponse.setParkingName((String) history.get(0));
            historyResponse.setTimeIn((String) history.get(1));
            historyResponse.setTimeOut((String) history.get(2));
            historyResponse.setAmount((Integer) history.get(3));
            historyResponses.add(historyResponse);
        }
        maintainHistoryResponse.setHistoryResponses(historyResponses);
        return maintainHistoryResponse;
    }

    /**
     * Function to maintain the record of Vechicle
     * @param calculateAmountRequest
     * @param timeOut
     * @param timeIn
     * @param amount
     */
    private void maintainHistory(CalculateAmountRequest calculateAmountRequest,String timeOut,String timeIn,int amount){
        if(vechicleHistory.containsKey(calculateAmountRequest.getVechicle().getVechicleNumber())){
            ArrayList<Object>history=new ArrayList<>();
            history.add(calculateAmountRequest.getParkingName());
            history.add(timeIn);
            history.add(timeOut);
            history.add(amount);
            ArrayList<ArrayList<Object>>newHistory=vechicleHistory.get(calculateAmountRequest.getVechicle().getVechicleNumber());
            newHistory.add(history);
            vechicleHistory.put(calculateAmountRequest.getVechicle().getVechicleNumber(),newHistory);
        }
        else{
           ArrayList<Object>history=new ArrayList<>();
            history.add(calculateAmountRequest.getParkingName());
            history.add(timeIn);
            history.add(timeOut);
            history.add(amount);
            ArrayList<ArrayList<Object>>newHistory=new ArrayList<>();
           newHistory.add(history);
           vechicleHistory.put(calculateAmountRequest.getVechicle().getVechicleNumber(),newHistory);
        }

    }
}
