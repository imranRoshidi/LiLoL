package com.example.lilol;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TngHomeActivity extends AppCompatActivity implements View.OnClickListener {

    View scan_button, pay_button, transfer_button, card_button, prepaid_button, bills_button;
    Button reload_button;
    TextView transaction_history_text, balance_amount;
    String balance, link_user, user_email, pass;
    ImageView profile_image;
    FloatingActionButton chatbot_button;
    private FirebaseAuth mAuth;
    Dialog dialog, dialog2;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_home);

        dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.activity_home_disclaimer_sign);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(true);

        dialog2.show();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_disabled_sign);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        scan_button = (View)findViewById(R.id.scan_button);
        pay_button = (View)findViewById(R.id.pay_button);
        transfer_button = (View)findViewById(R.id.transfer_button);
        card_button = (View)findViewById(R.id.card_button);
        prepaid_button = (View)findViewById(R.id.prepaid_button);
        bills_button = (View)findViewById(R.id.bills_button);
        reload_button = (Button)findViewById(R.id.reload_button);
        transaction_history_text = (TextView)findViewById(R.id.transaction_history_text);
        balance_amount = (TextView) findViewById(R.id.balance_amount);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        chatbot_button = (FloatingActionButton) findViewById(R.id.chatbot_btn);

        mAuth = FirebaseAuth.getInstance();

        scan_button.setOnClickListener(this);
        pay_button.setOnClickListener(this);
        transfer_button.setOnClickListener(this);
        card_button.setOnClickListener(this);
        reload_button.setOnClickListener(this);
        prepaid_button.setOnClickListener(this);
        bills_button.setOnClickListener(this);
        transaction_history_text.setOnClickListener(this);
        chatbot_button.setOnClickListener(this);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(TngHomeActivity.this, "Log Out Successful.", Toast.LENGTH_SHORT).show();

                openHomeActivity();

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TngUsers")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TngUser userProfile = snapshot.getValue(TngUser.class);

                if(userProfile != null){
                    balance = userProfile.balance;
                    link_user = userProfile.link_user;

                    balance_amount.setText("RM " + balance);
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
                        Toast.makeText(TngHomeActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TngHomeActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
//        Toast.makeText(HomeActivity.this, "Back Button", Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scan_button:
                openTngScanActivity();
                break;
            case R.id.pay_button:
                openTngPayActivity();
                break;
            case R.id.transfer_button:
                openTngTransferActivity();
                break;
            case R.id.bills_button:
                openTngBillsActivity();
                break;
            case R.id.prepaid_button:
                dialog.show();
                break;
            case R.id.card_button:
                dialog.show();
                break;
            case R.id.reload_button:
                openTngReloadActivity();
                break;
            case R.id.transaction_history_text:
                openTngTransactionHistoryActivity();
                break;
            case R.id.chatbot_btn:
                openChatbotActivity();
                break;

        }
    }

    private void openHomeActivity() {
        mAuth.signInWithEmailAndPassword(user_email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(TngHomeActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TngHomeActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(TngHomeActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void openTngScanActivity() {
        Intent intent = new Intent(this, TngScanActivity.class);
        startActivity(intent);
    }

    public void openTngPayActivity() {
        Intent intent = new Intent(this, TngPayActivity.class);
        intent.putExtra("balance_amount", balance);
        startActivity(intent);
    }

    public void openTngTransferActivity() {
        Intent intent = new Intent(this, TngTransferActivity.class);
        startActivity(intent);
    }

    public void openTngCardActivity() {
        Intent intent = new Intent(this, TngHomeActivity.class);
        startActivity(intent);
    }

    public void openTngReloadActivity() {
        Intent intent = new Intent(this, TngReloadActivity.class);
        startActivity(intent);
    }

    public void openTngTransactionHistoryActivity() {
        Intent intent = new Intent(this, TngTransactionHistoryActivity.class);
        startActivity(intent);
    }

    public void openTngBillsActivity() {
        Intent intent = new Intent(this, TngBillsActivity.class);
        intent.putExtra("balance_amount", balance);
        startActivity(intent);
    }

    private void openChatbotActivity() {
        Intent intent = new Intent(this, ChatbotActivity.class);
        startActivity(intent);
    }

}
