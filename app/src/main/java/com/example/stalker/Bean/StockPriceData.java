package com.example.stalker.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockPriceData {

    @SerializedName("1. open")
    @Expose
    private String _1Open;
    @SerializedName("2. high")
    @Expose
    private String _2High;
    @SerializedName("3. low")
    @Expose
    private String _3Low;
    @SerializedName("4. close")
    @Expose
    private String _4Close;
    @SerializedName("5. volume")
    @Expose
    private String _5Volume;

    public String get1Open() {
        return _1Open;
    }

    public void set1Open(String _1Open) {
        this._1Open = _1Open;
    }

    public String get2High() {
        return _2High;
    }

    public void set2High(String _2High) {
        this._2High = _2High;
    }

    public String get3Low() {
        return _3Low;
    }

    public void set3Low(String _3Low) {
        this._3Low = _3Low;
    }

    public String get4Close() {
        return _4Close;
    }

    public void set4Close(String _4Close) {
        this._4Close = _4Close;
    }

    public String get5Volume() {
        return _5Volume;
    }

    public void set5Volume(String _5Volume) {
        this._5Volume = _5Volume;
    }
}
