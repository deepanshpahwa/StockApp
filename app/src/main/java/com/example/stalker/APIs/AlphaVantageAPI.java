package com.example.stalker.APIs;

import com.example.stalker.Bean.Symbol;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlphaVantageAPI {
    @GET("query")
    Call <Map<String, Symbol>> getStockFromSearchQuery(@Query("function")String symbolSearch, @Query("keywords") String searchQuery, @Query("apikey") String apikey );
}
