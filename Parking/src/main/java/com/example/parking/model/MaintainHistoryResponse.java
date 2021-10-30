package com.example.parking.model;

import java.util.ArrayList;

public class MaintainHistoryResponse {

    private ArrayList<HistoryResponse>historyResponses;

    public ArrayList<HistoryResponse> getHistoryResponses() {
        return historyResponses;
    }

    public void setHistoryResponses(ArrayList<HistoryResponse> historyResponses) {
        this.historyResponses = historyResponses;
    }
}
