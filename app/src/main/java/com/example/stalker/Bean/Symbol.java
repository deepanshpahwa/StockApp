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
    private Double openTime;
    @SerializedName("close")
    @Expose
    private Double close;
    @SerializedName("closeTime")
    @Expose
    private Double closeTime;
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
    private Double latestUpdate;
    @SerializedName("latestVolume")
    @Expose
    private Double latestVolume;
    @SerializedName("iexRealtimePrice")
    @Expose
    private Double iexRealtimePrice;
    @SerializedName("iexRealtimeSize")
    @Expose
    private Double iexRealtimeSize;
    @SerializedName("iexLastUpdated")
    @Expose
    private Double iexLastUpdated;
    @SerializedName("delayedPrice")
    @Expose
    private Double delayedPrice;
    @SerializedName("delayedPriceTime")
    @Expose
    private Double delayedPriceTime;
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
    private Double extendedPriceTime;
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
    private Double iexVolume;
    @SerializedName("avgTotalVolume")
    @Expose
    private Double avgTotalVolume;
    @SerializedName("iexBidPrice")
    @Expose
    private Double iexBidPrice;
    @SerializedName("iexBidSize")
    @Expose
    private Double iexBidSize;
    @SerializedName("iexAskPrice")
    @Expose
    private Double iexAskPrice;
    @SerializedName("iexAskSize")
    @Expose
    private Double iexAskSize;
    @SerializedName("marketCap")
    @Expose
    private Double marketCap;
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
