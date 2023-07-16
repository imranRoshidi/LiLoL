package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lilol.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class TngBillsActivity2 extends AppCompatActivity implements View.OnClickListener {

    String postpaidList[] = {"Maxis", "Celcom", "Digi", "U Mobile", "redONE", "Yes"};
    int postpaidImages[] = {R.drawable.maxis, R.drawable.celcom, R.drawable.digi, R.drawable.umobile, R.drawable.redone, R.drawable.yes};

    String utilitiesList[] = {"Tenaga Nasional Berhad", "Indah Water", "Sarawak Energy", "Sabah Electricity", "Air Selangor", "Air Johor", "Air Kedah", "Air Perak", "Air Melaka",
                                "Air N.Sembilan", "Air Pulau Pinang", "Air Pahang", "Air Terengganu", "Air Kelantan", "Air Perlis", "Air Kuching", "Air Labuan", "Nur Distribution"};
    int utilitiesImages[] = {R.drawable.tnb, R.drawable.indahwater, R.drawable.sarawakenergy, R.drawable.sabahelectricity, R.drawable.air_selangor, R.drawable.air_johor,
                            R.drawable.air_kedah, R.drawable.air_perak, R.drawable.air_melaka, R.drawable.air_n9, R.drawable.air_penang, R.drawable.air_pahang,
                            R.drawable.air_terengganu, R.drawable.air_kelantan, R.drawable.air_perlis, R.drawable.air_kuching, R.drawable.air_labuan, R.drawable.nur_distribution};

    TextView postpaid_text, utilities_text, broadband_text, entertainment_text, loans_text, localcouncil_text;
    String category, amount;
    //int arrow_icon = R.drawable.ic_baseline_arrow_back_24;

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_bills2);

        postpaid_text = (TextView)findViewById(R.id.postpaid_text);
        utilities_text = (TextView)findViewById(R.id.utilities_text);
        broadband_text = (TextView)findViewById(R.id.broadband_text);
        entertainment_text = (TextView)findViewById(R.id.entertainment_text);
        loans_text = (TextView)findViewById(R.id.loans_text);
        localcouncil_text = (TextView)findViewById(R.id.localcouncil_text);
        listView = (ListView)findViewById(R.id.TngBillsPostpaidListviewActivity);

        postpaid_text.setOnClickListener(this);
        utilities_text.setOnClickListener(this);
        broadband_text.setOnClickListener(this);
        entertainment_text.setOnClickListener(this);
        loans_text.setOnClickListener(this);
        localcouncil_text.setOnClickListener(this);

        Intent intent = this.getIntent();

        if (intent != null){

            category = intent.getStringExtra("category");
            amount = intent.getStringExtra("balance_amount");

            switch (category) {
                case "postpaid":
                    openPostpaidListview();
                    break;
                case "utilities":
                    openUtilitiesListview();
                    break;
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.postpaid_text:
                openPostpaidListview();
                break;
            case R.id.utilities_text:
                openUtilitiesListview();
                break;
            case R.id.back_icon:
                openTngBillsActivity();
                break;
            /*case R.id.broadband_text:
                openBroadbandListview();
                break;
            case R.id.entertainment_text:
                openEntertainmentListview();
                break;
            case R.id.loans_text:
                openLoansListview();
                break;
            case R.id.localcouncil_text:
                openLocalCouncilListview();
                break;*/
        }
    }

    private void openBroadbandListview() {
    }

    private void openLoansListview() {
    }

    private void openEntertainmentListview() {
    }

    private void openLocalCouncilListview() {
    }

    private void openUtilitiesListview() {
        BillsBaseAdapter utilitiesBaseAdapter = new BillsBaseAdapter(getApplicationContext(), utilitiesList, utilitiesImages);
        listView.setAdapter(utilitiesBaseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TngBillsActivity2.this, TngBillsPayment.class);
                intent.putExtra("name", utilitiesList[i]);
                intent.putExtra("image", utilitiesImages[i]);
                intent.putExtra("balance_amount", amount);
                startActivity(intent);
            }
        });
    }

    private void openPostpaidListview() {
        BillsBaseAdapter postpaidBaseAdapter = new BillsBaseAdapter(getApplicationContext(), postpaidList, postpaidImages);
        listView.setAdapter(postpaidBaseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TngBillsActivity2.this, TngBillsPayment.class);
                intent.putExtra("name", postpaidList[i]);
                intent.putExtra("image", postpaidImages[i]);
                intent.putExtra("balance_amount", amount);
                startActivity(intent);
            }
        });
    }

    private void openTngBillsActivity() {
        Intent intent = new Intent(this, TngBillsActivity.class);
        startActivity(intent);
    }

}
