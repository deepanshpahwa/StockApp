package com.example.stalker.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class StockPriceBean {

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Map<String, StockPriceData> getMap() {
        return map;
    }

    public void setMap(Map<String, StockPriceData> map) {
        this.map = map;
    }

    @SerializedName("Meta Data")
    @Expose
    private MetaData metaData;
    @SerializedName("Time Series (Daily)")
    @Expose
    Map<String, StockPriceData> map = new HashMap<>();




}
