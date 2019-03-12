package com.example.stalker.APIs;

import com.example.stalker.Bean.Example;
import com.example.stalker.Bean.ListOfBestMatches;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlphaVantageAPI {
    @GET("query")
    Call <ListOfBestMatches> getStockFromSearchQuery(@Query("function") String symbolSearch, @Query("keywords") String searchQuery, @Query("apikey") String apikey);

    @GET("query")
    Call <Example> getMacdFromSearchQuery(@Query("function") String function_MACD, @Query("symbol") String stockSymbol, @Query("interval") String interval, @Query("series_type") String series_type, @Query("apikey") String apikey );
}
