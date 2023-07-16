package com.example.lilol;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class AdminHomeActivity extends AppCompatActivity {

    //TextView create_acc;
    private TextView login_button;
    private TextView register_button;
    // creating variables for our list view.
    private ListView listView;
    ImageView logout_icon;

    // creating a new array list.
    ArrayList<String> transactionArrayList;
    ArrayList<String> chat_text = new ArrayList<>();
    ArrayList<String> date_time = new ArrayList<>();

    // creating a variable for database reference.
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // initializing variables for listviews.
        listView = findViewById(R.id.ChatbotListviewActivity);
        logout_icon = (ImageView)findViewById(R.id.cancel_icon);

        logout_icon.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               FirebaseAuth.getInstance().signOut();
                                               Toast.makeText(AdminHomeActivity.this, "Log Out Successful.", Toast.LENGTH_SHORT).show();
                                               openLoginActivity();
                                           }
                                       });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ChatbotInput");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Get map of users in datasnapshot
                        collectChat((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void collectChat(Map<String,Object> chat) {

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : chat.entrySet()){

            //Get user map
            Map singleTransaction = (Map) entry.getValue();

            //Get phone field and append to list
            chat_text.add((String) singleTransaction.get("chat"));
            date_time.add((String) singleTransaction.get("date_time"));
        }

        ChatbotBaseAdapter BaseAdapter = new ChatbotBaseAdapter(getApplicationContext(), chat_text, date_time);
        listView.setAdapter(BaseAdapter);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}