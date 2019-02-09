package com.example.stalker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.stalker.Bean.Quote;
import com.example.stalker.Bean.Symbol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

//    private ArrayList<Map<String, Symbol>> stocks = new ArrayList<>();
     Map<String, Symbol> stocksMAP = new HashMap<>();


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

        populateStockListArray(rvStocks);
        if (stocksMAP.isEmpty()) System.out.print("::::::::stocks is empty");
//        Stock stock = new Stock();
//        stock.setStockCode("NFLX");
//        stock.setMACD(100d);
//        stock.setStockPrice("10000");
//        stock.setTimeStamp("today");
//        stocks.add(stock);

//        rvStocks.setLayoutManager(new LinearLayoutManager(this));
//        StockRvAdapter adapter = new StockRvAdapter(stocksMAP);
//        rvStocks.setAdapter(adapter);






    }

    private void populateStockListArray(final RecyclerView rvStocks) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.iextrading.com/1.0/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        IEXtradingAPI IEXtradingAPI = retrofit.create(IEXtradingAPI.class);

        Call<Map<String, Symbol>> call = IEXtradingAPI.getDailyStockBatch("quote", "MSFT");//TODO
//        Map<String, Symbol> ret = call.execute().body();

        call.enqueue(new Callback<Map<String, Symbol>>() {
            @Override
            public void onResponse(Call<Map<String, Symbol>> call, Response<Map<String, Symbol>> response) {
                if (response.isSuccessful()) {
                    System.out.println("::::::::::::::::::::onResponse is successful" + response);
                    stocksMAP = response.body();
                    Symbol temp = stocksMAP.get("MSFT");
                    Quote temp2 = temp.getQuote();
                    temp2.getCompanyName();

                    rvStocks.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    StockRvAdapter adapter = new StockRvAdapter(stocksMAP);
                    rvStocks.setAdapter(adapter);

//                    stocksMAP
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
