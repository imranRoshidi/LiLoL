package com.example.lilol;

import android.app.Dialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TngLoginActivity2 extends AppCompatActivity implements View.OnClickListener {

    String phone_num, keyword, fail_login, success_login;
    TextView register_text;
    ImageView back_icon;
    Button login_button;
    EditText pin;
    int error_count = 0, fail_login_amt, success_login_amt;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_login2);

        back_icon = (ImageView)findViewById(R.id.home_icon);
        login_button = (Button) findViewById(R.id.login_button);
        pin = (EditText) findViewById(R.id.pin_number);
        register_text = (TextView) findViewById(R.id.register_text);

        login_button.setOnClickListener(this);
        back_icon.setOnClickListener(this);

        Intent intent = this.getIntent();

        if (intent != null){

            phone_num = intent.getStringExtra("phone_num");
            phone_num = phone_num + "@gmail.com";
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database.getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("attempt_category").child("login");

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SimulatorAttemptCategory userAttempt = snapshot.getValue(SimulatorAttemptCategory.class);

                if(userAttempt != null){
                    success_login = userAttempt.success;
                    fail_login = userAttempt.fail;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TngLoginActivity2.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin_code = pin.getText().toString().trim();
                if(pin_code.length() != 6)
                {
                    pin.setError("PIN must be in 6 characters");
                    pin.requestFocus();
                    error_count += 1;
                    if(error_count == 3) {
                        fail_login_amt = Integer.parseInt(fail_login) + 1;
                        fail_login = String.valueOf(fail_login_amt);
                        updateFailedLoginAttemptsData(fail_login);
                        keyword = "login";
                        error_count = 0;
                        pin.setText(null);
                        openChatbotActivity();
                    }
                    return;
                }
                else
                {
                    String link_user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    mAuth.signInWithEmailAndPassword(phone_num, pin_code)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        success_login_amt = Integer.parseInt(success_login) + 1;
                                        success_login = String.valueOf(success_login_amt);
                                        updateSuccessfulLoginAttemptsData(success_login, link_user);

                                        Toast.makeText(TngLoginActivity2.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), TngHomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(TngLoginActivity2.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        error_count += 1;
                                        if(error_count == 3) {
                                            fail_login_amt = Integer.parseInt(fail_login) + 1;
                                            fail_login = String.valueOf(fail_login_amt);
                                            updateFailedLoginAttemptsData(fail_login);
                                            keyword = "login";
                                            error_count = 0;
                                            pin.setText(null);
                                            openChatbotActivity();
                                        }
                                    }
                                }
                            });
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_icon:
                openTngLoginActivity();
                break;
            case R.id.register_text:
                openTngRegisterActivity();
                break;
        }
    }

    private void login() {

    }

    private void openTngLoginActivity() {
        Intent intent = new Intent(this, TngLoginActivity.class);
        startActivity(intent);
    }

    private void openTngRegisterActivity() {
        Intent intent = new Intent(this, TngRegisterActivity.class);
        startActivity(intent);
    }

    private void openChatbotActivity() {
        Intent intent = new Intent(this, ChatbotActivity.class);
        intent.putExtra("keyword", keyword);
        startActivity(intent);
    }

    private void updateFailedLoginAttemptsData(String fail_value) {

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("attempt_category").child("login").child("fail")
                .setValue(fail_value).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void updateSuccessfulLoginAttemptsData(String success_value, String link_user) {

        FirebaseDatabase.getInstance().getReference("Users")
                .child(link_user)
                .child("attempt_category").child("login").child("success")
                .setValue(success_value).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

}
