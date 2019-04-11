package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class CustomIndicatorActivity extends Activity {

    private static String STOCKABBR;
    private static String STOCKNAME;

    public Intent getIntent(Activity activity){
        Intent intent = new Intent(activity, this.getClass());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_indicator);

        Bundle bundle = getIntent().getExtras();
        TextView stockNameTv,stockNameAbbrTv;

        stockNameTv = findViewById(R.id.CIA_company_name_tv);
        stockNameAbbrTv = findViewById(R.id.CIA_company_abbr_tv);


        if (bundle.getString("companyAbbr") != null){
            STOCKABBR =bundle.getString("companyAbbr");
            STOCKNAME =bundle.getString("companyName");
        }

        MaterialSpinner  firstSpinner = (MaterialSpinner ) findViewById(R.id.spinner_first_indicator);
        MaterialSpinner secondSpinner = (MaterialSpinner ) findViewById(R.id.spinner_second_indicator);
        MaterialSpinner  mathematicalFunctions = (MaterialSpinner ) findViewById(R.id.spinner_mathematical_function);
        Button button = findViewById(R.id.ACI_Button);

        setupFISpinner(firstSpinner);
        setupFISpinner(secondSpinner);

        setupMFspinner(mathematicalFunctions, R.array.mathematical_functions);


        stockNameTv.setText(STOCKNAME);
        stockNameAbbrTv.setText(STOCKABBR);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewIndicator newIndicator =  new NewIndicator();
                Intent intent = newIndicator.getIntent(CustomIndicatorActivity.this);
                intent.putExtra("companyAbbr",STOCKABBR);
                intent.putExtra("companyName",STOCKNAME);
                CustomIndicatorActivity.this.startActivity(intent);
            }
        });

    }

    private void setupMFspinner(MaterialSpinner  mathematicalFunctions, int p) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                p, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mathematicalFunctions.setAdapter(adapter);
    }

    private void setupFISpinner(MaterialSpinner  spinner) {
        setupMFspinner(spinner, R.array.financial_indicators);
    }


}
