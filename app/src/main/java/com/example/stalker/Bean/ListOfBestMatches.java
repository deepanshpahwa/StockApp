package com.example.stalker.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListOfBestMatches {


    @SerializedName("bestMatches")
    @Expose
    private List<BestMatch> bestMatches;

    public List<BestMatch> getBestMatches() {
        return bestMatches;
    }

    public void setBestMatches(List<BestMatch> bestMatches) {
        this.bestMatches = bestMatches;
    }



}
