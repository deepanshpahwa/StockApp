package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.stalker.APIs.AlphaVantageAPI;
import com.example.stalker.Bean.BollingerTechnicalData;
import com.example.stalker.Bean.Bollinger_Bean;
import com.example.stalker.Bean.CustomIndicatorBeanRealm;
import com.example.stalker.Bean.ListOFCustomIndicatorsBean;
import com.example.stalker.Bean.MACD_Bean;
import com.example.stalker.Bean.MACD_TechnicalData;
import com.example.stalker.Bean.StockPriceBean;
import com.example.stalker.Bean.StockPriceData;
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
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewIndicator extends AppCompatActivity {

    private static String STOCKABBR = "";
    private static String STOCKNAME = "";
    private static String FIRST_INDICATOR = "";
    private static String SECOND_INDICATOR = "";
    private static String THIRD_INDICATOR = "";
    private static boolean HAS_THIRD_INDICATOR ;
    private static String FIRST_MATH_FUNCTION = "";
    private static String SECOND_MATH_FUNCTION = "";
    private ArrayList<Entry> values = new ArrayList<>();
    private static Map<String, MACD_TechnicalData> MACD_hashMap = new HashMap<>();
    private static Map<String, BollingerTechnicalData> Bollinger_hashMap = new HashMap<>();
    private static int NUMBER_OF_DATAPOINTS = 50;
    Realm realm;
    boolean showButton;

    public NewIndicator(boolean showSaveButton) {
        if (showSaveButton) {
            showButton = showSaveButton;
        }
    }
    public NewIndicator() {
        showButton=true;
    }


    public Intent getIntent(Activity activity){
        Intent intent = new Intent(activity, this.getClass());
        return intent;
    }

    MACD_TechnicalData macdValues;
    BollingerTechnicalData data;

    DataPoint[] dataPoint;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");



    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_indicator);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.ANI_my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#A9A9A9"));

