package com.example.lilol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register_text;
    private Button reset_button;
    ImageView back_icon;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        reset_button = (Button)findViewById(R.id.reset_password_button);
        register_text = (TextView)findViewById(R.id.register_text);
        back_icon = (ImageView)findViewById(R.id.back_icon);
        email = (EditText)findViewById(R.id.reset_password);

        reset_button.setOnClickListener(this);
        register_text.setOnClickListener(this);
        back_icon.setOnClickListener(this);
        email.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset_password_button:
                reset_password();
                break;
            case R.id.register_text:
                openRegisterActivity();
                break;
            case R.id.back_icon:
                openMainActivity();
                break;
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void reset_password() {

        String user_email = email.getText().toString().trim();

        if(user_email.isEmpty())
        {
            email.setError("Email cannot be empty");
            email.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(user_email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Link to reset password have been sent to your email.", Toast.LENGTH_SHORT).show();
                            openLoginActivity();
                        }
                        else {
                            Toast.makeText(ForgotPasswordActivity.this, "Please enter your email again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}