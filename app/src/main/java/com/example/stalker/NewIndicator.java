package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.stalker.APIs.AlphaVantageAPI;
import com.example.stalker.Bean.BollingerTechnicalData;
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
import java.util.Map;

import androidx.annotation.Nullable;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewIndicator extends Activity{

    private static String STOCKABBR = "";
    private static String STOCKNAME = "";
    private ArrayList<Entry> values = new ArrayList<>();

    public Intent getIntent(Activity activity){
        Intent intent = new Intent(activity, this.getClass());
        return intent;
    }

    MACD_TechnicalData macdValues;
    BollingerTechnicalData data;
    TextView sticky;

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
        }

//        DataPointInterface[] dataset = getDataPoint();
        getDataPoint();

//        loadChart(dataset);


//        {
//            TextView tvX = findViewById(R.id.tvXMax);
//            TextView tvY = findViewById(R.id.tvYMax);
//            sticky = findViewById(R.id.TV_year);
//
////        SeekBar seekBarX = findViewById(R.id.seekBar1);
////        seekBarX.setOnSeekBarChangeListener(this);
//
////        SeekBar seekBarY = findViewById(R.id.seekBar2);
////        seekBarY.setMax(180);
////        seekBarY.setOnSeekBarChangeListener(this);
//
//            LineChart chart = findViewById(R.id.chart1);
//
////        chart.setOnChartValueSelectedListener(NewIndicator.this);
////        chart.setOnChartValueSelectedListener(NewIndicator.this);
//
//            ArrayList<Entry> values = new ArrayList<>();
//
////        getData(STOCKABBR,getString(R.string.stock_interval), "open", "200", values);
////        Long date1 =
//
//            Calendar calendar = Calendar.getInstance();
//
//            Date time1 = calendar.getTime();
//            Long timestamp1 = (time1.getTime());
//
//            calendar.add(Calendar.DATE, 1);
//            Date time2 = calendar.getTime();
//            Long timestamp2 = (time2.getTime());
//
//            calendar.add(Calendar.DATE, 1);
//            Date time3 = calendar.getTime();
//            Long timestamp3 = (time3.getTime());
//
//            Utils.print(String.valueOf(timestamp1));
//            Utils.print(String.valueOf(timestamp2));
//            Utils.print(String.valueOf(timestamp3));
//
////        values.add(new Entry(Long.valueOf(1546387200).floatValue(),130));
////        values.add(new Entry(Long.valueOf(1546473600).floatValue(),100));
////        values.add(new Entry(30,200f));
//
//            MyXAxisValueFormatter axisValueFormatter = new MyXAxisValueFormatter();
//
//
//            XAxis xAxis = chart.getXAxis();
//            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//            xAxis.setGranularity(1f);
//            xAxis.setGranularityEnabled(true);
////        xAxis.setValueFormatter(new MyAxisValueFormatter(chart, sticky));
//            xAxis.setValueFormatter(axisValueFormatter);
//            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//            xAxis.setDrawGridLines(true);
//
//            values.add(new Entry(1451660400L, 120f));
//            values.add(new Entry(1451685600L, 130f));
////        values.add(new Entry (1451721600L,150f));
//
//            LineDataSet set1 = new LineDataSet(values, "Data Set 1");
//            set1.setFillAlpha(110);
//
////        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
////        dataSets.add(set1);
//
//            LineData data = new LineData(set1);
//
//            chart.setData(data);
//        }

    }

    private void loadChart(DataPointInterface[] dataset) {
        GraphView graphView = findViewById(R.id.new_graph_view);
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

//        Calendar calendar = Calendar.getInstance();
//
//        Date date = calendar.getTime();
//
//        calendar.add(Calendar.DATE,1);
//        Date date1 = calendar.getTime();
//
//        calendar.add(Calendar.DATE,2);
//        Date date2 = calendar.getTime();
//
//        calendar.add(Calendar.DATE,3);
//        Date date3 = calendar.getTime();
//
//         DataPoint[] dataPoint1 = new DataPoint[]{
//                new DataPoint(date,1),
//                new DataPoint(date1,10),
//                new DataPoint(date2,7),
//                new DataPoint(date3,8)
//        };



        return dataPoint;


    }

    private void getData(String symbol, String interval, String series_type, String time_period) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.AlphaVantageAPI_BaseURL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        final String strDate = Utils.getLatestBusinessDayDate();
        Utils.print(strDate);

        AlphaVantageAPI alphaVantageAPI = retrofit.create(AlphaVantageAPI.class);

        Call<MACD_Bean> MACD_call =  alphaVantageAPI.getMacdFromSearchQuery("MACD", symbol , interval, series_type, getString(R.string.Alpha_Vantage_API_key) );
        MACD_call.enqueue(new Callback<MACD_Bean>() {
            @Override
            public void onResponse(Call<MACD_Bean> call, Response<MACD_Bean> response) {
                if (response.isSuccessful()) {

                    Utils.print("pulling MACD data");
//                    MACD_TechnicalData data = response.body().getMap().get("2019-03-08");//TODO get current date


//                    try{
                        macdValues = response.body().getMap().get(strDate);

//                        for (int i=0;i<macdValues.getMACDSignal().length(); i++){
//                            dataPoints[0] = new DataPoint(Utils.parseDateForChart(strDate),Double.valueOf(macdValues.getMACDSignal()));
//                        }
                        DataPoint[] dataPoints = new DataPoint[200];
                        Date iDate = Utils.parseDateForChart(strDate);

                        ArrayList<String> temp = new ArrayList<>();

                    int indexi =0;

                        for (Map.Entry<String, MACD_TechnicalData> entry : response.body().getMap().entrySet()){
                            if (indexi>=200){break;}
                            temp.add(entry.getKey());

//                            dataPoints[index] = new DataPoint(Utils.parseDateForChart(entry.getKey()),Double.valueOf(entry.getValue().getMACDSignal()));
//                            iDate
                            indexi++;
                        }

                        Collections.sort(temp);
//                        Collections.reverse(temp);

                    int indexj =0;

                    for (String entry:temp){
                            if (indexj>=200){break;}
//                            temp.add(Utils.parseDateForChart(entry.getKey()));

                            dataPoints[indexj] = new DataPoint(Utils.parseDateForChart(entry), Double.valueOf(response.body().getMap().get(entry).getMACDSignal()));
//                            iDate
                            indexj++;
                        }
                        loadChart(dataPoints);


//                    }catch (Exception e){
//                        Log.e("New INdicator", e.getMessage());
//                            Utils.makeSnackBar(findViewById(R.id.ANI_coordinator_layout),"There was an error getting information on this stock", Snackbar.LENGTH_LONG);
//                        }
    //                    if (macdValues!=null) {
    //                        macdValue1Tv.setText(macdValues.getMACD());
    //                    }else{
    //                        Utils.makeSnackBar(findViewById(R.id.coordinatorLayout),"There was an error getting information on this stock", Snackbar.LENGTH_LONG);
    //                    }
    //                Utils.print(data.getMACD()+", "+data.getMACDSignal());
                    }
            }

            @Override
            public void onFailure(Call<MACD_Bean> call, Throwable t) {
                Utils.print("failed to pull data for this stock");
            }
        });


