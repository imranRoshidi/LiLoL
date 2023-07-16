package com.example.lilol;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SimulatorActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView maybank;
    ImageView tng;
    ImageView back_icon;
    ProgressBar progressBar;
    int progressStatus = 0;
    TextView textView;
    Handler handler = new Handler();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);


//        maybank = (ImageView) findViewById(R.id.maybank_icon);
        tng = (ImageView) findViewById(R.id.tng_icon);
        back_icon = (ImageView) findViewById(R.id.back_icon);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_load_simulator);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

//        maybank.setOnClickListener(this);
        tng.setOnClickListener(this);
        back_icon.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){/*
            case R.id.maybank_icon:
                openMaybankActivity();
                break;*/
            case R.id.tng_icon:
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        openTngActivity();
                        finish();
                    }
                }, 5000);
                break;
            case R.id.back_icon:
                openHomeActivity();
                break;
        }
    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openMaybankActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openTngActivity() {
        Intent intent = new Intent(this, TngSplashscreenActivity.class);
        startActivity(intent);
    }

}