package com.example.stalker.APIs;

import com.example.stalker.Bean.Bollinger_Bean;
import com.example.stalker.Bean.MACD_Bean;
import com.example.stalker.Bean.ListOfBestMatches;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlphaVantageAPI {
    @GET("query")
    Call <ListOfBestMatches> getStockFromSearchQuery(@Query("function") String symbolSearch, @Query("keywords") String searchQuery, @Query("apikey") String apikey);

    @GET("query")
    Call <MACD_Bean> getMacdFromSearchQuery(@Query("function") String function_MACD, @Query("symbol") String stockSymbol, @Query("interval") String interval, @Query("series_type") String series_type, @Query("apikey") String apikey );

    @GET("query")
    Call<Bollinger_Bean> getBollingerFromSearchQuery(@Query("function") String bbands, @Query("symbol") String symbol, @Query("interval") String interval,@Query("time_period") String time_period, @Query("series_type") String series_type, @Query("apikey") String alpha_vantage_api_key);
}
