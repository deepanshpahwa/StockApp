package com.example.stalker;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Utils {

    //toolbar
    static void print(String s){
        System.out.println(":::::::::::"+s);
    }

    static void setToolbar(AppCompatActivity activity) {
        activity.setContentView(R.layout.activity_stock_detail);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
    }

}
