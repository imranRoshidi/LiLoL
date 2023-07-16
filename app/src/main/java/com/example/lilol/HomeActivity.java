package com.example.lilol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button simulator_button;
    private Button analytics_button;
    private Button chatbot_button;
    ImageView logout_icon;
    TextView hello_text;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        simulator_button = (Button)findViewById(R.id.simulator_btn);
        analytics_button = (Button)findViewById(R.id.analytics_btn);
        chatbot_button = (Button)findViewById(R.id.chatbot_btn);
        logout_icon = (ImageView)findViewById(R.id.logout_icon);
        hello_text = (TextView)findViewById(R.id.hello_text);
        /*user = auth.getCurrentUser();

        if (user == null){
            openLoginActivity();
        }*/

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String username = userProfile.un;

                    hello_text.setText("Hello, " + username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        simulator_button.setOnClickListener(this);
        analytics_button.setOnClickListener(this);
        chatbot_button.setOnClickListener(this);
        logout_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeActivity.this, "Log Out Successful.", Toast.LENGTH_SHORT).show();
                openLoginActivity();
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
            case R.id.simulator_btn:

                openSimulatorActivity();
                break;
            case R.id.analytics_btn:
                openAnalyticsActivity();
                break;
            case R.id.chatbot_btn:
                openChatbotActivity();
                break;
        }
    }

    private void openChatbotActivity() {
        Intent intent = new Intent(this, ChatbotActivity.class);
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

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openSimulatorActivity() {
        Intent intent = new Intent(this, SimulatorActivity.class);
        startActivity(intent);
    }

    private void openAnalyticsActivity() {
        Intent intent = new Intent(this, AnalyticsActivity.class);
        startActivity(intent);
    }

}