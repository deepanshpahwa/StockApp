package com.example.stalker.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MACD_TechnicalData {
    @SerializedName("MACD_Signal")
    @Expose
    private String mACDSignal;
    @SerializedName("MACD_Hist")
    @Expose
    private String mACDHist;
    @SerializedName("MACD")
    @Expose
    private String mACD;

    public String getMACDSignal() {
        return mACDSignal;
    }

    public void setMACDSignal(String mACDSignal) {
        this.mACDSignal = mACDSignal;
    }

    public String getMACDHist() {
        return mACDHist;
    }

    public void setMACDHist(String mACDHist) {
        this.mACDHist = mACDHist;
    }

    public String getMACD() {
        return mACD;
    }

    public void setMACD(String mACD) {
        this.mACD = mACD;
    }
}
