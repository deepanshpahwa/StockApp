package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.stalker.Adapter.SavedIndicatorRVAdapter;
import com.example.stalker.Bean.CustomIndicatorBeanRealm;
import com.example.stalker.Bean.ListOFCustomIndicatorsBean;

import androidx.annotation.Nullable;
import io.realm.Realm;

public class SavedIndicatorActivity extends AppCompatActivity implements SavedIndicatorRVAdapter.ItemClickListener{
    private static String STOCKABBR;
    private static String STOCKNAME ;
    private SavedIndicatorRVAdapter adapter;

    public Intent getIntent(Activity activity){
        Intent intent = new Intent(activity, this.getClass());
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_indicator);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("companyAbbr") != null){
            STOCKABBR =bundle.getString("companyAbbr");
            STOCKNAME =bundle.getString("companyName");
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.ASI_my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#A9A9A9"));

//        Utils.setToolbar(SavedIndicatorActivity.this);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(Color.parseColor("#A9A9A9"));
//        setActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.SI_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new SavedIndicatorRVAdapter(getApplicationContext());
        adapter.setClickListener(SavedIndicatorActivity.this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        //open newIndicator

        Utils.print("HHHHSIA :OnITemClick");
        NewIndicator newIndicator = new NewIndicator();
        Intent i = newIndicator.getIntent(SavedIndicatorActivity.this);

        //todo this needs to be added
        i.putExtra("companyAbbr", STOCKABBR);
        i.putExtra("companyName", STOCKNAME);
        Realm realm = Realm.getDefaultInstance();
        CustomIndicatorBeanRealm object = realm.where(ListOFCustomIndicatorsBean.class).findFirst().getList().get(position);

        i.putExtra(Utils.FIRST_ELEMENT, object.getFirstElement());
        i.putExtra(Utils.SECOND_ELEMENT, object.getSecondElement());
        i.putExtra(Utils.FIRST_MATH_FUNCTION, object.getFirstMathFunction());
        i.putExtra(Utils.HAS_THIRD_ELEMENT,object.hasThirdElement());
        if (object.hasThirdElement()) {
            i.putExtra(Utils.SECOND_MATH_FUNCTION, object.getSecondMathFunction());
            i.putExtra(Utils.THIRD_ELEMENT, object.getThirdElement());
        }

        i.putExtra("hideButton", true);
        SavedIndicatorActivity.this.startActivity(i);
    }
}
