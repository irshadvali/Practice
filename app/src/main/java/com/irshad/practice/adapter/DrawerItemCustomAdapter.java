package com.irshad.practice.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.irshad.practice.R;
import com.irshad.practice.model.DataModel;



public class DrawerItemCustomAdapter extends ArrayAdapter<DataModel> {

    Activity activity;
    int layoutResourceId;
    DataModel data[] = null;

    public DrawerItemCustomAdapter(Activity activity, int layoutResourceId, DataModel[] data) {

        super(activity, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.activity = activity;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) activity).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        ImageView CheckBox = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);

        DataModel folder = data[position];


       // imageViewIcon.setImageResource(folder.icon);
        textViewName.setText(folder.name);

        return listItem;
    }
}