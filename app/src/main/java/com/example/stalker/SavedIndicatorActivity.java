package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.stalker.Adapter.SavedIndicatorRVAdapter;

import androidx.annotation.Nullable;

public class SavedIndicatorActivity extends Activity {
    private SavedIndicatorRVAdapter adapter;

    public Intent getIntent(Activity activity){
        Intent intent = new Intent(activity, this.getClass());
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_indicator);

//        Utils.setToolbar(SavedIndicatorActivity.this);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(Color.parseColor("#A9A9A9"));
//        setActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.SI_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new SavedIndicatorRVAdapter(getApplicationContext());
//        adapter.setClickListener TODO if onclick stuff required
        recyclerView.setAdapter(adapter);










    }
}
