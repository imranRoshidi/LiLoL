package com.example.lilol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UtilitiesBaseAdapter extends BaseAdapter {

    Context context;
    String[] utilitiesList;
    int[] utilitiesImages;
    //int arrow_icon;
    LayoutInflater inflater;

    public UtilitiesBaseAdapter(Context ctx, String [] utilitiesList, int [] utilitiesImages) {
        this.context = ctx;
        this.utilitiesList = utilitiesList;
        this.utilitiesImages = utilitiesImages;
        //this.arrow_icon = arrow_icon;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return utilitiesList.length;
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
        view = inflater.inflate(R.layout.tng_bills_utilities_listview, null);
        TextView textView = (TextView) view.findViewById(R.id.utilities_name);
        ImageView postpaidImage = (ImageView) view.findViewById(R.id.utilities_icon);
        //ImageView arrow_icon = (ImageView) view.findViewById(R.id.arrow_icon);
        textView.setText(utilitiesList[i]);
        postpaidImage.setImageResource(utilitiesImages[i]);
        //arrow_icon.setImageResource(arrow_icon);
        return view;
    }
}
