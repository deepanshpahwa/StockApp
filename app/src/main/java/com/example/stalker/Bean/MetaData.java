package com.example.stalker.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaData {


    @SerializedName("1: Symbol")
    @Expose
    private String _1Symbol;
    @SerializedName("2: Indicator")
    @Expose
    private String _2Indicator;
    @SerializedName("3: Last Refreshed")
    @Expose
    private String _3LastRefreshed;
    @SerializedName("4: Interval")
    @Expose
    private String _4Interval;
    @SerializedName("5.1: Fast Period")
    @Expose
    private Integer _51FastPeriod;
    @SerializedName("5.2: Slow Period")
    @Expose
    private Integer _52SlowPeriod;
    @SerializedName("5.3: Signal Period")
    @Expose
    private Integer _53SignalPeriod;
    @SerializedName("6: Series Type")
    @Expose
    private String _6SeriesType;
    @SerializedName("7: Time Zone")
    @Expose
    private String _7TimeZone;

    public String get1Symbol() {
        return _1Symbol;
    }

    public void set1Symbol(String _1Symbol) {
        this._1Symbol = _1Symbol;
    }

    public String get2Indicator() {
        return _2Indicator;
    }

    public void set2Indicator(String _2Indicator) {
        this._2Indicator = _2Indicator;
    }

    public String get3LastRefreshed() {
        return _3LastRefreshed;
    }

    public void set3LastRefreshed(String _3LastRefreshed) {
        this._3LastRefreshed = _3LastRefreshed;
    }

    public String get4Interval() {
        return _4Interval;
    }

    public void set4Interval(String _4Interval) {
        this._4Interval = _4Interval;
    }

    public Integer get51FastPeriod() {
        return _51FastPeriod;
    }

    public void set51FastPeriod(Integer _51FastPeriod) {
        this._51FastPeriod = _51FastPeriod;
    }

    public Integer get52SlowPeriod() {
        return _52SlowPeriod;
    }

    public void set52SlowPeriod(Integer _52SlowPeriod) {
        this._52SlowPeriod = _52SlowPeriod;
    }

    public Integer get53SignalPeriod() {
        return _53SignalPeriod;
    }

    public void set53SignalPeriod(Integer _53SignalPeriod) {
        this._53SignalPeriod = _53SignalPeriod;
    }

    public String get6SeriesType() {
        return _6SeriesType;
    }

    public void set6SeriesType(String _6SeriesType) {
        this._6SeriesType = _6SeriesType;
    }

    public String get7TimeZone() {
        return _7TimeZone;
    }

    public void set7TimeZone(String _7TimeZone) {
        this._7TimeZone = _7TimeZone;
    }


}
