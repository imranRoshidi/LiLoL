package com.example.lilol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TngTransactionBaseAdapter extends BaseAdapter {

    Context context;
    //String title, type, date_time, status, payee_receiver, amount;
    ArrayList<String> title, type, date_time, amount;
    LayoutInflater inflater;
    List<String> reversedTitle, reversedType, reversedDate, reversedAmount;

    public TngTransactionBaseAdapter(Context ctx, ArrayList<String> title, ArrayList<String> type, ArrayList<String> date_time, ArrayList<String> amount) {
        this.context = ctx;
        this.title = title;
        this.type = type;
        this.date_time = date_time;
        this.amount = amount;
        //this.arrow_icon = arrow_icon;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return title.size();
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

        view = inflater.inflate(R.layout.tng_transaction_listview, null);
        TextView title_text = (TextView) view.findViewById(R.id.title);
        TextView type_text = (TextView) view.findViewById(R.id.category);
        TextView amount_text = (TextView) view.findViewById(R.id.amount);
        TextView date_text = (TextView) view.findViewById(R.id.date_time);
        title_text.setText(title.get(i));
        type_text.setText(type.get(i));
        amount_text.setText("RM" + amount.get(i));
        date_text.setText(date_time.get(i));

        return view;
    }
}
