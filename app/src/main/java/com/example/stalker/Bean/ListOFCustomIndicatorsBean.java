package com.example.stalker.Bean;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class ListOFCustomIndicatorsBean extends RealmObject {
    public RealmList<CustomIndicatorBeanRealm> getList() {
        return list;
    }

    public void setList(RealmList<CustomIndicatorBeanRealm> list) {
        this.list = list;
    }

    RealmList<CustomIndicatorBeanRealm> list = new RealmList<>();

}
