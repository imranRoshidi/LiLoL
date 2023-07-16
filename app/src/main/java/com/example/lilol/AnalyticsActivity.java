package com.example.lilol;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AnalyticsActivity extends AppCompatActivity implements View.OnClickListener {

    String key;
    ImageView back_icon;
    TextView dialog_text1, dialog_text2;
    Dialog dialog2;
    Float success_value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        Spinner spinnerCategory = findViewById(R.id.spinner);
        back_icon = findViewById(R.id.cancel_icon);

        dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.activity_home_disclaimer_sign);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(true);

        dialog_text1 = dialog2.findViewById(R.id.textView);
        dialog_text2 = dialog2.findViewById(R.id.textView2);

        back_icon.setOnClickListener(this);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                openHomeActivity();
            }
        });

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.analytics_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                key = spinnerCategory.getSelectedItem().toString().trim().toLowerCase();
//                Toast.makeText(AnalyticsActivity.this, "Key: " + key, Toast.LENGTH_LONG).show();
                getData(key);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AnalyticsActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getData (String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("attempt_category")
                .child(key);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SimulatorAttemptCategory userAttempt = snapshot.getValue(SimulatorAttemptCategory.class);

                ArrayList<PieEntry> entries = new ArrayList<>();

                if(userAttempt != null){
                    String success_pin = userAttempt.success;
                    String fail_pin = userAttempt.fail;
                    entries.add(new PieEntry(Float.valueOf(success_pin), "Successful"));
                    entries.add(new PieEntry(Float.valueOf(fail_pin), "Failed"));
//                    Toast.makeText(AnalyticsActivity.this, "Success: " + success_pin, Toast.LENGTH_LONG).show();
//                    Toast.makeText(AnalyticsActivity.this, "Fail: " + fail_pin, Toast.LENGTH_LONG).show();

                    showChart(entries);

//                    success_value = (Float.valueOf(success_pin) / (Float.valueOf(success_pin) + Float.valueOf(fail_pin))) * 100;
//                    if (success_value >= 80.0)
//                    {
//                        dialog_text1.setText("CONGRATULATIONS");
//                        dialog_text2.setText("You have mastered " + key + " attempts");
//                        dialog2.show();
//                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AnalyticsActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void showChart (ArrayList<PieEntry> entries) {
        PieChart pieChart = findViewById(R.id.barChart);

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(Color.GREEN, Color.RED);
        dataSet.setValueTextSize(14);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
// ...

// Create a PieData object and set it to the chart
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

// Set additional configurations for the chart
        pieChart.getDescription().setEnabled(false); // Disable the description
        pieChart.setUsePercentValues(true); // Display values as percentages
        pieChart.setCenterText(key.toUpperCase() + " ATTEMPTS (%)");

// ...

// Refresh the chart
        pieChart.invalidate();
    }

    @Override
    public void onClick(View view) {

    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
