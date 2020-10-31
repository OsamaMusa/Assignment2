package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private int seconds = 0;
    private boolean running;
    private EditText H,M,S;
    TextView remining;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if(savedInstanceState !=null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        H =findViewById(R.id.hours);
        M=findViewById(R.id.min);
        S =findViewById(R.id.sec);
        remining= findViewById(R.id.remining);



        runTimer();
    }
    private void runTimer(){

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(seconds==-1){
                    running = false;
                }else {
                    int hours = seconds / 3600;
                    int minutes = seconds % 3600 / 60;
                    int snds = seconds % 60;
                    remining.setText(hours+" : "+minutes+" : "+snds);
                    if(seconds<10) {
                        remining.setTextColor(Color.rgb(255,0,0));
                    }else{
                        remining.setTextColor(Color.rgb(0,0,0));

                    }
                    if (running) {
                        --seconds;
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("seconds", seconds);
        bundle.putBoolean("running", running);
    }

    public void btnGoToBMI(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void onClickStart(View view) {
        seconds=Integer.valueOf(H.getText().toString())*60*60;
        seconds+=Integer.valueOf(M.getText().toString())*60;
        seconds+=Integer.valueOf(S.getText().toString());
        int hours = seconds / 3600;
        int minutes = seconds % 3600 / 60;
        int snds = seconds % 60;
        H.setText(hours+"");
        M.setText(minutes+"");
        S.setText(snds+"");
        running = true;


    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        H.setText("00");
        M.setText("00");
        S.setText("00");
        remining.setText("00 : 00 : 00");
        seconds=0;
        running = false;

    }
}