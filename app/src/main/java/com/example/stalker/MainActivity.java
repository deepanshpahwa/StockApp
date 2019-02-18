package com.example.stalker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.stalker.APIs.IEXtradingAPI;
import com.example.stalker.Bean.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements StockRvAdapter.ItemClickListener{

//    private ArrayList<Map<String, Symbol>> stocks = new ArrayList<>();
    Map<String, Symbol> stocksMAP = new HashMap<>();
    StockRvAdapter adapter;
    RecyclerView rvStocks;
    ArrayList<String> favoriteStocks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#A9A9A9"));



        rvStocks = (RecyclerView) findViewById(R.id.rvStocks);

        favoriteStocks = new ArrayList<>();
        favoriteStocks.add("AAPL");
        favoriteStocks.add("NFLX");
        favoriteStocks.add("MSFT");
        favoriteStocks.add("AMZN");
        favoriteStocks.add("GOOGL");
        favoriteStocks.add("TWTR");
        favoriteStocks.add("TSLA");
        favoriteStocks.add("FB");
        favoriteStocks.add("GPRO");


//        Menu navmen = myToolbar.getMenu();
//        MenuItem favoriteButton = navmen.findItem(R.id.favorites).setVisible(true);
//        favoriteButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Intent intent = new Intent(MainActivity.this, FavoriteStocks.class);
//                MainActivity.this.startActivity(intent);
//                return true;
//            }
//        });
        populateStockListArray(rvStocks, favoriteStocks);

        //just a check
        if (stocksMAP.isEmpty()) System.out.print("::::::::stocks is empty");


    }

    private void populateStockListArray(final RecyclerView rvStocks, final ArrayList<String> favoriteStocks) {

        StringBuilder stringOfStocks = new StringBuilder();
        for (String stock: favoriteStocks){
            stringOfStocks.append(stock);
            stringOfStocks.append(",");
        }
        stringOfStocks.replace(stringOfStocks.length()-1,stringOfStocks.length(),"");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.IEXtradingAPI_BaseURL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        IEXtradingAPI IEXtradingAPI = retrofit.create(IEXtradingAPI.class);

        Call<Map<String, Symbol>> call = IEXtradingAPI.getDailyStockBatch("quote", stringOfStocks.toString());//TODO
//        Map<String, Symbol> ret = call.execute().body();

        call.enqueue(new Callback<Map<String, Symbol>>() {
            @Override
            public void onResponse(Call<Map<String, Symbol>> call, Response<Map<String, Symbol>> response) {
                if (response.isSuccessful()) {
                    System.out.println("::::::::::::::::::::onResponse is successful" + response);
                    stocksMAP = response.body();

//                    rvStocks.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new StockRvAdapter(stocksMAP, favoriteStocks);
                    adapter.setClickListener(MainActivity.this);
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
        menu.clear();
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorites:
                Intent intent = new Intent(MainActivity.this, FavoriteStocks.class);
                MainActivity.this.startActivity(intent);
            case R.id.settings:
                //open the settings page
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        new StockDetailActivity(MainActivity.this, "MSFT");//TODO
        //get item using the position and the array list
//        MainActivity.this.startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        populateStockListArray( rvStocks, favoriteStocks);
    }
}
