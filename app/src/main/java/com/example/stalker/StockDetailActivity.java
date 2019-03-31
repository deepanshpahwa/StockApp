package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.stalker.APIs.AlphaVantageAPI;
import com.example.stalker.Bean.Example;
import com.example.stalker.Bean.TechnicalData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockDetailActivity extends AppCompatActivity {

    private String STOCKABBR, STOCKNAME;
    TextView stockNameTv,stockNameAbbrTv, indicator1Tv, indicator2Tv, indicator1ValueTv, indicator2ValueTv;


    public StockDetailActivity( String stockAbbr, String stockName ){
        this.STOCKABBR = stockAbbr;
        this.STOCKNAME = stockName;

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
        setContentView(R.layout.activity_stock_detail);
        Bundle bundle = getIntent().getExtras();

        Button button = findViewById(R.id.create_custom_indicator);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.StartActivity();
            }
        });

        if (bundle.getString("companyAbbr") != null){
            STOCKABBR =bundle.getString("companyAbbr");
            STOCKNAME =bundle.getString("companyName");
        }

        Utils.setToolbar(this);

        stockNameTv = findViewById(R.id.ASD_company_name);
        stockNameAbbrTv = findViewById(R.id.ASD_company_abbr);
        indicator1Tv = findViewById(R.id.ASD_indicator1);
        indicator2Tv = findViewById(R.id.ASD_indicator2);
        indicator1ValueTv = findViewById(R.id.ASD_indiacator1_value);
        indicator2ValueTv = findViewById(R.id.ASD_indicator2_value);

        stockNameTv.setText(STOCKNAME);
        stockNameAbbrTv.setText(STOCKABBR);

        getData(STOCKABBR,"daily", "open");


    }

    private void getData(String symbol, String interval, String series_type) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.AlphaVantageAPI_BaseURL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        AlphaVantageAPI alphaVantageAPI = retrofit.create(AlphaVantageAPI.class);

        Call<Example> call =  alphaVantageAPI.getMacdFromSearchQuery("MACD", symbol , interval, series_type, getString(R.string.Alpha_Vantage_API_key) );

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {

                    Utils.print("pulling MACD data");
                    TechnicalData data = response.body().getMap().get("2019-03-08");

                    indicator2Tv.setText("Bollinger Bands");

                    indicator1Tv.setText("MACD");
                    indicator2ValueTv.setText("NA");

                    TechnicalData macdValues = response.body().getMap().get("2019-03-05");
                    if (macdValues!=null) {
                        indicator1ValueTv.setText(macdValues.getMACD());
                    }else{
                        Utils.makeSnackBar(findViewById(R.id.coordinatorLayout),"There was an error getting information on this stock", Snackbar.LENGTH_LONG);
                    }

//                Utils.print(data.getMACD()+", "+data.getMACDSignal());
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Utils.print("failed to pull MACD data");
            }
        });
    }


}
