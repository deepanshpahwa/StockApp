package com.example.stalker.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Symbol {
    @SerializedName("quote")
    @Expose
    private Quote quote;
}
class Quote{
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("primaryExchange")
    @Expose
    private String primaryExchange;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("calculationPrice")
    @Expose
    private String calculationPrice;
    @SerializedName("open")
    @Expose
    private Double open;
    @SerializedName("openTime")
    @Expose
    private Long openTime;
    @SerializedName("close")
    @Expose
    private Double close;
    @SerializedName("closeTime")
    @Expose
    private Long closeTime;
    @SerializedName("high")
    @Expose
    private Double high;
    @SerializedName("low")
    @Expose
    private Double low;
    @SerializedName("latestPrice")
    @Expose
    private Double latestPrice;
    @SerializedName("latestSource")
    @Expose
    private String latestSource;
    @SerializedName("latestTime")
    @Expose
    private String latestTime;
    @SerializedName("latestUpdate")
    @Expose
    private Long latestUpdate;
    @SerializedName("latestVolume")
    @Expose
    private Long latestVolume;
    @SerializedName("iexRealtimePrice")
    @Expose
    private Double iexRealtimePrice;
    @SerializedName("iexRealtimeSize")
    @Expose
    private Long iexRealtimeSize;
    @SerializedName("iexLastUpdated")
    @Expose
    private Long iexLastUpdated;
    @SerializedName("delayedPrice")
    @Expose
    private Double delayedPrice;
    @SerializedName("delayedPriceTime")
    @Expose
    private Long delayedPriceTime;
    @SerializedName("extendedPrice")
    @Expose
    private Double extendedPrice;
    @SerializedName("extendedChange")
    @Expose
    private Double extendedChange;
    @SerializedName("extendedChangePercent")
    @Expose
    private Double extendedChangePercent;
    @SerializedName("extendedPriceTime")
    @Expose
    private Long extendedPriceTime;
    @SerializedName("previousClose")
    @Expose
    private Double previousClose;
    @SerializedName("change")
    @Expose
    private Double change;
    @SerializedName("changePercent")
    @Expose
    private Double changePercent;
    @SerializedName("iexMarketPercent")
    @Expose
    private Double iexMarketPercent;
    @SerializedName("iexVolume")
    @Expose
    private Long iexVolume;
    @SerializedName("avgTotalVolume")
    @Expose
    private Long avgTotalVolume;
    @SerializedName("iexBidPrice")
    @Expose
    private Long iexBidPrice;
    @SerializedName("iexBidSize")
    @Expose
    private Long iexBidSize;
    @SerializedName("iexAskPrice")
    @Expose
    private Long iexAskPrice;
    @SerializedName("iexAskSize")
    @Expose
    private Long iexAskSize;
    @SerializedName("marketCap")
    @Expose
    private Long marketCap;
    @SerializedName("peRatio")
    @Expose
    private Double peRatio;
    @SerializedName("week52High")
    @Expose
    private Double week52High;
    @SerializedName("week52Low")
    @Expose
    private Double week52Low;
    @SerializedName("ytdChange")
    @Expose
    private Double ytdChange;
}
