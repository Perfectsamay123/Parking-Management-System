package com.example.parking.model;

public class CalculateAmountResponse {
    private String vechicleNumber;
    private String timeIn;
    private String timeOut;
    private int amount;

    public String getVechicleNumber() {
        return vechicleNumber;
    }

    public void setVechicleNumber(String vechicleNumber) {
        this.vechicleNumber = vechicleNumber;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
