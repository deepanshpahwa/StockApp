package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class StockDetailActivity extends AppCompatActivity {

    private String stockName;


    public StockDetailActivity( String stockName ){
        this.stockName = stockName;
    }
    public StockDetailActivity(  ){
    }

    public Intent getIntent(Activity activity){
        Intent intent = new Intent(activity, this.getClass());
        return intent;
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
