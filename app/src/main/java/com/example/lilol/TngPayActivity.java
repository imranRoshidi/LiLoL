package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TngPayActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_icon;
    TextView balance_amount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_pay);

        back_icon = (ImageView)findViewById(R.id.back_icon);
        balance_amount = (TextView)findViewById(R.id.balance_amount);

        back_icon.setOnClickListener(this);

        Intent intent = this.getIntent();

        if (intent != null){

            String balance = intent.getStringExtra("balance_amount");
            balance_amount.setText("RM " + balance);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_icon) {
            openTngHomeActivity();
        }
    }

    public void openTngHomeActivity() {
        Intent intent = new Intent(this, TngHomeActivity.class);
        startActivity(intent);
    }

}
