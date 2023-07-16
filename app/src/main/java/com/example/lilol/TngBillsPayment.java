package com.example.lilol;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class TngBillsPayment extends AppCompatActivity implements View.OnClickListener {

    ImageView icon, cancel_icon;
    TextView name;
    Button next_button, submit_button;
    EditText amount_input;
    Dialog dialog, chatbot;
    EditText pin;
    int success_pin_amt, fail_pin_amt, success_transfer_amt, fail_transfer_amt;
    String amount, pin_code, name_text, title, type, date_time, status, payee_receiver, keyword, success_pin, fail_pin, success_transfer, fail_transfer, link_user;
    int error_count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_postpaid_payment);

        name = (TextView)findViewById(R.id.text);
        icon = (ImageView)findViewById(R.id.icon);
        next_button = (Button)findViewById(R.id.next_button);
        amount_input  = (EditText)findViewById(R.id.amount);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_tng_pin);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        chatbot = new Dialog(this);
        chatbot.setContentView(R.layout.activity_chatbot);
        chatbot.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        chatbot.setCancelable(true);

        pin = (EditText)dialog.findViewById(R.id.pin_number);
        submit_button = (Button)dialog.findViewById(R.id.submit_button);
        cancel_icon = (ImageView)dialog.findViewById(R.id.cancel_icon);

        next_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        cancel_icon.setOnClickListener(this);

        Intent intent = this.getIntent();

        if (intent != null){

            name_text = intent.getStringExtra("name");
            //amount = intent.getStringExtra("balance_amount");
            //amount_format = Double.valueOf(amount);
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                int icon_image = bundle.getInt("image");
                icon.setImageResource(icon_image);
            }

            //int postpaidIcon = intent.getIntExtra("postpaidImage", R.drawable.group_12335);

            //postpaid_icon.setImageResource(postpaidIcon);
            name.setText(name_text);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TngUsers")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TngUser userProfile = snapshot.getValue(TngUser.class);

                if(userProfile != null){
                    amount = userProfile.balance;
                    pin_code =  userProfile.pin_code;
                    link_user = userProfile.link_user;
                }

                DatabaseReference myRef2 = database.getReference("Users")
                        .child(userProfile.link_user).child("attempt_category").child("transfer_pay");

                myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SimulatorAttemptCategory userAttempt = snapshot.getValue(SimulatorAttemptCategory.class);

                        if(userAttempt != null){
                            success_transfer = userAttempt.success;
                            fail_transfer = userAttempt.fail;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TngBillsPayment.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
                    }
                });

                DatabaseReference myRef3 = database.getReference("Users")
                        .child(userProfile.link_user).child("attempt_category").child("pin");

                myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SimulatorAttemptCategory userAttempt = snapshot.getValue(SimulatorAttemptCategory.class);

                        if(userAttempt != null){
                            success_pin = userAttempt.success;
                            fail_pin = userAttempt.fail;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TngBillsPayment.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
                    }
                });

                Toast.makeText(TngBillsPayment.this, "link_user: " + link_user , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TngBillsPayment.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_button:
                if (amount_input.getText().toString().trim().isEmpty())
                {
                    amount_input.setError("Please enter an amount");
                    amount_input.requestFocus();
                    return;
                }
                if (Double.valueOf(amount_input.getText().toString().trim()) > Double.valueOf(amount))
                {
                    amount_input.setError("Amount you entered exceeded your wallet balance");
                    amount_input.requestFocus();
                    error_count += 1;
                    if(error_count == 3) {
                        fail_transfer_amt = Integer.parseInt(fail_transfer) + 1;
                        fail_transfer = String.valueOf(fail_transfer_amt);
                        updateFailedTransferAttemptsData(fail_transfer);
                        keyword = "transfer";
                        error_count = 0;
                        amount_input.setText(null);
                        openChatbotActivity();
                    }
                    return;
                }
                else
                    success_transfer_amt = Integer.parseInt(success_transfer) + 1;
                    success_transfer = String.valueOf(success_transfer_amt);
                    updateSuccessfulTransferAttemptsData(success_transfer);
                    dialog.show();
                break;
            case R.id.back_icon:
                openTngBillsActivity2();
                break;
            case R.id.cancel_icon:
                dialog.dismiss();
                break;
            case R.id.submit_button:
                if (pin.length() != 6 || pin == null)
                {
                    pin.setError("Please enter your 6 characters PIN");
                    pin.requestFocus();
                    error_count += 1;
                    Toast.makeText(this, "error: " + error_count, Toast.LENGTH_SHORT).show();
                    if(error_count == 3) {
                        dialog.dismiss();
                        fail_pin_amt = Integer.parseInt(fail_pin) + 1;
                        fail_pin = String.valueOf(fail_pin_amt);
                        updateFailedPinAttemptsData(fail_pin);
                        keyword = "pin";
                        error_count = 0;
                        pin.setText(null);
                        openChatbotActivity();
                    }
                    return;
                }
                if (!pin.getText().toString().trim().equals(pin_code))
                {
                    pin.setError("Wrong pin");
                    pin.requestFocus();
                    error_count += 1;
                    Toast.makeText(this, "error: " + error_count, Toast.LENGTH_SHORT).show();
                    if(error_count == 3) {
                        dialog.dismiss();
                        fail_pin_amt = Integer.parseInt(fail_pin) + 1;
                        fail_pin = String.valueOf(fail_pin_amt);
                        updateFailedPinAttemptsData(fail_pin);
                        keyword = "pin";
                        error_count = 0;
                        pin.setText(null);
                        openChatbotActivity();
                    }
                    return;
                }
                else
                    success_pin_amt = Integer.parseInt(success_pin) + 1;
                    success_pin = String.valueOf(success_pin_amt);
                    updateSuccessfulPinAttemptsData(success_pin);
                    processTransfer();
                break;

        }
    }

    private void processTransfer() {
        Double balance_amount = Double.valueOf(amount) - Double.valueOf(amount_input.getText().toString().trim());
        String new_balance = String.format("%.2f", balance_amount);

        FirebaseDatabase.getInstance().getReference("TngUsers")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("balance")
                .setValue(new_balance).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(TngBillsPayment.this, "Updated successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TngBillsPayment.this, "Update failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        title = "Payment to " + name_text;
        type = "Payment";
        date_time = String.valueOf(Calendar.getInstance().getTime());
        status = "Successful";
        payee_receiver = name_text;
        amount = amount_input.getText().toString().trim();
        long timestamp = System.currentTimeMillis();

        TngTransaction tngTransaction = new TngTransaction(title, type, date_time, status, payee_receiver, amount, timestamp);

        FirebaseDatabase.getInstance().getReference("TngTransactions")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push()
                .setValue(tngTransaction).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(TngBillsPayment.this, "Transaction successful.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TngBillsPayment.this, "Transaction failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        openSuccessfulPaymentActivity();
    }

    private void openTngBillsActivity2() {
        Intent intent = new Intent(this, TngBillsActivity2.class);
        startActivity(intent);
    }

    private void openSuccessfulPaymentActivity() {
        Date currentTime = Calendar.getInstance().getTime();

        Intent intent = new Intent(this, SuccessfulPaymentActivity.class);
        intent.putExtra("name", name_text);
        intent.putExtra("amount", amount_input.getText().toString().trim());
        intent.putExtra("datetime", String.valueOf(currentTime));
        intent.putExtra("description", "Transferred");
        //intent.putExtra("remark", payment_details.getText().toString().trim());
        startActivity(intent);
    }

    private void openChatbotActivity() {
        Intent intent = new Intent(this, ChatbotActivity.class);
        intent.putExtra("keyword", keyword);
        startActivity(intent);
    }

    private void updateFailedTransferAttemptsData(String fail_value) {

        FirebaseDatabase.getInstance().getReference("Users")
                .child(link_user)
                .child("attempt_category").child("transfer_pay").child("fail")
                .setValue(fail_value).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void updateSuccessfulTransferAttemptsData(String success_value) {

        FirebaseDatabase.getInstance().getReference("Users")
                .child(link_user)
                .child("attempt_category").child("transfer_pay").child("success")
                .setValue(success_value).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void updateSuccessfulPinAttemptsData(String success_value) {

        FirebaseDatabase.getInstance().getReference("Users")
                .child(link_user)
                .child("attempt_category").child("pin").child("success")
                .setValue(success_value).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void updateFailedPinAttemptsData(String fail_value) {

        FirebaseDatabase.getInstance().getReference("Users")
                .child(link_user)
                .child("attempt_category").child("pin").child("fail")
                .setValue(fail_value).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

}
