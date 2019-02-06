package com.example.stalker;


import com.example.stalker.Bean.Symbol;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IEXtradingAPI {
    @GET("stock/market/batch?")
    Call <Map<String, Symbol>> getDailyStockBatch(@Query("types") String types, @Query("symbols") String symbol);//change post


}