//        Utils.setToolbar(NewIndicator);
        realm = Realm.getDefaultInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("companyAbbr") != null){
            STOCKABBR =bundle.getString(Utils.COMPANY_ABBR);
            STOCKNAME =bundle.getString(Utils.COMPANY_NAME);

            FIRST_INDICATOR = bundle.getString(Utils.FIRST_ELEMENT);
            SECOND_INDICATOR = bundle.getString(Utils.SECOND_ELEMENT);
            FIRST_MATH_FUNCTION = bundle.getString(Utils.FIRST_MATH_FUNCTION);

            HAS_THIRD_INDICATOR = bundle.getBoolean(Utils.HAS_THIRD_ELEMENT);

            if (HAS_THIRD_INDICATOR){
                THIRD_INDICATOR = bundle.getString(Utils.THIRD_ELEMENT);
                SECOND_MATH_FUNCTION = bundle.getString(Utils.SECOND_MATH_FUNCTION);
            }

        }

        final EditText savedName = findViewById(R.id.saved_name_ANI);

        loadIndicatorChart(STOCKABBR,getString(R.string.stock_interval), "open", "200");
        loadPriceChart(STOCKABBR);



        Button submit = findViewById(R.id.NI_button);
        if (!showButton){
            submit.setVisibility(View.INVISIBLE);
            savedName.setVisibility(View.INVISIBLE);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.print("NIA "+savedName.getText().toString().trim());
                if (savedName.getText().toString().trim().length() == 0) {
                    Utils.makeToast(getApplicationContext(), "Please enter a name");
                } else {
                    realm.beginTransaction();
                    CustomIndicatorBeanRealm object = realm.createObject(CustomIndicatorBeanRealm.class);
                    ListOFCustomIndicatorsBean list;
                    if (realm.where(ListOFCustomIndicatorsBean.class).findFirst()==null){
                        list = realm.createObject(ListOFCustomIndicatorsBean.class);
                    }else{
                        list = realm.where(ListOFCustomIndicatorsBean.class).findFirst();
                    }
                    if (object.isValid() && list.isValid()) {
                        Utils.print("NIA HERE HERE");
                        object.setName(savedName.getText().toString().trim());
                        object.setFirstElement(FIRST_INDICATOR);
                        object.setSecondElement(SECOND_INDICATOR);
                        object.setFirstMathFunction(FIRST_MATH_FUNCTION);

                        object.setHasThirdElement(HAS_THIRD_INDICATOR);
                        if (HAS_THIRD_INDICATOR) {
                            object.setSecondMathFunction(SECOND_MATH_FUNCTION);//TODO change
                            object.setThirdElement(THIRD_INDICATOR);
                        }
                        list.getList().add(object);
                    }
                    realm.commitTransaction();

                    SavedIndicatorActivity savedIndicatorActivity = new SavedIndicatorActivity();
                    Intent intent = savedIndicatorActivity.getIntent(NewIndicator.this);
                    intent.putExtra("companyAbbr", STOCKABBR);
                    intent.putExtra("companyName",STOCKNAME);
                    NewIndicator.this.startActivity(intent);


                    //TODO disable button
                    //TODO show toast to display "View saved indicators"
                }
            }
        });


    }

    private void loadPriceChart(String symbol) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.AlphaVantageAPI_BaseURL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        AlphaVantageAPI alphaVantageAPI = retrofit.create(AlphaVantageAPI.class);
        Call<StockPriceBean> price_call = alphaVantageAPI.getStockPriceHistory(getString(R.string.alphavantage_time_series_daily), symbol, String.valueOf(R.string.Alpha_Vantage_API_key));

        price_call.enqueue(new Callback<StockPriceBean>() {
            @Override
            public void onResponse(Call<StockPriceBean> call, Response<StockPriceBean> response) {

                if (response.isSuccessful()) {

                    DataPoint[] dataPointsprice = new DataPoint[NUMBER_OF_DATAPOINTS];
                    ArrayList<String> temp = new ArrayList<>(NUMBER_OF_DATAPOINTS);
                    int index = 0;
                    for (Map.Entry<String, StockPriceData> entry : response.body().getMap().entrySet()){
                        if (index>=NUMBER_OF_DATAPOINTS){break;}
                        temp.add(entry.getKey());
                        index++;
                    }

                    Collections.sort(temp);

                    int indexi=0;
                    for (String entry:temp){
                        if (indexi >= NUMBER_OF_DATAPOINTS){break;}
                        dataPointsprice[indexi] = new DataPoint(Utils.parseDateForChart(entry),Double.valueOf(response.body().getMap().get(entry).get1Open()));//TODO
                        indexi++;
                    }
                    Utils.print(String.valueOf(indexi));

//                    try {
                        loadCustomIndicatorChart(dataPointsprice, R.id.priceChart);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<StockPriceBean> call, Throwable t) {

            }
        });
    }

    private void loadCustomIndicatorChart(DataPointInterface[] dataset, int chartViewId) {
        try {
            GraphView graphView = (GraphView) findViewById(chartViewId);
            Utils.print(String.valueOf(dataset));
            LineGraphSeries<DataPointInterface> series = new LineGraphSeries<>(dataset);
            graphView.addSeries(series);

            graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        return sdf.format(new Date((long) value));
                    } else {
                        return super.formatLabel(value, isValueX);
                    }
                }
            });
        }catch (Exception e){
            Utils.makeSnackBar(findViewById(R.id.ANI_coordinator_layout),"Error loading data.",Snackbar.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

    private void loadIndicatorChart(String symbol, String interval, String series_type, String time_period) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.AlphaVantageAPI_BaseURL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        final String strDate = Utils.getLatestBusinessDayDate();

        AlphaVantageAPI alphaVantageAPI = retrofit.create(AlphaVantageAPI.class);

        Call<MACD_Bean> MACD_call =  alphaVantageAPI.getMacdFromSearchQuery("MACD", symbol , interval, series_type, getString(R.string.Alpha_Vantage_API_key) );
        generateMAC_DHashmap(MACD_call);

        Call<Bollinger_Bean> bollinger_call = alphaVantageAPI.getBollingerFromSearchQuery("BBANDS", symbol, interval, time_period, series_type, getString(R.string.Alpha_Vantage_API_key));
        generateBollingerHashMap(strDate, bollinger_call);

        /**
        Other financial indicators can be added over here.
         */

        Utils.print("DATA PULLED");

    }

    private void performMathematicalFunction() {
        Utils.print("SIZE of HASHMAPS: "+MACD_hashMap.size()+"   ,  "+Bollinger_hashMap.size());


        if (Bollinger_hashMap==null || MACD_hashMap==null || Bollinger_hashMap.size()==0 || MACD_hashMap.size()==0){
            return;
        }


        DataPoint[] dataPoints = new DataPoint[NUMBER_OF_DATAPOINTS];

        ArrayList<String> temp = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, MACD_TechnicalData> entry : MACD_hashMap.entrySet()){
            if (index>=NUMBER_OF_DATAPOINTS){break;}
            temp.add(entry.getKey());
            index++;
        }

        Collections.sort(temp);

        if (FIRST_MATH_FUNCTION.equals("+")){

            int indexi=0;
            for (String entry:temp){
                if (indexi >= NUMBER_OF_DATAPOINTS){
                    break;
                }
//                Double sum = Double.valueOf(  MACD_hashMap.get(temp.get(indexi)).getMACDSignal()  ) + Double.valueOf(Bollinger_hashMap.get(temp.get(indexi)).getRlBand());
                Utils.print("try1"+ getValueFromIndicatorAndKey(FIRST_INDICATOR,temp.get(indexi)));
                Utils.print("try2"+  MACD_hashMap.get(temp.get(indexi)).getMACDSignal() );

                Double sum = Double.valueOf(  getValueFromIndicatorAndKey(FIRST_INDICATOR, temp.get(indexi)) ) + Double.valueOf(getValueFromIndicatorAndKey(SECOND_INDICATOR, temp.get(indexi)));
                dataPoints[indexi] = new DataPoint(Utils.parseDateForChart(entry),sum);

                indexi++;
            }
            Utils.print(String.valueOf(indexi));

        }else if(FIRST_MATH_FUNCTION.equals("-")){

            int indexi=0;
            for (String entry:temp){
                if (indexi >= NUMBER_OF_DATAPOINTS){
                    break;
                }
//                Double sum = Double.valueOf(  MACD_hashMap.get(temp.get(indexi)).getMACDSignal()  ) + Double.valueOf(Bollinger_hashMap.get(temp.get(indexi)).getRlBand());
                Double difference = Double.valueOf(  getValueFromIndicatorAndKey(FIRST_INDICATOR, temp.get(indexi)) ) - Double.valueOf(getValueFromIndicatorAndKey(SECOND_INDICATOR, temp.get(indexi)));
                dataPoints[indexi] = new DataPoint(Utils.parseDateForChart(entry),difference);

                indexi++;
            }
            Utils.print(String.valueOf(indexi));



        }else if(FIRST_MATH_FUNCTION.equals("x")){

            int indexi=0;
            for (String entry:temp){
                if (indexi >= NUMBER_OF_DATAPOINTS){
                    break;
                }
//                Double sum = Double.valueOf(  MACD_hashMap.get(temp.get(indexi)).getMACDSignal()  ) + Double.valueOf(Bollinger_hashMap.get(temp.get(indexi)).getRlBand());
                Double product = Double.valueOf(  getValueFromIndicatorAndKey(FIRST_INDICATOR, temp.get(indexi)) ) * Double.valueOf(getValueFromIndicatorAndKey(SECOND_INDICATOR, temp.get(indexi)));
                dataPoints[indexi] = new DataPoint(Utils.parseDateForChart(entry),product);

                indexi++;
            }
            Utils.print(String.valueOf(indexi));


        }else if(FIRST_MATH_FUNCTION.equals("/")){
            int indexi=0;
            for (String entry:temp){
                if (indexi >= NUMBER_OF_DATAPOINTS){
                    break;
                }
//                Double sum = Double.valueOf(  MACD_hashMap.get(temp.get(indexi)).getMACDSignal()  ) + Double.valueOf(Bollinger_hashMap.get(temp.get(indexi)).getRlBand());
                Double quotient = Double.valueOf(  getValueFromIndicatorAndKey(FIRST_INDICATOR, temp.get(indexi)) ) / Double.valueOf(getValueFromIndicatorAndKey(SECOND_INDICATOR, temp.get(indexi)));
                dataPoints[indexi] = new DataPoint(Utils.parseDateForChart(entry),quotient);

                indexi++;
            }
            Utils.print(String.valueOf(indexi));

        }

        loadCustomIndicatorChart(dataPoints,R.id.new_graph_view);

    }

    Double getValueFromIndicatorAndKey(String indicator, String key){
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

//    @Override
//    protected void onPause() {
//        super.onPause();
//        super.onStop();
//        super.onDestroy();//TODO fix
//    }
//
//    @Override
//    protected void onStop() {
//        super.onPause();
//        super.onStop();
//        super.onDestroy();//TODO fix
//    }
}
