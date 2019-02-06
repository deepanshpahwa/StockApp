package com.example.stalker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.stalker.Bean.Symbol;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

//    private ArrayList<Map<String, Symbol>> stocks = new ArrayList<>();
    private Map<String, Symbol> stocksMAP = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#A9A9A9"));

        RecyclerView rvStocks = (RecyclerView) findViewById(R.id.rvStocks);
        //stocks = //get through API

        populateStockListArray();
//        Stock stock = new Stock();
//        stock.setStockCode("NFLX");
//        stock.setMACD(100d);
//        stock.setStockPrice("10000");
//        stock.setTimeStamp("today");
//        stocks.add(stock);
        StockRvAdapter adapter = new StockRvAdapter(stocksMAP);
        rvStocks.setAdapter(adapter);
        rvStocks.setLayoutManager(new LinearLayoutManager(this));






    }

    private void populateStockListArray() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.iextrading.com/1.0/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        IEXtradingAPI IEXtradingAPI = retrofit.create(IEXtradingAPI.class);

        Call<Map<String, Symbol>> call = IEXtradingAPI.getDailyStockBatch("quote", "MSFT,AAPL");//TODO

        call.enqueue(new Callback<Map<String, Symbol>>() {
            @Override
            public void onResponse(Call<Map<String, Symbol>> call, Response<Map<String, Symbol>> response) {
                if (response.isSuccessful()) {
                    System.out.println("::::::::::::::::::::onResponse is successful" + response);
                    stocksMAP = response.body();
                }else{
                    System.out.println("::::::::::::::::::::onResponse not successful" + response);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Symbol>> call, Throwable t) {
                System.out.println("::::::::::::::::::::onFailure"+t.getMessage());

            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
}
