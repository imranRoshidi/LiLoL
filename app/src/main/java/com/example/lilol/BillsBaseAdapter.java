package com.example.lilol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BillsBaseAdapter extends BaseAdapter {

    Context context;
    String[] list;
    int[] images;
    LayoutInflater inflater;

    public BillsBaseAdapter(Context ctx, String [] list, int [] images) {
        this.context = ctx;
        this.list = list;
        this.images = images;
        //this.arrow_icon = arrow_icon;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return list.length;
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

        view = inflater.inflate(R.layout.tng_bills_postpaid_listview, null);
        TextView postpaidName = (TextView) view.findViewById(R.id.name);
        ImageView postpaidImage = (ImageView) view.findViewById(R.id.icon);
        postpaidName.setText(list[i]);
        postpaidImage.setImageResource(images[i]);

        return view;
    }
}
