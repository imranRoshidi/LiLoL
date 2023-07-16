package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.Map;

public class TngReloadActivity2<auth> extends AppCompatActivity implements View.OnClickListener {

    String bankList[] = {"Bank Kerjasama Rakyat", "CIMB Bank", "RHB Bank", "Hong Leong Bank", "Affin Bank", "Alliance Bank", "Agro Bank", "Ambank", "Bank Islam", "Bank Muamalat",
                        "BSN", "HSBC", "OCBC Bank", "Public Bank", "Standard Chartered Bank", "UOB"};
    int bankImages[] = {R.drawable.bankrakyat, R.drawable.cimb, R.drawable.rhb, R.drawable.hongleong, R.drawable.affin, R.drawable.alliance,
                        R.drawable.agrobank, R.drawable.ambank, R.drawable.bankislam, R.drawable.muamalat, R.drawable.bsn, R.drawable.hsbc, R.drawable.ocbc,
                        R.drawable.public_bank, R.drawable.standardchartered, R.drawable.uob};

    TextView card_text, online_banking_text, reload_amount;
    Double reload_amt;
    String amount, balance, phone_num, pin_code, title, type, date_time, status, payee_receiver;
    String dateTime;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_reload2);

        listView = (ListView)findViewById(R.id.TngBillsPostpaidListviewActivity);
        online_banking_text = (TextView) findViewById(R.id.online_banking_text);
        reload_amount = (TextView) findViewById(R.id.reload_amount);

        online_banking_text.setOnClickListener(this);

        openOnlineBankingListview();

        Intent intent = this.getIntent();

        if (intent != null){

            amount = intent.getStringExtra("amount");
            Double amount_format = Double.valueOf(amount);
            amount = String.format("%.2f", amount_format);
            reload_amount.setText("RM " + amount);
        }

        Date currentTime = Calendar.getInstance().getTime();
        dateTime = String.valueOf(currentTime);
        Toast.makeText(TngReloadActivity2.this, "Date & time: " + dateTime, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.card_text:
                openPostpaidListview();
                break;*/
            case R.id.online_banking_text:
                openOnlineBankingListview();
                break;
        }
    }

    private void openOnlineBankingListview() {
        BillsBaseAdapter utilitiesBaseAdapter = new BillsBaseAdapter(getApplicationContext(), bankList, bankImages);
        listView.setAdapter(utilitiesBaseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("TngUsers")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
               /* DatabaseReference transRef = database.getReference("TngTransactions")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());*/

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TngUser userProfile = snapshot.getValue(TngUser.class);

                        if(userProfile != null){
                            balance = userProfile.balance;
                            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            //String user_id = String.valueOf(user);

                            reload_amt = Double.valueOf(balance) + Double.valueOf(amount);
                            String new_balance = String.format("%.2f", reload_amt);

                            FirebaseDatabase.getInstance().getReference("TngUsers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("balance")
                                    .setValue(new_balance).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(TngReloadActivity2.this, "Updated successfully", Toast.LENGTH_LONG).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(TngReloadActivity2.this, "Update failed", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                            title = "Reload";
                            type = "Reload";
                            date_time = dateTime;
                            status = "Successful";
                            payee_receiver = bankList[i];
                            long timestamp = System.currentTimeMillis();

                            TngTransaction tngTransaction = new TngTransaction(title, type, date_time, status, payee_receiver, amount, timestamp);

                            FirebaseDatabase.getInstance().getReference("TngTransactions")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push()
                                    .setValue(tngTransaction).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(TngReloadActivity2.this, "Transaction successful.",
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(TngReloadActivity2.this, "Transaction failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                            Toast.makeText(TngReloadActivity2.this, "id: " + user, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TngReloadActivity2.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
                    }
                });

                Intent intent = new Intent(TngReloadActivity2.this, SuccessfulPaymentActivity.class);
                intent.putExtra("name", bankList[i]);
                intent.putExtra("amount", amount);
                intent.putExtra("datetime", dateTime);
                intent.putExtra("description", "Reloaded");
                //intent.putExtra("image", bankImages[i]);
                startActivity(intent);
            }
        });
    }

    private void updateData(String phone_num, String pin_code, String balance) {

    }

    public void openTngScanActivity() {
        Intent intent = new Intent(this, TngScanActivity.class);
        startActivity(intent);
    }

}
