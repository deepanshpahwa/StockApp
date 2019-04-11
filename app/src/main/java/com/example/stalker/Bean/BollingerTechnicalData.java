package com.example.stalker.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BollingerTechnicalData {

    @SerializedName("Real Lower Band")
    @Expose
    private String rlBand;
    @SerializedName("Real Upper Band")
    @Expose
    private String ruBand;
    @SerializedName("Real Middle Band")
    @Expose
    private String rmBand;

    public String getRlBand() {
        return rlBand;
    }

    public void setRlBand(String rlBand) {
        this.rlBand = rlBand;
    }

    public String getRuBand() {
        return ruBand;
    }

    public void setRuBand(String ruBand) {
        this.ruBand = ruBand;
    }

    public String getRmBand() {
        return rmBand;
    }

    public void setRmBand(String rmBand) {
        this.rmBand = rmBand;
    }

}
