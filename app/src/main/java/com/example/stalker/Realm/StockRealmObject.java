package com.example.stalker.Realm;

import io.realm.RealmObject;

public class StockRealmObject extends RealmObject {

    public String getStockAbbr() {
        return stockAbbr;
    }

    public void setStockAbbr(String stockAbbr) {
        this.stockAbbr = stockAbbr;
    }

    private String stockAbbr;

}
