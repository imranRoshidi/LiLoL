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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register_text, forgot_password_text, admin_text;
    private Button login_button;
    ImageView back_icon;
    private FirebaseAuth mAuth;
    private EditText email_username, password;

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        login_button = (Button)findViewById(R.id.login_button);
        register_text = (TextView)findViewById(R.id.register_text);
        admin_text = (TextView)findViewById(R.id.admin_text);
        forgot_password_text = (TextView)findViewById(R.id.forgot_password_text);
        back_icon = (ImageView)findViewById(R.id.back_icon);
        email_username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();


        login_button.setOnClickListener(this);
        register_text.setOnClickListener(this);
        admin_text.setOnClickListener(this);
        forgot_password_text.setOnClickListener(this);
        back_icon.setOnClickListener(this);
        login_button.setOnClickListener(this);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email = email_username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(user_email.isEmpty())
                {
                    email_username.setError("Email cannot be empty");
                    email_username.requestFocus();
                    return;
                }
                if(pass.isEmpty())
                {
                    password.setError("Password cannot be empty");
                    password.requestFocus();
                    return;
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(user_email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                        openHomeActivity();
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        openMainActivity();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgot_password_text:
                openForgotPasswordActivity();
                break;
            case R.id.admin_text:
                openAdminLoginActivity();
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

    public void openForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openAdminLoginActivity() {
        Intent intent = new Intent(this, AdminLoginActivity.class);
        startActivity(intent);
    }
}