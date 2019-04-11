package com.example.stalker.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Bollinger_Bean {

    @SerializedName("Meta Data")
    @Expose
    private MetaData metaData;
    @SerializedName("Technical Analysis: BBANDS")
    @Expose
    Map<String, BollingerTechnicalData> map = new HashMap<>();

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public void setMap(Map<String, BollingerTechnicalData> map) {
        this.map = map;
    }

    public Map<String, BollingerTechnicalData> getMap() {
        return map;
    }


}
