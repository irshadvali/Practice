package com.irshad.practice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irshad.practice.activity.AddNewList;
import com.irshad.practice.adapter.DataListAdapter;
import com.irshad.practice.model.DataListModel;
import com.irshad.practice.utils.RecyclerItemTouchHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    Activity activity;
    RelativeLayout addList;
    RecyclerView listView;
    DatabaseReference databaseReference;

    DataListAdapter dataListAdapter;

    List<DataListModel> dataListModel= new ArrayList<DataListModel>();
    public static String mainId;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=MainActivity.this;
        databaseReference = FirebaseDatabase.getInstance().getReference("mainList");
        addList=(RelativeLayout) findViewById(R.id.addList) ;
        listView = (RecyclerView) findViewById(R.id.listView);
        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(activity, AddNewList.class);
                startActivity(i);
            }
        });

        mLayoutManager = new LinearLayoutManager(activity);
        listView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(listView);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(listView);

    }
    @Override
    protected void onStart(){
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataListModel.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    DataListModel dataList=postSnapshot.getValue(DataListModel.class);
                    dataListModel.add(dataList);
                }

                System.out.println("irshad vali======"+dataListModel.size());
                dataListAdapter = new DataListAdapter(activity,dataListModel,databaseReference);
                listView.setAdapter(dataListAdapter);

            }





            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof DataListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = dataListModel.get(viewHolder.getAdapterPosition()).getId();

            // backup of removed item for undo purpose
            final DataListModel deletedItem = dataListModel.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            dataListAdapter.removeItem(viewHolder.getAdapterPosition());


        }
    }
}
