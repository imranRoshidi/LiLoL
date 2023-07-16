package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TngRegisterActivity3 extends AppCompatActivity implements View.OnClickListener {

    String phone_num, link_user, user_email, pass;
    ImageView back_icon;
    EditText pin, confirm_pin;
    Button submit_button;
    double account_balance = 100.00;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_register3);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            link_user = user.getUid();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users")
                .child(link_user);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    user_email = userProfile.user_email;
                    pass = userProfile.pass;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TngRegisterActivity3.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        back_icon = (ImageView)findViewById(R.id.back_icon2);
        pin = (EditText) findViewById(R.id.pin);
        confirm_pin = (EditText) findViewById(R.id.confirm_pin);
        submit_button = (Button) findViewById(R.id.submit_button);

        back_icon.setOnClickListener(this);
        submit_button.setOnClickListener(this);

        Intent intent = this.getIntent();

        if (intent != null){

            phone_num = intent.getStringExtra("phone_num");
            phone_num = phone_num + "@gmail.com";

        }
        Toast.makeText(this, "phone num: " + phone_num, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                openTngRegisterActivity2();
                break;
            case R.id.submit_button:
                register();
                break;
        }
    }

    private void register() {
        String pin_code = pin.getText().toString().trim();
        String confirm_pin_code = confirm_pin.getText().toString().trim();
        String balance = String.format("%.2f", account_balance);

        if(pin_code.length() != 6)
        {
            pin.setError("PIN must be in 6 characters");
            pin.requestFocus();
            return;
        }
        if(confirm_pin_code.isEmpty() || !confirm_pin_code.equals(pin_code))
        {
            confirm_pin.setError("Recheck your PIN");
            confirm_pin.requestFocus();
            return;
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(phone_num, pin_code)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                TngUser user = new TngUser(phone_num, pin_code, balance, link_user);

                                FirebaseDatabase.getInstance().getReference("TngUsers")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful()){
                                                    mAuth.signInWithEmailAndPassword(user_email, pass)
                                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    if (task.isSuccessful()) {

                                                                    } else {
                                                                        Toast.makeText(TngRegisterActivity3.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                    Toast.makeText(TngRegisterActivity3.this, "Account registered and activated successfully.",
                                                            Toast.LENGTH_SHORT).show();
                                                    openTngLoginActivity();
                                                } else {
                                                    Toast.makeText(TngRegisterActivity3.this, "Registration and activation failed.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(TngRegisterActivity3.this, "Registration failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void openTngRegisterActivity2() {
        Intent intent = new Intent(this, TngRegisterActivity2.class);
        startActivity(intent);
    }

    private void openTngLoginActivity() {
        Intent intent = new Intent(this, TngLoginActivity.class);
        startActivity(intent);
    }
}
