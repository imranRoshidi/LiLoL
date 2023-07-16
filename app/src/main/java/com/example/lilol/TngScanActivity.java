package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class TngScanActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_icon;
    Button scan_from_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scan_from_gallery = (Button)findViewById(R.id.scan_button);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), TngScanActivity2.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_icon:
                openTngHomeActivity();
                break;
            /*case R.id.scan_button:
                openGallery();
                break;*/
    }
}

    private void openTngHomeActivity() {
        Intent intent = new Intent(this, TngHomeActivity.class);
        startActivity(intent);
    }

    private void openGallery() {
        Intent intent = new Intent(this, TngHomeActivity.class);
        startActivity(intent);
    }
}
