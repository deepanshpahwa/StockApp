package com.example.stalker.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BestMatch {
    @SerializedName("1. symbol")
    @Expose
    private String _1Symbol;
    @SerializedName("2. name")
    @Expose
    private String _2Name;
    @SerializedName("3. type")
    @Expose
    private String _3Type;
    @SerializedName("4. region")
    @Expose
    private String _4Region;
    @SerializedName("5. marketOpen")
    @Expose
    private String _5MarketOpen;
    @SerializedName("6. marketClose")
    @Expose
    private String _6MarketClose;
    @SerializedName("7. timezone")
    @Expose
    private String _7Timezone;
    @SerializedName("8. currency")
    @Expose
    private String _8Currency;
    @SerializedName("9. matchScore")
    @Expose
    private String _9MatchScore;

    public String get1Symbol() {
        return _1Symbol;
    }

    public void set1Symbol(String _1Symbol) {
        this._1Symbol = _1Symbol;
    }

    public String get2Name() {
        return _2Name;
    }

    public void set2Name(String _2Name) {
        this._2Name = _2Name;
    }

    public String get3Type() {
        return _3Type;
    }

    public void set3Type(String _3Type) {
        this._3Type = _3Type;
    }

    public String get4Region() {
        return _4Region;
    }

    public void set4Region(String _4Region) {
        this._4Region = _4Region;
    }

    public String get5MarketOpen() {
        return _5MarketOpen;
    }

    public void set5MarketOpen(String _5MarketOpen) {
        this._5MarketOpen = _5MarketOpen;
    }

    public String get6MarketClose() {
        return _6MarketClose;
    }

    public void set6MarketClose(String _6MarketClose) {
        this._6MarketClose = _6MarketClose;
    }

    public String get7Timezone() {
        return _7Timezone;
    }

    public void set7Timezone(String _7Timezone) {
        this._7Timezone = _7Timezone;
    }

    public String get8Currency() {
        return _8Currency;
    }

    public void set8Currency(String _8Currency) {
        this._8Currency = _8Currency;
    }

    public String get9MatchScore() {
        return _9MatchScore;
    }

    public void set9MatchScore(String _9MatchScore) {
        this._9MatchScore = _9MatchScore;
    }
}
