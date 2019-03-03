package com.example.stalker.Bean;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmObjectListOfFavStocks extends RealmObject {
    RealmList<String> list = new RealmList<String>();

    public RealmList<String> getList() {
        return list;
    }

    public void setList(RealmList<String> list) {
        this.list = list;
    }


}
