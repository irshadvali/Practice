package com.irshad.practice.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.irshad.practice.R;
import com.irshad.practice.activity.DetailsPage;
import com.irshad.practice.model.DataListModel;

import java.util.List;

/**
 * Created by irshadvali on 21/01/18.
 */

public class DataListAdapter  extends RecyclerView.Adapter <DataListAdapter.MyViewHolder>{
    Activity activity;
    List<DataListModel> dataListModel;
    DatabaseReference databaseReference;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, details;
        CheckBox  favouritecb,likecb;
        public RelativeLayout viewBackground, viewForeground;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            details = (TextView) view.findViewById(R.id.details);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            favouritecb=(CheckBox) view.findViewById(R.id.favouritecb) ;
            likecb=(CheckBox) view.findViewById(R.id.likecb) ;
        }
    }
    public DataListAdapter(Activity activity, List<DataListModel> dataListModel, DatabaseReference databaseReference) {
        this.activity=activity;
        this.dataListModel= dataListModel;
        this.databaseReference=databaseReference;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);
        return new DataListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {

       final DataListModel dataList= dataListModel.get(position);
        holder.title.setText(dataList.getTitle());
        holder.details.setText(dataList.getDetails());
        if(dataList.getLike()==1){
            holder.likecb.setChecked(true);
        }
        else {
            holder.likecb.setChecked(false);
        }
        if(dataList.getFavourite()==1){
            holder.favouritecb.setChecked(true);
        }
        else {
            holder.favouritecb.setChecked(false);
        }

        holder.likecb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(dataList.getLike()==1){
                    databaseReference.child(dataListModel.get(position).getId()).child("like").setValue(0);
                    holder.likecb.setChecked(false);
                }
                else {
                    databaseReference.child(dataListModel.get(position).getId()).child("like").setValue(1);
                    holder.likecb.setChecked(true);
                }
            }
        });

        holder.favouritecb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(dataList.getFavourite()==1){
                    databaseReference.child(dataListModel.get(position).getId()).child("favourite").setValue(0);
                    holder.favouritecb.setChecked(false);
                }
                else {
                    databaseReference.child(dataListModel.get(position).getId()).child("favourite").setValue(1);
                    holder.favouritecb.setChecked(true);
                }
            }
        });

        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(activity.getApplicationContext(), DetailsPage.class);
                    i.putExtra("title", dataList.getTitle());
                    i.putExtra("details", dataList.getDetails());
                    i.putExtra("id", dataListModel.get(position).getId());
                    activity.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataListModel.size();
    }

    public void removeItem(int position) {
        databaseReference.child(dataListModel.get(position).getId()).removeValue();
        notifyItemRemoved(position);
    }
}
