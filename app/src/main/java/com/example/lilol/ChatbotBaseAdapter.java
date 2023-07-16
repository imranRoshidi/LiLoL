package com.example.lilol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatbotBaseAdapter extends BaseAdapter {

    Context context;
    //String title, type, date_time, status, payee_receiver, amount;
    ArrayList<String> chat, date_time;
    LayoutInflater inflater;

    public ChatbotBaseAdapter(Context ctx, ArrayList<String> chat, ArrayList<String> date_time) {
        this.context = ctx;
        this.chat = chat;
        this.date_time = date_time;
        //this.arrow_icon = arrow_icon;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return chat.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.chatbot_input_listview, null);
        TextView chat_text = (TextView) view.findViewById(R.id.chat);
        TextView date_text = (TextView) view.findViewById(R.id.date_time);
        chat_text.setText(chat.get(i));
        date_text.setText(date_time.get(i));

        return view;
    }

}
