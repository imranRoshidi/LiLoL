package com.example.lilol;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class TngTransactionHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    // creating variables for our list view.
    private ListView listView;

    // creating a new array list.
    ArrayList<String> transactionArrayList;
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();
    ArrayList<String> date_time = new ArrayList<>();
    ArrayList<String> amount = new ArrayList<>();

    // creating a variable for database reference.
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng_transactionhistory);

        // initializing variables for listviews.
        listView = findViewById(R.id.TngTransactionHistoryListviewActivity);

//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("TngTransactions")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        Query query = ref.orderByChild("title");
//
//        query.addValueEventListener(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        //Get map of users in datasnapshot
//                        collectTransactionHistory((Map<String,Object>) dataSnapshot.getValue());
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        //handle databaseError
//                    }
//                });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("TngTransactions");
        Query query = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByChild("timestamp");
        query.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            Log.d("info", String.valueOf(snapshot.getValue()));
                            //Get phone field and append to list
                            title.add(snapshot.child("title").getValue(String.class));
                            type.add(snapshot.child("type").getValue(String.class));
                            date_time.add(snapshot.child("date_time").getValue(String.class));
                            amount.add(snapshot.child("amount").getValue(String.class));
                        }
                        Collections.reverse(title);
                        Collections.reverse(type);
                        Collections.reverse(date_time);
                        Collections.reverse(amount);
                        TngTransactionBaseAdapter BaseAdapter = new TngTransactionBaseAdapter(getApplicationContext(), title, type, date_time, amount);
                        listView.setAdapter(BaseAdapter);
                        //Get map of users in datasnapshot
//                        collectTransactionHistory((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        // initializing our array list
        //transactionArrayList = new ArrayList<String>();

        // calling a method to get data from
        // Firebase and set data to list view
        //initializeListView();

    }

    private void initializeListView() {
        // creating a new array adapter for our list view.
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, transactionArrayList);

        // below line is used for getting reference
        // of our Firebase Database.


        //Get datasnapshot at your "users" root node


        /*reference =  FirebaseDatabase.getInstance().getReference("TngTransactions")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // in below line we are calling method for add child event
        // listener to get the child of our database.
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added to
                // our data base and after adding new child
                // we are adding that item inside our array list and
                // notifying our adapter that the data in adapter is changed.
                transactionArrayList.add(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when the new child is added.
                // when the new child is added to our list we will be
                // notifying our adapter that data has changed.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // below method is called when we remove a child from our database.
                // inside this method we are removing the child from our array list
                // by comparing with it's value.
                // after removing the data we are notifying our adapter that the
                // data has been changed.
                transactionArrayList.remove(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when we move our
                // child in our database.
                // in our code we are note moving any child.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // this method is called when we get any
                // error from Firebase with error.
            }
        });*/

        // below line is used for setting
        // an adapter to our list view.

    }

    private void collectTransactionHistory(Map<String,Object> tngTransaction) {

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : tngTransaction.entrySet()){

            //Get user map
            Map singleTransaction = (Map) entry.getValue();

            //Get phone field and append to list
            title.add((String) singleTransaction.get("title"));
            type.add((String) singleTransaction.get("type"));
            date_time.add((String) singleTransaction.get("date_time"));
            amount.add((String) singleTransaction.get("amount"));
        }

//        TngTransactionBaseAdapter BaseAdapter = new TngTransactionBaseAdapter(getApplicationContext(), title, type, date_time, amount);
//        listView.setAdapter(BaseAdapter);
    }

    @Override
    public void onClick(View view) {

    }
}
