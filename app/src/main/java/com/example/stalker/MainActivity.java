package com.example.stalker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.stalker.APIs.IEXtradingAPI;
import com.example.stalker.Bean.RealmObjectListOfFavStocks;
import com.example.stalker.Bean.Symbol;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainActivityRVAdapter.ItemClickListener{

//    private ArrayList<Map<String, Symbol>> stocks = new ArrayList<>();
    Map<String, Symbol> stocksMAP = new HashMap<>();
    MainActivityRVAdapter adapter;
    RecyclerView rvStocks;
    private Context context;
//    RealmObjectListOfFavStocks realmObjectListOfFavStocks = new RealmObjectListOfFavStocks();
    Realm realm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();


        Realm.init(context);
        realm = Realm.getDefaultInstance();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#A9A9A9"));


        rvStocks = (RecyclerView) findViewById(R.id.rvStocks);


//        favoriteStocks.add(new RealmObjectStock("AAPL"));
//        favoriteStocks.add(new RealmObjectStock("NFLX"));
//        favoriteStocks.add(new RealmObjectStock("MSFT"));
//        favoriteStocks.add(new RealmObjectStock("AMZN"));
//        favoriteStocks.add(new RealmObjectStock("GOOGL"));
//        favoriteStocks.add(new RealmObjectStock("TWTR"));
//        favoriteStocks.add(new RealmObjectStock("TSLA"));
//        favoriteStocks.add(new RealmObjectStock("FB"));
//        favoriteStocks.add(new RealmObjectStock("GPRO"));


        realm.beginTransaction();
        RealmObjectListOfFavStocks object = realm.createObject(RealmObjectListOfFavStocks.class);
        if(object.isValid()){
            object.getList().add("AAPL");
            object.getList().add("NFLX");
        }
        realm.commitTransaction();
//        realm.close();


        populateStockListArray(rvStocks);

    }

    private void populateStockListArray(final RecyclerView rvStocks) {
        final RealmObjectListOfFavStocks favoriteStocks = realm.where(RealmObjectListOfFavStocks.class).findFirst();

        StringBuilder stringOfStocks = new StringBuilder();
        for (String stock: favoriteStocks.getList()){
            stringOfStocks.append(stock);
            stringOfStocks.append(",");
        }
        stringOfStocks.replace(stringOfStocks.length()-1,stringOfStocks.length(),"");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.IEXtradingAPI_BaseURL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        IEXtradingAPI IEXtradingAPI = retrofit.create(IEXtradingAPI.class);

        Call<Map<String, Symbol>> call = IEXtradingAPI.getDailyStockBatch("quote", stringOfStocks.toString());//TODO

        call.enqueue(new Callback<Map<String, Symbol>>() {
            @Override
            public void onResponse(Call<Map<String, Symbol>> call, Response<Map<String, Symbol>> response) {
                if (response.isSuccessful()) {
                    Utils.print("onResponse is successful: "+response);
                    stocksMAP = response.body();

                    rvStocks.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new MainActivityRVAdapter(stocksMAP, favoriteStocks);
                    adapter.setClickListener(MainActivity.this);
                    rvStocks.setAdapter(adapter);


//                    stocksMAP
                }else{
                    Utils.print("onResponse not successful: " + response);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Symbol>> call, Throwable t) {
                Utils.print("onFailure: "+t.getMessage());
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
        Utils.print("onItemClick: "+position);

        final RealmObjectListOfFavStocks favoriteStocks = realm.where(RealmObjectListOfFavStocks.class).findFirst();
        String stockAbbr = favoriteStocks.getList().get(position);

        StockDetailActivity stockDetailActivity = new StockDetailActivity();//TODO
        Intent intent = stockDetailActivity.getIntent(MainActivity.this);
        intent.putExtra("companyAbbr",stockAbbr);
        intent.putExtra("companyName",adapter.getItem(stockAbbr).getQuote().getCompanyName());
        MainActivity.this.startActivity(intent);
        //get item using the position and the array list

    }

    @Override
    protected void onResume() {
        super.onResume();
        populateStockListArray( rvStocks);
    }
}