//        final Call<Bollinger_Bean> bollinger_call = alphaVantageAPI.getBollingerFromSearchQuery("BBANDS", symbol, interval, time_period, series_type, getString(R.string.Alpha_Vantage_API_key));
//        bollinger_call.enqueue(new Callback<Bollinger_Bean>() {
//            @Override
//            public void onResponse(Call<Bollinger_Bean> call, Response<Bollinger_Bean> response) {
//                if (response.isSuccessful()){
//                    try {
//                        data = response.body().getMap().get(strDate);
//                        Calendar calendarDate;
//                        calendarDate = Utils.getPreviousDate(200);
//
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                        String date;
//                        String value;
//                        date = dateFormat.format(calendarDate.getTime());
//
////                        for (int i=0; i<200 ; i++){
////                            date = dateFormat.format(calendarDate.getTime());
////                            value = response.body().getMap().get(date).getRlBand();
////
////
//////                            values.add(new Entry(date, value));
////
////                        }
//                        values.add(new Entry(0,60));
//                        values.add(new Entry(0,70));
//                        values.add(new Entry(0,10));
//                        values.add(new Entry(0,200));
//
//                    }catch (Exception e){
//                        Utils.makeSnackBar(findViewById(R.id.coordinatorLayout),"There was an error getting information on this stock", Snackbar.LENGTH_LONG);
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Bollinger_Bean> call, Throwable t) {
//                Utils.print("failed to pull data for this stock");
//            }
//        });



//        Call<MACD_Bean> call2 = alphaVantageAPI.getBollingerFromSearchQuery();
    }

}
