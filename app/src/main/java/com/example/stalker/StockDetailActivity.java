package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class StockDetailActivity extends AppCompatActivity {

    private final String stockName;

    StockDetailActivity(Activity activity, String stockName ){
        Intent intent = new Intent(activity, this.getClass());
        activity.startActivity(intent);
        this.stockName = stockName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.setToolbar(this);

        TextView stockNameTv = findViewById(R.id.textView3);
        TextView stockNameAbbrTv = findViewById(R.id.textView2);
//        TextView stockNameTv = findViewById(R.id.);
//        TextView stockNameTv = findViewById(R.id.);


    }




}
