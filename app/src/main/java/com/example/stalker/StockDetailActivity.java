package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.stalker.APIs.AlphaVantageAPI;
import com.example.stalker.Bean.BollingerTechnicalData;
import com.example.stalker.Bean.Bollinger_Bean;
import com.example.stalker.Bean.MACD_Bean;
import com.example.stalker.Bean.MACD_TechnicalData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockDetailActivity extends AppCompatActivity {

    private String STOCKABBR, STOCKNAME;
    TextView stockNameTv,stockNameAbbrTv, indicator1Tv, indicator2Tv, macdValue1Tv, bollingerValue1Tv, bollingerValue2Tv, bollingerValue3Tv;
    Button button;


    public StockDetailActivity(String stockAbbr, String stockName){
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

        if (bundle.getString("companyAbbr") != null){
            STOCKABBR =bundle.getString("companyAbbr");
            STOCKNAME =bundle.getString("companyName");
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.ASD_my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
//        Utils.setToolbar(this);

        stockNameTv = findViewById(R.id.ASD_company_name);
        stockNameAbbrTv = findViewById(R.id.ASD_company_abbr);
        indicator1Tv = findViewById(R.id.ASD_indicator1);
        indicator2Tv = findViewById(R.id.ASD_indicator2);
        macdValue1Tv = findViewById(R.id.ASD_indicator2_value);
        bollingerValue1Tv = findViewById(R.id.ASD_bollinger_value_1);
        bollingerValue2Tv = findViewById(R.id.ASD_bollinger_value_2);
        bollingerValue3Tv = findViewById(R.id.ASD_bollinger_value_3);
        button = findViewById(R.id.create_custom_indicator);

        stockNameTv.setText(STOCKNAME);
        stockNameAbbrTv.setText(STOCKABBR);
        indicator1Tv.setText("Bollinger Bands");
        indicator2Tv.setText("MACD");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.AlphaVantageAPI_BaseURL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        AlphaVantageAPI alphaVantageAPI = retrofit.create(AlphaVantageAPI.class);
        final String strDate = Utils.getLatestBusinessDayDate();
        Utils.print(strDate);

        getMACDData(strDate, alphaVantageAPI, STOCKABBR,getString(R.string.stock_interval), "open", "200");
        getBollingerData(strDate, alphaVantageAPI, STOCKABBR,getString(R.string.stock_interval), "open", "200");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomIndicatorActivity customIndicatorActivity = new CustomIndicatorActivity();
                Intent intent= customIndicatorActivity.getIntent(StockDetailActivity.this);
                intent.putExtra("companyAbbr",STOCKABBR);
                intent.putExtra("companyName",STOCKNAME);
                StockDetailActivity.this.startActivity(intent);
            }
        });

    }

    private void getMACDData(final String strDate, AlphaVantageAPI alphaVantageAPI, String symbol, String interval, String series_type, String time_period) {

        Call<MACD_Bean> MACD_call =  alphaVantageAPI.getMacdFromSearchQuery("MACD", symbol , interval, series_type, getString(R.string.Alpha_Vantage_API_key) );
        MACD_call.enqueue(new Callback<MACD_Bean>() {
            @Override
            public void onResponse(Call<MACD_Bean> call, Response<MACD_Bean> response) {
                if (response.isSuccessful()) {

                    Utils.print("pulling MACD data");
                    MACD_TechnicalData macdValues = response.body().getMap().get(strDate);

                    try{
                        macdValue1Tv.setText(macdValues.getMACD());
                    }catch (Exception e){
                        macdValue1Tv.setText("NA");
                        Log.e("MACD on rsponse",e.getMessage());
                        Utils.makeSnackBar(findViewById(R.id.coordinatorLayout),"There was an error getting information on this stock", Snackbar.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MACD_Bean> call, Throwable t) {
                Utils.print("failed to pull data for this stock");
            }
        });
    }

    private void getBollingerData(final String strDate, AlphaVantageAPI alphaVantageAPI, String symbol, String interval, String series_type, String time_period) {

        Call<Bollinger_Bean> bollinger_call = alphaVantageAPI.getBollingerFromSearchQuery("BBANDS", symbol, interval, time_period, series_type, getString(R.string.Alpha_Vantage_API_key));
        bollinger_call.enqueue(new Callback<Bollinger_Bean>() {
            @Override
            public void onResponse(Call<Bollinger_Bean> call, Response<Bollinger_Bean> response) {
                if (response.isSuccessful()){
                    BollingerTechnicalData data = response.body().getMap().get(strDate);
                    try {
                        bollingerValue1Tv.setText(data.getRlBand());
                        bollingerValue2Tv.setText(data.getRuBand());
                        bollingerValue3Tv.setText(data.getRmBand());
                    }catch (Exception e){
                        Log.e("Bollinger on rsponse",e.getMessage());
                        bollingerValue1Tv.setText("NA");
                        bollingerValue2Tv.setText("NA");
                        bollingerValue3Tv.setText("NA");
                        Utils.makeSnackBar(findViewById(R.id.coordinatorLayout),"There was an error getting information on this stock", Snackbar.LENGTH_LONG);

                    }
                }

            }

            @Override
            public void onFailure(Call<Bollinger_Bean> call, Throwable t) {
                Utils.print("failed to pull data for this stock");
            }
        });


    }

}
