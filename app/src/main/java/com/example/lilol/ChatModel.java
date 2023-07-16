package com.example.lilol;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatModel extends RecyclerView.Adapter {
    public String chat;
    public boolean isBot;

    public ChatModel(String chat, boolean isBot) {
        this.chat = chat;
        this.isBot = isBot;
    }

    public ChatModel(String chat) {
        this.chat = chat;
        this.isBot = false;
    }

    public String getChat(){
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
