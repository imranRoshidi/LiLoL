package com.example.lilol;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TngBillsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_icon;
    String amount;
    View postpaid_button, utilities_button, broadband_button, entertainment_button, loans_button, localcouncil_text, tnb_button, celcom_button, indahwater_button;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_bills);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_bills_disclaimer_sign);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        dialog.show();

        back_icon = (ImageView)findViewById(R.id.back_icon);
        postpaid_button = (View)findViewById(R.id.postpaid_button);
        utilities_button = (View)findViewById(R.id.utilities_button);
        broadband_button = (View)findViewById(R.id.broadband_button);
        entertainment_button = (View)findViewById(R.id.entertainment_button);
        loans_button = (View)findViewById(R.id.loans_button);
        localcouncil_text = (View)findViewById(R.id.localcouncil_button);
        tnb_button = (View)findViewById(R.id.tnbButton);
        celcom_button = (View)findViewById(R.id.celcomButton);
        indahwater_button = (View)findViewById(R.id.indahwaterButton);

        back_icon.setOnClickListener(this);
        postpaid_button.setOnClickListener(this);
        utilities_button.setOnClickListener(this);
        tnb_button.setOnClickListener(this);
        celcom_button.setOnClickListener(this);
        indahwater_button.setOnClickListener(this);

        Intent intent = this.getIntent();

        if (intent != null){

            amount = intent.getStringExtra("balance_amount");
        }
    }



    @Override
    public void onClick(View view) {
        String category;
        switch (view.getId()) {
            case R.id.postpaid_button:
                category = "postpaid";
                openTngBillsActivity2(category);
                break;
            case R.id.utilities_button:
                category = "utilities";
                openTngBillsActivity2(category);
                break;
            case R.id.broadband_button:
                category = "broadband";
                openTngBillsActivity2(category);
                break;
            case R.id.entertainment_button:
                category = "entertainment";
                openTngBillsActivity2(category);
                break;
            case R.id.loans_button:
                category = "loans";
                openTngBillsActivity2(category);
                break;
            case R.id.localcouncil_button:
                category = "localcouncil";
                openTngBillsActivity2(category);
                break;
            case R.id.tnbButton:
                openTNBBillsPayment();
                break;
            case R.id.celcomButton:
                openCelcomBillsPayment();
                break;
            case R.id.indahwaterButton:
                openIndahWaterBillsPayment();
                break;
            case R.id.back_icon:
                openTngHomeActivity();
                break;
        }
    }

    private void openTngBillsActivity2(String category) {
        Intent intent = new Intent(this, TngBillsActivity2.class);
        intent.putExtra("category", category);
        intent.putExtra("balance_amount", amount);
        startActivity(intent);
    }

    public void openTngHomeActivity() {
        Intent intent = new Intent(this, TngHomeActivity.class);
        startActivity(intent);
    }

    private void openTNBBillsPayment() {
        Intent intent = new Intent(this, TngBillsPayment.class);
        intent.putExtra("name", "Tenaga Nasional Berhad");
        intent.putExtra("image", R.drawable.tnb);
        intent.putExtra("balance_amount", amount);
        startActivity(intent);
    }

    private void openCelcomBillsPayment() {
        Intent intent = new Intent(this, TngBillsPayment.class);
        intent.putExtra("name", "Celcom");
        intent.putExtra("image", R.drawable.celcom);
        intent.putExtra("balance_amount", amount);
        startActivity(intent);
    }

    private void openIndahWaterBillsPayment() {
        Intent intent = new Intent(this, TngBillsPayment.class);
        intent.putExtra("name", "Indah Water");
        intent.putExtra("image", R.drawable.indahwater);
        intent.putExtra("balance_amount", amount);
        startActivity(intent);
    }

}
