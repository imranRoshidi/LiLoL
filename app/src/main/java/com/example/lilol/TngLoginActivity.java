package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TngLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText phone_num, pin;
    Button login_button;
    TextView register_text;
    ImageView home_icon;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_login);

        login_button = (Button) findViewById(R.id.login_button);
        phone_num = (EditText) findViewById(R.id.phone_num);
        register_text = (TextView) findViewById(R.id.register_text);
        home_icon = (ImageView) findViewById(R.id.home_icon);

        login_button.setOnClickListener(this);
        phone_num.setOnClickListener(this);
        register_text.setOnClickListener(this);
        home_icon.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                if (phone_num.getText().toString().trim().isEmpty())
                {
                    phone_num.setError("Please enter your phone number");
                    phone_num.requestFocus();
                    return;
                } else
                {
                    openTngLoginActivity2();
                }
                break;
            /*case R.id.phone_num:
                login();
                break;*/
            case R.id.register_text:
                openTngRegisterActivity();
                break;
            case R.id.home_icon:
                openSimulatorActivity();
                break;

        }
    }

    public void openSimulatorActivity() {
        Intent intent = new Intent(this, SimulatorActivity.class);
        startActivity(intent);
    }

    public void openTngRegisterActivity() {
        Intent intent = new Intent(this, TngRegisterActivity.class);
        startActivity(intent);
    }

    public void openTngHomeActivity() {
        Intent intent = new Intent(this, TngHomeActivity.class);
        startActivity(intent);
    }

    public void openTngLoginActivity2() {
        String num = "+60" + phone_num.getText().toString().trim();
        Intent intent = new Intent(this, TngLoginActivity2.class);
        intent.putExtra("phone_num", num);
        startActivity(intent);

    }
}
