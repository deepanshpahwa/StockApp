package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.example.stalker.APIs.AlphaVantageAPI;
import com.example.stalker.APIs.IEXtradingAPI;
import com.example.stalker.Bean.BollingerTechnicalData;
import com.example.stalker.Bean.Bollinger_Bean;
import com.example.stalker.Bean.MACD_Bean;
import com.example.stalker.Bean.MACD_TechnicalData;
import com.github.mikephil.charting.data.Entry;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewIndicator extends Activity{

    private static String STOCKABBR = "";
    private static String STOCKNAME = "";
    private static String FIRST_INDICATOR = "";
    private static String SECOND_INDICATOR = "";
    private static String MATHEMATICAL_FUNCTION = "";
    private ArrayList<Entry> values = new ArrayList<>();
    private static Map<String, MACD_TechnicalData> MACD_hashMap = new HashMap<>();
    private static Map<String, BollingerTechnicalData> Bollinger_hashMap = new HashMap<>();

    public Intent getIntent(Activity activity){
        Intent intent = new Intent(activity, this.getClass());
        return intent;
    }

    MACD_TechnicalData macdValues;
    BollingerTechnicalData data;

    DataPoint[] dataPoint;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_indicator);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("companyAbbr") != null){
            STOCKABBR =bundle.getString("companyAbbr");
            STOCKNAME =bundle.getString("companyName");
            FIRST_INDICATOR = bundle.getString("firstSpinner_value");
            SECOND_INDICATOR = bundle.getString("secondSpinner_value");
            MATHEMATICAL_FUNCTION = bundle.getString("thirdSpinner_value");
        }

        getDataPoint();

        getStockPrice();




    }

    private void getStockPrice() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.AlphaVantageAPI_BaseURL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        IEXtradingAPI ieXtradingAPI = retrofit.create(IEXtradingAPI.class);
    }

    private void loadPriceChart(DataPointInterface[] dataset) {
        GraphView graphView = findViewById(R.id.priceChart);
        Utils.print(String.valueOf(dataset));
        LineGraphSeries<DataPointInterface> series = new LineGraphSeries<>(dataset);
        graphView.addSeries(series);

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX){
                    return sdf.format(new Date((long) value));
                }else{
                    return super.formatLabel(value, isValueX);
                }
            }
        });
    }

    private void loadChart(DataPointInterface[] dataset) {
        GraphView graphView = findViewById(R.id.new_graph_view);
        Utils.print(String.valueOf(dataset));
        LineGraphSeries<DataPointInterface> series = new LineGraphSeries<>(dataset);
        graphView.addSeries(series);

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX){
                    return sdf.format(new Date((long) value));
                }else{
                    return super.formatLabel(value, isValueX);
                }
            }
        });
    }

    private DataPointInterface[] getDataPoint() {
        dataPoint = new DataPoint[200];
        getData(STOCKABBR,getString(R.string.stock_interval), "open", "200");
        return dataPoint;
    }

    private void getData(String symbol, String interval, String series_type, String time_period) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.AlphaVantageAPI_BaseURL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        final String strDate = Utils.getLatestBusinessDayDate();
