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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView login_text;
    private Button register_button;
    ImageView back_icon;
    private FirebaseAuth mAuth;
    private EditText full_name, username, phone_num, email, password, confirm_password;
    private String attempt_category [] = {"login", "pin", "transfer_pay"};
    String success = "0";
    String fail = "0";
/*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            openHomeActivity();
        }
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login_text = (TextView) findViewById(R.id.login_text);
        register_button = (Button)findViewById(R.id.register_button);
        back_icon = (ImageView)findViewById(R.id.back_icon);
        full_name = (EditText)findViewById(R.id.full_name);
        username = (EditText)findViewById(R.id.username);
        phone_num = (EditText)findViewById(R.id.phone_num);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        confirm_password = (EditText)findViewById(R.id.confirm_password);
        mAuth = FirebaseAuth.getInstance();

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
                openLoginActivity();
                break;
            case R.id.back_icon:
                openMainActivity();
                break;
        }
    }

    private void register()
    {
        String fn = full_name.getText().toString().trim();
        String un = username.getText().toString().trim();
        String phone = phone_num.getText().toString().trim();
        String user_email = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirm_pass = confirm_password.getText().toString().trim();

        if(fn.isEmpty())
        {
            full_name.setError("Full name cannot be empty");
            full_name.requestFocus();
            return;
        }
        if(un.isEmpty())
        {
            username.setError("Username cannot be empty");
            username.requestFocus();
            return;
        }
        if(phone.isEmpty())
        {
            phone_num.setError("Phone number cannot be empty");
            phone_num.requestFocus();
            return;
        }
        if(user_email.isEmpty())
        {
            email.setError("Email cannot be empty");
            email.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            password.setError("Password cannot be empty");
            password.requestFocus();
            return;
        }
        if(confirm_pass.isEmpty() || !confirm_pass.equals(pass))
        {
            confirm_password.setError("Recheck your password");
            confirm_password.requestFocus();
            return;
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(user_email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(fn, un, phone, user_email, pass);

                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                myRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        for (int i=0; i<attempt_category.length; i++)
                                        {
                                            SimulatorAttemptCategory attempt = new SimulatorAttemptCategory(success, fail);

                                            myRef.child("attempt_category").child(attempt_category[i])
                                                    .setValue(attempt).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if(task.isSuccessful()){
                                                                Toast.makeText(RegisterActivity.this, "Account registered and activated successfully.",
                                                                        Toast.LENGTH_SHORT).show();
                                                                openLoginActivity();
                                                            } else {
                                                                Toast.makeText(RegisterActivity.this, "Registration failed.",
                                                                        Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                        /*Toast.makeText(RegisterActivity.this, "Account registered and activated successfully. (1/2)",
                                            Toast.LENGTH_SHORT).show();*/
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registration failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Registration failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
