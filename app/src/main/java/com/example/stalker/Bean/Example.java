package com.example.stalker.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Example {

    @SerializedName("Meta Data")
    @Expose
    private MetaData metaData;
    @SerializedName("Technical Analysis: MACD")
    @Expose
    Map<String, TechnicalData> map = new HashMap<>();

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public void setMap(Map<String, TechnicalData> map) {
        this.map = map;
    }

    public Map<String, TechnicalData> getMap() {
        return map;
    }

}