//        Utils.print(strDate);

        AlphaVantageAPI alphaVantageAPI = retrofit.create(AlphaVantageAPI.class);

        Call<MACD_Bean> MACD_call =  alphaVantageAPI.getMacdFromSearchQuery("MACD", symbol , interval, series_type, getString(R.string.Alpha_Vantage_API_key) );
        generateMAC_DHashmap(MACD_call);

        Call<Bollinger_Bean> bollinger_call = alphaVantageAPI.getBollingerFromSearchQuery("BBANDS", symbol, interval, time_period, series_type, getString(R.string.Alpha_Vantage_API_key));
        generateBollingerHashMap(strDate, bollinger_call);

        /**
        Other financial indicators can be added over here.
         */
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        while (MACD_hashMap==null || Bollinger_hashMap==null){
//            continue;
//        }

//        Single<Response<List<SampleUserDetailsPojo>>> testObservable= TestApplication.getRestClient().getRestInterface().getUserDetails();
        Utils.print("DATA PULLED");

//        performMathematicalFunction();

//        Call<MACD_Bean> call2 = alphaVantageAPI.getBollingerFromSearchQuery();
    }

    private void performMathematicalFunction() {
        if (Bollinger_hashMap==null || MACD_hashMap==null || Bollinger_hashMap.size()==0 || MACD_hashMap.size()==0){
            return;
        }

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Utils.print("SIZE of HASHMAPS: "+MACD_hashMap.size()+"   ,  "+Bollinger_hashMap.size());
        DataPoint[] dataPoints = new DataPoint[200];

//        if (FIRST_INDICATOR == Utils.MACD){
//            firstIndicator = MACD_hashMap.
//        }


        ArrayList<String> temp = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, MACD_TechnicalData> entry : MACD_hashMap.entrySet()){
            if (index>=200){break;}
            temp.add(entry.getKey());
            index++;
        }

        Collections.sort(temp);

        if (MATHEMATICAL_FUNCTION.equals("+")){

            int indexi=0;
            for (String entry:temp){
                if (indexi >= 200){
                    break;
                }
//                Double sum = Double.valueOf(  MACD_hashMap.get(temp.get(indexi)).getMACDSignal()  ) + Double.valueOf(Bollinger_hashMap.get(temp.get(indexi)).getRlBand());
                Utils.print("try1"+getvaluefromIndicatorandKey(FIRST_INDICATOR,temp.get(indexi)));
                Utils.print("try2"+  MACD_hashMap.get(temp.get(indexi)).getMACDSignal() );

                Double sum = Double.valueOf(  getvaluefromIndicatorandKey(FIRST_INDICATOR, temp.get(indexi)) ) + Double.valueOf(getvaluefromIndicatorandKey(SECOND_INDICATOR, temp.get(indexi)));
                dataPoints[indexi] = new DataPoint(Utils.parseDateForChart(entry),sum);

                indexi++;
            }
            Utils.print(String.valueOf(indexi));

        }else if(MATHEMATICAL_FUNCTION.equals("-")){

            int indexi=0;
            for (String entry:temp){
                if (indexi >= 200){
                    break;
                }
//                Double sum = Double.valueOf(  MACD_hashMap.get(temp.get(indexi)).getMACDSignal()  ) + Double.valueOf(Bollinger_hashMap.get(temp.get(indexi)).getRlBand());
                Double difference = Double.valueOf(  getvaluefromIndicatorandKey(FIRST_INDICATOR, temp.get(indexi)) ) - Double.valueOf(getvaluefromIndicatorandKey(SECOND_INDICATOR, temp.get(indexi)));
                dataPoints[indexi] = new DataPoint(Utils.parseDateForChart(entry),difference);

                indexi++;
            }
            Utils.print(String.valueOf(indexi));



        }else if(MATHEMATICAL_FUNCTION.equals("x")){

            int indexi=0;
            for (String entry:temp){
                if (indexi >= 200){
                    break;
                }
//                Double sum = Double.valueOf(  MACD_hashMap.get(temp.get(indexi)).getMACDSignal()  ) + Double.valueOf(Bollinger_hashMap.get(temp.get(indexi)).getRlBand());
                Double product = Double.valueOf(  getvaluefromIndicatorandKey(FIRST_INDICATOR, temp.get(indexi)) ) * Double.valueOf(getvaluefromIndicatorandKey(SECOND_INDICATOR, temp.get(indexi)));
                dataPoints[indexi] = new DataPoint(Utils.parseDateForChart(entry),product);

                indexi++;
            }
            Utils.print(String.valueOf(indexi));


        }else if(MATHEMATICAL_FUNCTION.equals("/")){
            int indexi=0;
            for (String entry:temp){
                if (indexi >= 200){
                    break;
                }
//                Double sum = Double.valueOf(  MACD_hashMap.get(temp.get(indexi)).getMACDSignal()  ) + Double.valueOf(Bollinger_hashMap.get(temp.get(indexi)).getRlBand());
                Double quotient = Double.valueOf(  getvaluefromIndicatorandKey(FIRST_INDICATOR, temp.get(indexi)) ) / Double.valueOf(getvaluefromIndicatorandKey(SECOND_INDICATOR, temp.get(indexi)));
                dataPoints[indexi] = new DataPoint(Utils.parseDateForChart(entry),quotient);

                indexi++;
            }
            Utils.print(String.valueOf(indexi));

        }

        loadChart(dataPoints);


    }

    Double getvaluefromIndicatorandKey(String indicator, String key){
        if (indicator == Utils.MACD){
            try {
                return Double.valueOf(MACD_hashMap.get(key).getMACDSignal());
            }catch (Exception e){
                Utils.print("Here 1");
                e.printStackTrace();
            }
        }

        else if (indicator == Utils.BOLL_LOWER){
            try{
                return Double.valueOf(Bollinger_hashMap.get(key).getRlBand());
            }catch (Exception e){
                Utils.print("Here 2");
                e.printStackTrace();
            }
        }

        else if (indicator == Utils.BOLL_MIDDLE){
            try{
                return Double.valueOf(Bollinger_hashMap.get(key).getRmBand());
            }catch (Exception e){
                Utils.print("Here 3");
                e.printStackTrace();
            }
        }

//        else//if (indicator == Utils.BOLL_UPPER)
//        {
//            try{
                return Double.valueOf(Bollinger_hashMap.get(key).getRuBand());
//            }catch (Exception e){
//                Utils.print("Here 4");
//                e.printStackTrace();
//            }
//        }

    }

    private void generateBollingerHashMap(final String strDate, Call<Bollinger_Bean> bollinger_call) {
        bollinger_call.enqueue(new Callback<Bollinger_Bean>() {
            @Override
            public void onResponse(Call<Bollinger_Bean> call, Response<Bollinger_Bean> response) {
                if (response.isSuccessful()){
                    try {
                        updateBollingerHashMap(response.body().getMap());
//                        Bollinger_hashMap = response.body().getMap();
                        performMathematicalFunction();
                    }catch (Exception e){
                        e.printStackTrace();
                        Utils.makeSnackBar(findViewById(R.id.ANI_coordinator_layout),"There was an error getting information on this stock", Snackbar.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<Bollinger_Bean> call, Throwable t) {
                Utils.print("failed to pull data for this stock");
                Utils.makeSnackBar(findViewById(R.id.ANI_coordinator_layout),"There was an error getting information on this stock", Snackbar.LENGTH_LONG);
            }
        });
    }

    private void updateMACDHashMap( Map<String, MACD_TechnicalData> hash){
        MACD_hashMap = hash;
    }

    private void updateBollingerHashMap( Map<String, BollingerTechnicalData> hash){
        Bollinger_hashMap = hash;
    }



    private void generateMAC_DHashmap(Call<MACD_Bean> MACD_call) {
        MACD_call.enqueue(new Callback<MACD_Bean>() {
            @Override
            public void onResponse(Call<MACD_Bean> call, Response<MACD_Bean> response) {
                if (response.isSuccessful()) {

                    try{
                        updateMACDHashMap(response.body().getMap());
                        performMathematicalFunction();
//                        MACD_hashMap = response.body().getMap();
                        /*
//                        macdValues = response.body().getMap().get(strDate);

                        DataPoint[] dataPoints = new DataPoint[200];

                        ArrayList<String> temp = new ArrayList<>();


                        int indexi =0;

                        for (Map.Entry<String, MACD_TechnicalData> entry : response.body().getMap().entrySet()){
                            if (indexi>=200){break;}
                            temp.add(entry.getKey());
                            indexi++;
                        }

                        Collections.sort(temp);

                        int indexj =0;

                        for (String entry:temp){
                            if (indexj>=200){break;}
                            dataPoints[indexj] = new DataPoint(Utils.parseDateForChart(entry), Double.valueOf(response.body().getMap().get(entry).getMACDSignal()));
                            indexj++;
                        }
                        loadChart(dataPoints);

                    */
                    }catch (Exception e){
                        e.printStackTrace();
                            Utils.makeSnackBar(findViewById(R.id.ANI_coordinator_layout),"There was an error getting information on this stock", Snackbar.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MACD_Bean> call, Throwable t) {
                Utils.print("failed to pull data for this stock");
            }
        });
    }

}
