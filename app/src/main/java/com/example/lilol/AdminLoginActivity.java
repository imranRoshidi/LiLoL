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

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_admin_login);

        login_button = (Button)findViewById(R.id.login_button);
        back_icon = (ImageView)findViewById(R.id.back_icon);
        email_username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();


        login_button.setOnClickListener(this);
        back_icon.setOnClickListener(this);
        login_button.setOnClickListener(this);

        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String user_email = "admin@gmail.com";
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
                                        Toast.makeText(AdminLoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                        openAdminHomeActivity();
                                        finish();
                                    } else {
                                        Toast.makeText(AdminLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
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
            case R.id.back_icon:
                openMainActivity();
                break;
        }
    }


    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAdminHomeActivity() {
        Intent intent = new Intent(this, AdminHomeActivity.class);
        startActivity(intent);
    }
}