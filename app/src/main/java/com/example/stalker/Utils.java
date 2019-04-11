package com.example.stalker;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String MACD = "MACD";
    public static String BOLL_UPPER = "Bollinger Bands(Upper)";
    public static String BOLL_MIDDLE = "Bollinger Bands(Middle)";
    public static String BOLL_LOWER = "Bollinger Bands(Lower)";
    public static String FUNCTION_ADD = "+";
    public static String FUCNTION_SUBTRACT = "-";
    public static String FUCNTION_MULTIPLY = "x";
    public static String FUCNTION_DIVIDE = "/";


    //toolbar
    public static void print(String s){
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

    public static void StartActivity() {

    }

    public static String getLatestBusinessDayDate() {//TODO get ACTUAL current date
        Calendar calendar = Calendar.getInstance(Locale.US);
        Utils.print("asdfg"+String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
        if (calendar.get(Calendar.DAY_OF_WEEK) == 7){
            calendar.add(Calendar.DATE, -1);
        }else if (calendar.get(Calendar.DAY_OF_WEEK) == 1){
            calendar.add(Calendar.DATE, -2);
        }
        calendar.add(Calendar.DATE,-1);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String ss = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
//
//        return ss;

        return dateFormat.format(calendar.getTime());

    }

    public static Calendar getPreviousDate(int days) {
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.add(Calendar.DATE,-days);
        return calendar;

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        return dateFormat.format(calendar.getTime());
    }

    public static Date parseDateForChart(String time) {
        String inputPattern = "yyyy-MM-dd";
//        String outputPattern = "dd/MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
//        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
//        String str = null;

        try {
            date = inputFormat.parse(time);
//            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
