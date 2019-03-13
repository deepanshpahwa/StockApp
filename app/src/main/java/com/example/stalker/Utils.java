package com.example.stalker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.AlteredCharSequence;
import android.view.View;
import android.widget.Toast;

public class Utils {


    //toolbar
    static void print(String s){
        System.out.println(":::::::::::"+s);
    }

    static void setToolbar(AppCompatActivity activity) {
        activity.setContentView(R.layout.activity_stock_detail);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#A9A9A9"));
        activity.setSupportActionBar(toolbar);
    }

    public static void makeToast(Context context, String new_favorite_added) {
        Toast.makeText(context,new_favorite_added,Toast.LENGTH_SHORT).show();
    }
    public static void makeSnackBar(View contextView, String string, int length){
        Snackbar.make(contextView,string,length)
                .show();
    }
}
