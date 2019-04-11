package com.example.stalker;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestValuFormatter extends ValueFormatter implements IAxisValueFormatter {

    private final SimpleDateFormat mDataFormat;
    private final Date mDate;
    private SimpleDateFormat mFormat;

    TestValuFormatter(){
        mFormat = new SimpleDateFormat("MMM dd");
        this.mDataFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        this.mDate = new Date();
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
//        return mFormat.format(value);
        Utils.print(new Date(Float.valueOf(value).longValue()).toString());
        return getHour((long)value);
//        return new Date(Float.valueOf(value).longValue()).toString();
    }

    private String getHour(long value) {
        try{
            mDate.setTime(value*1000);
            Utils.print("getHOUR");
            return mDataFormat.format(mDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

}
