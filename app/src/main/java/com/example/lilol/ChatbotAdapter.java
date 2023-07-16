package com.example.lilol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatbotAdapter extends RecyclerView.Adapter {
    private ArrayList<ChatModel> list;
    private Context context;

    // constructor class.
    public ChatbotAdapter(ArrayList<ChatModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout, parent, false);
        return new UserViewHolder(view);*/

        View view;
        // below code is to switch our
        // layout type along with view holder.
        switch (viewType) {
            case 0:
                // below line we are inflating user message layout.
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout, parent, false);
                return new UserViewHolder(view);
            case 1:
                // below line we are inflating bot message layout.
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_chat_layout, parent, false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel modal = list.get(position);
//        ((UserViewHolder) holder).userTV.setText(modal.getChat());
        if (!modal.isBot) {
            ((UserViewHolder) holder).userTV.setText(modal.getChat());
        } else {
            ((BotViewHolder) holder).botTV.setText(modal.getChat());
        }
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        // below line of code is to set position.
        if (!list.get(position).isBot) {
            return 0;
        }
        else {
            return 1;
        }
        /*switch (list.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }*/
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        // creating a variable
        // for our text view.
        TextView userTV;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing with id.
            userTV = itemView.findViewById(R.id.idTVUser);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {

        // creating a variable
        // for our text view.
        TextView botTV;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing with id.
            botTV = itemView.findViewById(R.id.idTVBot);
        }
    }

    /*class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView txtChat;

        MyViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_chat, parent, false));
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_chat, parent, false);
            txtChat = itemView.findViewById(R.id.txtChat);
        }

        void bind(ChatModel chat) {
            if (!chat.isBot) {
                txtChat.setBackgroundColor(Color.WHITE);
                txtChat.setTextColor(Color.BLACK);
                txtChat.setText("chat.chat");
            } else {
                txtChat.setBackgroundColor(Color.CYAN);
                txtChat.setTextColor(Color.BLACK);
                txtChat.setText(chat.chat);
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(list.get(position));
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.txtChat.setText("list.");
//        timeStampStr = chatMessage.getTime();
//        viewHolder.time.setText((timeStampStr));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addChatToList(ChatModel chat) {
        list.add(chat);
        notifyDataSetChanged();
    }*/
}
