package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TngRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText phone_num;
    Button register_button;
    TextView login_text;
    ImageView back_icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_register);

        register_button = (Button)findViewById(R.id.register_button);
        phone_num = (EditText)findViewById(R.id.phone_num);
        login_text = (TextView)findViewById(R.id.login_text);
        back_icon = (ImageView)findViewById(R.id.back_icon2);

        register_button.setOnClickListener(this);
        login_text.setOnClickListener(this);
        back_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_button:
                register();
                break;
            case R.id.login_text:
                openTngLoginActivity();
                break;
            case R.id.back_icon2:
                openTngLoginActivity();
                break;

        }
    }

    private void register() {
        String num = "+60" + phone_num.getText().toString().trim();

        Intent intent = new Intent(this, TngRegisterActivity3.class);
        intent.putExtra("phone_num", num);
        startActivity(intent);
    }


    private void openTngLoginActivity() {
        Intent intent = new Intent(this, TngLoginActivity.class);
        startActivity(intent);
    }
}
