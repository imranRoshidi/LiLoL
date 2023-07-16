package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TngTransferActivity extends AppCompatActivity implements View.OnClickListener {

    Button next_button;
    ImageView back_icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_transfer);

        next_button = (Button) findViewById(R.id.next_button);
        back_icon = (ImageView) findViewById(R.id.back_icon);

        next_button.setOnClickListener(this);
        back_icon.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_button:
                openTngTransferActivity2();
                break;
            case R.id.back_icon:
                openTngHomeActivity();
                break;
        }
    }

    private void openTngHomeActivity() {
        Intent intent = new Intent(this, TngHomeActivity.class);
        startActivity(intent);
    }

    private void openTngTransferActivity2() {
        Intent intent = new Intent(this, TngTransferActivity2.class);
        startActivity(intent);
    }
}
