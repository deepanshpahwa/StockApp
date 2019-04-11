package com.example.stalker.Bean;

import java.util.HashMap;
import java.util.Map;

public class TechnicalAnalysisMACD {
    Map<String, MACD_TechnicalData> map = new HashMap<>();

    public void setMap(Map<String, MACD_TechnicalData> map) {
        this.map = map;
    }

    public Map<String, MACD_TechnicalData> getMap() {
        return map;
    }


}
