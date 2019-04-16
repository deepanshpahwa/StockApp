package com.example.stalker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class NewIndicatorInterfaceActivity extends AppCompatActivity {

    private static String STOCKABBR;
    private static String STOCKNAME;
    String item;


    public Intent getIntent(Activity activity){
        Intent intent = new Intent(activity, this.getClass());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_indicator_interface);



        Toolbar myToolbar = (Toolbar) findViewById(R.id.ACI_my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));

        Bundle bundle = getIntent().getExtras();
        TextView stockNameTv,stockNameAbbrTv;

        stockNameTv = findViewById(R.id.CIA_company_name_tv);
        stockNameAbbrTv = findViewById(R.id.CIA_company_abbr_tv);


        if (bundle.getString("companyAbbr") != null){
            STOCKABBR =bundle.getString("companyAbbr");
            STOCKNAME =bundle.getString("companyName");
        }

        final MaterialSpinner  firstSpinner = (MaterialSpinner ) findViewById(R.id.spinner_first_indicator);
        final MaterialSpinner secondSpinner = (MaterialSpinner ) findViewById(R.id.spinner_second_indicator);
        final MaterialSpinner thirdSpinner = (MaterialSpinner ) findViewById(R.id.spinner_third_indicator);

        final MaterialSpinner  firstMathFunctionSpinner = (MaterialSpinner ) findViewById(R.id.spinner_first_math_function);
        final MaterialSpinner  secondMathFunctionSpinner = (MaterialSpinner ) findViewById(R.id.spinner_second_math_function);
        Button button = findViewById(R.id.ACI_Button);

        setupFISpinner(firstSpinner);
        setupFISpinner(secondSpinner);
        setupFISpinner(thirdSpinner);

        setupMFspinner(firstMathFunctionSpinner, R.array.mathematical_functions);
        setupMFspinner(secondMathFunctionSpinner, R.array.mathematical_functions);

        stockNameTv.setText(STOCKNAME);
        stockNameAbbrTv.setText(STOCKABBR);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstSpinner.getSelectedIndex()==0){
                    Utils.makeToast(getApplicationContext(),"Please Select at least one indicator");
                }else if(firstSpinner.getSelectedIndex()!=0 && secondSpinner.getSelectedIndex()!=0 && firstMathFunctionSpinner.getSelectedIndex()==0){
                    Utils.makeToast(getApplicationContext(), "Please select a mathematical function");
                }else if(firstSpinner.getSelectedIndex()!=0 && secondSpinner.getSelectedIndex()!=0 && thirdSpinner.getSelectedIndex()!=0 && firstMathFunctionSpinner.getSelectedIndex()==0){
                    Utils.makeToast(getApplicationContext(), "Please select a mathematical function");
                }else if(firstSpinner.getSelectedIndex()!=0 && secondSpinner.getSelectedIndex()!=0 && thirdSpinner.getSelectedIndex()!=0 && secondMathFunctionSpinner.getSelectedIndex()==0){
                    Utils.makeToast(getApplicationContext(), "Please select a mathematical function");
                }else {
                    String[] array = getResources().getStringArray(R.array.financial_indicators);
                    String firstElement = array[firstSpinner.getSelectedIndex()];
                    String secondElement = array[secondSpinner.getSelectedIndex()];
                    String thirdElement = array[thirdSpinner.getSelectedIndex()];

                    String[] array2 = getResources().getStringArray(R.array.mathematical_functions);

                    String firstMathFunction = array2[firstMathFunctionSpinner.getSelectedIndex()];
                    String secondMathFunction = array2[secondMathFunctionSpinner.getSelectedIndex()];

                    CustomIndicatorActivity customIndicatorActivity = new CustomIndicatorActivity();
                    Intent intent = customIndicatorActivity.getIntent(NewIndicatorInterfaceActivity.this);

                    intent.putExtra(Utils.COMPANY_ABBR, STOCKABBR);
                    intent.putExtra(Utils.COMPANY_NAME, STOCKNAME);
                    intent.putExtra(Utils.FIRST_ELEMENT, firstElement);
                    intent.putExtra(Utils.SECOND_ELEMENT, secondElement);
                    intent.putExtra(Utils.FIRST_MATH_FUNCTION, firstMathFunction);
                    if (thirdSpinner.getSelectedIndex()!=0 || secondMathFunctionSpinner.getSelectedIndex()!=0){
                        intent.putExtra(Utils.HAS_THIRD_ELEMENT,true);
                        intent.putExtra(Utils.THIRD_ELEMENT, thirdElement);
                        intent.putExtra(Utils.SECOND_MATH_FUNCTION, secondMathFunction);
                    }else{
                        intent.putExtra(Utils.HAS_THIRD_ELEMENT,false);
                    }

                    intent.putExtra("hideButton", false);


                    NewIndicatorInterfaceActivity.this.startActivity(intent);
                }
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
