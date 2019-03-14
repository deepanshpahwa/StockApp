package com.example.stalker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.stalker.APIs.AlphaVantageAPI;
import com.example.stalker.Bean.ListOfBestMatches;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoriteStocks extends AppCompatActivity implements SearchView.OnQueryTextListener  {

    FavoriteStocksRVAdapter rvAdapter ;
    ListOfBestMatches listOfBestMatches = null;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_favorite_stocks);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#A9A9A9"));

        recyclerView = findViewById(R.id.stock_rv);


    }

    private void attachAdapterAndPopulateRV() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvAdapter = new FavoriteStocksRVAdapter(getApplicationContext(), listOfBestMatches );
//         rvAdapter.setclicklistener(this);//TODO
        recyclerView.setAdapter(rvAdapter);
    }

    private void doMySearch(String query) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.AlphaVantageAPI_BaseURL))
                .addConverterFactory(GsonConverterFactory.create()).build();

         AlphaVantageAPI alphaVantageAPI = retrofit.create(AlphaVantageAPI.class);

        Call<ListOfBestMatches> call = alphaVantageAPI.getStockFromSearchQuery(getString(R.string.AlphaVantage_SymbolSearchFunction), query, getString(R.string.Alpha_Vantage_API_key));

        call.enqueue(new Callback<ListOfBestMatches>() {
            @Override
            public void onResponse(Call<ListOfBestMatches> call, Response<ListOfBestMatches> response) {
                if (response.isSuccessful()) {

                    listOfBestMatches = response.body();
                    Utils.print(response.body().toString());

                    attachAdapterAndPopulateRV();
                }
                System.out.println(":::::::::::outside"+response.body());
                Utils.print("onResponse outside"+response.body().toString());

            }

            @Override
            public void onFailure(Call<ListOfBestMatches> call, Throwable t) {
                Utils.print(call.toString());
                Utils.print("onFailure");
                Utils.print(t.getMessage());

            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.favorites_toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Stocks");
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        if (s.length()!=0) {
            doMySearch(s);
        }
//        Toast.makeText(this, "Query Inserted", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length()!=0) {
            onQueryTextSubmit(s);
        }
        return true;
    }
}
