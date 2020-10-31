package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String NAME="NAME";
    public static final String HEIGHT="HEIGHT";
    public static final String WEIGHT ="WEIGHT";
    public static final String GENDER="GENDER";
    public static final String FLAG="FLAG";
    public static final String HSCALE="HSCALE";
    public static final String WSCALE="WSCALE";
    private SharedPreferences preferences ;
    private SharedPreferences.Editor editor ;
    EditText name,height,weight;
    TextView result;
    Spinner gender,heightSpinner,weightSpinner;
    CheckBox chk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        setupViews();
        checkPref();

    }

    private void checkPref() {
        boolean flag =preferences.getBoolean(FLAG,false);
        if(flag){
            chk.setChecked(true);
            name.setText(preferences.getString(NAME,""));
            height.setText(preferences.getString(HEIGHT,""));
            weight.setText(preferences.getString(WEIGHT,""));
            if(preferences.getString(GENDER,"Male").equals("Male")){
                gender.setSelection(0);
            }else{
                gender.setSelection(1);
            }
            if(preferences.getString(HSCALE,"").equals("Centimeter")){
                heightSpinner.setSelection(1);
            }
            if(preferences.getString(WSCALE,"").equals("Bound")){
                weightSpinner.setSelection(1);
            }else if(preferences.getString(WSCALE,"").equals("Gram")){
                weightSpinner.setSelection(2);
            }

        }
    }

    private void setupViews() {
        name = findViewById(R.id.name);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        result = findViewById(R.id.result);
        gender =findViewById(R.id.gender);
        heightSpinner =findViewById(R.id.heightSpinner);
        weightSpinner =findViewById(R.id.weightSpinner);
        chk  = findViewById(R.id.checksave);
        fillSpinners();
    }


    public void fillSpinners(){
        fillGenderSpinner();
        fillHeightSpinner();
        fillWeightSpinner();
    }
    public void fillWeightSpinner(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Kilogram") ;
        strings.add("Bound");
        strings.add("Gram") ;
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.addAll(strings);
        weightSpinner.setAdapter(spinnerAdapter);
    }
    public void fillHeightSpinner(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Meter") ;
        strings.add("Centimeter");
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.addAll(strings);
        heightSpinner.setAdapter(spinnerAdapter);
    }
    public void fillGenderSpinner(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Male") ;
        strings.add("Female");
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.addAll(strings);
        gender.setAdapter(spinnerAdapter);
    }
    public void btnGoToTimer(View view) {
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }


    public void onclickGetBMI(View view) {
       if(!height.getText().toString().equals("")||!weight.getText().toString().equals("")){
           Double w=Double.valueOf(weight.getText().toString());
           Double h = Double.valueOf(height.getText().toString());
           if(heightSpinner.getSelectedItem().toString().equals("Centimeter")){
               h = h /100;
           }
           if(weightSpinner.getSelectedItem().toString().equals("Bound")){
               w = w * 0.453592;
           }else if(weightSpinner.getSelectedItem().toString().equals("Gram")){
               w = w/1000;
           }

           float bmi = (float) (w / Math.pow(h,2));
           if(bmi<18.5){
               result.setText("BMI : "+bmi+" ,Underweight");
           }else if( bmi >= 18.5 && bmi < 24.9){
               result.setText("BMI : "+bmi+" ,Normal weight");
           }else if(bmi >= 25 && bmi <29.9){
               result.setText("BMI : "+bmi+" ,Overweight ");
           }else{
               result.setText("BMI : "+bmi+" ,Obesity ");
           }
           if(chk.isChecked()){
               editor.putBoolean(FLAG,true);
               editor.putString(NAME,name.getText().toString());
               editor.putString(GENDER,gender.getSelectedItem().toString());
               editor.putString(WSCALE,weightSpinner.getSelectedItem().toString());
               editor.putString(HSCALE,heightSpinner.getSelectedItem().toString());
               editor.putString(HEIGHT,height.getText().toString());
               editor.putString(WEIGHT,weight.getText().toString());
               editor.commit();
           }
       }
    }
}