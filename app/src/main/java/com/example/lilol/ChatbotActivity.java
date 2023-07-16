package com.example.lilol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatbotActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<ChatModel> list;
    private ChatbotAdapter adapterChatBot;
    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private String chat, keyword, date_time;
    private boolean auto_trigger = false;
    ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot2);
        chatsRV = findViewById(R.id.idRVChats);
        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);
        back_icon = findViewById(R.id.cancel_icon);

        back_icon.setOnClickListener(this);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        Intent intent = this.getIntent();

        if (intent.getStringExtra("keyword") != null){

            keyword = intent.getStringExtra("keyword");
            auto_trigger = true;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.207.203.136:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);
        RecyclerView rvChatList = findViewById(R.id.idRVChats);
        list = new ArrayList<>();

        if (auto_trigger)
        {
            chat = keyword + " problem";
            list.add(new ChatModel(chat));
//            adapterChatBot.notifyDataSetChanged();
            apiService.chatWithTheBit(chat).enqueue(callBack);
            auto_trigger = false;
        }

        sendMsgIB.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             // checking if the message entered
                                             // by user is empty or not.
                                             chat = userMsgEdt.getText().toString();
                                             if (chat.isEmpty()) {
                                                 // if the edit text is empty display a toast message.
                                                 Toast.makeText(ChatbotActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                                                 return;
                                             }
                                             saveChatbotMessage(chat);
                                             list.add(new ChatModel(chat));
                                             adapterChatBot.notifyDataSetChanged();
                                             apiService.chatWithTheBit(chat).enqueue(callBack);
                                             userMsgEdt.getText().clear();
                                         }
                                     });

        adapterChatBot = new ChatbotAdapter(list, this);
        // below line we are creating a variable for our linear layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatbotActivity.this, RecyclerView.VERTICAL, false);

        // below line is to set layout
        // manager to our recycler view.
        chatsRV.setLayoutManager(linearLayoutManager);

        // below line we are setting
        // adapter to our recycler view.
        chatsRV.setAdapter(adapterChatBot);


        /*findViewById(R.id.idIBSend).setOnClickListener(view -> {
            EditText etChat = findViewById(R.id.etChat);
            String chatText = etChat.getText().toString();

            if (chatText.isEmpty()) {
                Toast.makeText(ChatbotActivity.this, "Please enter a text", Toast.LENGTH_LONG).show();
                return;
            }

            list.add(new ChatModel(chatText));
            adapterChatBot.notifyDataSetChanged();
//            apiService.chatWithTheBit(chatText).enqueue(callBack);
            etChat.getText().clear();
            rvChatList.setLayoutManager(new LinearLayoutManager(this));
            rvChatList.setAdapter(adapterChatBot);
        });*/
    }

    private void saveChatbotMessage(String chat) {

        date_time = String.valueOf(Calendar.getInstance().getTime());
        Chatbot chatbot = new Chatbot(chat, date_time);

        FirebaseDatabase.getInstance().getReference("ChatbotInput").push()
                .setValue(chatbot).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(ChatbotActivity.this, "Successful",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChatbotActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private Callback<ChatResponse> callBack = new Callback<ChatResponse>() {
        @Override
        public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                list.add(new ChatModel(response.body().getChatBotReply(), true));
                adapterChatBot.notifyDataSetChanged();
            } else {
                Toast.makeText(ChatbotActivity.this, "Something went wrong (response)", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<ChatResponse> call, Throwable t) {
            Toast.makeText(ChatbotActivity.this, "Something went wrong (failure)", Toast.LENGTH_LONG).show();
        }
    };

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
