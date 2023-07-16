package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TngReloadActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_icon;
    EditText amount;
    Button reload_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_reload);

        back_icon = (ImageView)findViewById(R.id.back_icon);
        amount = (EditText)findViewById(R.id.amount);
        reload_button = (Button)findViewById(R.id.reload_button);

        reload_button.setOnClickListener(this);
        back_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                openTngHomeActivity();
                break;
            case R.id.reload_button:
                if (amount.getText().toString().trim().isEmpty())
                {
                    amount.setError("Please enter amount to reload");
                    amount.requestFocus();
                    return;
                }
                if (Double.valueOf(amount.getText().toString().trim()) < 10.0)
                {
                    amount.setError("Minimum amount to reload is RM10");
                    amount.requestFocus();
                    return;
                }
                else
                {
                    openTngReloadActivity2();
                }
                break;
        }
    }

    private void openTngHomeActivity() {
        Intent intent = new Intent(this, TngHomeActivity.class);
        startActivity(intent);
    }

    private void openTngReloadActivity2() {
        String reload_amount = amount.getText().toString().trim();
        Intent intent = new Intent(this, TngReloadActivity2.class);
        intent.putExtra("amount", reload_amount);
        startActivity(intent);
    }


}
