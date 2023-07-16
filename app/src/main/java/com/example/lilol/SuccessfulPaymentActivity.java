package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SuccessfulPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    Button done_button;
    TextView name, amount, datetime, transfer_text, remark_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_payment);

        done_button = (Button)findViewById(R.id.done_button);
        name = (TextView)findViewById(R.id.full_name);
        amount = (TextView)findViewById(R.id.amount);
        datetime = (TextView)findViewById(R.id.date_time);
        transfer_text = (TextView)findViewById(R.id.transfer_text);
        remark_text = (TextView)findViewById(R.id.remark);

        done_button.setOnClickListener(this);

        Intent intent = this.getIntent();

        if (intent != null){

            String name_text = intent.getStringExtra("name");
            String amount_text = intent.getStringExtra("amount");
            String date_time = intent.getStringExtra("datetime");
            String description = intent.getStringExtra("description");
            String remark = intent.getStringExtra("remark");
            name.setText(name_text);
            amount.setText("RM " + amount_text);
            datetime.setText(date_time);
            transfer_text.setText(description);
            remark_text.setText(remark);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.done_button) {
            openTngHomeActivity();
        }
    }

    public void openTngHomeActivity() {
        Intent intent = new Intent(this, TngHomeActivity.class);
        startActivity(intent);
    }
}
