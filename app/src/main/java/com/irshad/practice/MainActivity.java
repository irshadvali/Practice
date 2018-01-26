package com.irshad.practice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.irshad.practice.adapter.DrawerItemCustomAdapter;
import com.irshad.practice.model.DataListModel;
import com.irshad.practice.model.DataModel;
import com.irshad.practice.utils.RecyclerItemTouchHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    Activity activity;
    RelativeLayout addList;
    RecyclerView rvlistView;
    DatabaseReference databaseReference;

    DataListAdapter dataListAdapter;

    List<DataListModel> dataListModel = new ArrayList<DataListModel>();
    public static String mainId;
    RecyclerView.LayoutManager mLayoutManager;
    int isFavFilter = 0;
    int isLikeFilter = 0;
    int isPoemFilter = 0;
    int isStoryFilter = 0;


    /*
    Drawer Navigation
     */

    private CoordinatorLayout coordinatorLayout;
    NavigationView navigationView;
    RelativeLayout drawer_header;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;
    ImageButton rightbtn;
    CheckBox hearted_cb, favourte_cb, poems_cb, story_cb;
    RelativeLayout apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act_drawer);
        activity = MainActivity.this;
        databaseReference = FirebaseDatabase.getInstance().getReference("mainList");
        addList = (RelativeLayout) findViewById(R.id.addList);
        rvlistView = (RecyclerView) findViewById(R.id.listView);

        rightbtn = (ImageButton) findViewById(R.id.right_btn);
        drawer_header = (RelativeLayout) findViewById(R.id.header);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(navigationView)) {
                    mDrawerLayout.closeDrawer(navigationView);
                }
            }
        });

        hearted_cb = (CheckBox) findViewById(R.id.hearted_cb);
        favourte_cb = (CheckBox) findViewById(R.id.favourte_cb);
        poems_cb = (CheckBox) findViewById(R.id.poems_cb);
        story_cb = (CheckBox) findViewById(R.id.story_cb);
        apply = (RelativeLayout) findViewById(R.id.apply);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //setupDrawerToggle();


        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(navigationView)) {
                    mDrawerLayout.closeDrawer(navigationView);
                } else if (!mDrawerLayout.isDrawerOpen(navigationView)) {
                    mDrawerLayout.openDrawer(navigationView);
                    setFilter();
                }
            }
        });


        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, AddNewList.class);
                startActivity(i);
            }
        });

        mLayoutManager = new LinearLayoutManager(activity);
        rvlistView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvlistView);

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
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(rvlistView);


        hearted_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (hearted_cb.isChecked()) {
                    hearted_cb.setTextColor(Color.parseColor("#3CB371"));

                } else {
                    hearted_cb.setTextColor(Color.parseColor("#ffffff"));

                }
            }
        });

        favourte_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (favourte_cb.isChecked()) {
                    favourte_cb.setTextColor(Color.parseColor("#3CB371"));

                } else {
                    favourte_cb.setTextColor(Color.parseColor("#ffffff"));

                }
            }
        });

        poems_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (poems_cb.isChecked()) {
                    poems_cb.setTextColor(Color.parseColor("#3CB371"));

                } else {
                    poems_cb.setTextColor(Color.parseColor("#ffffff"));

                }
            }
        });
        story_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (story_cb.isChecked()) {
                    story_cb.setTextColor(Color.parseColor("#3CB371"));

                } else {
                    story_cb.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favourte_cb.isChecked()){
                    isFavFilter=1;
                }
                else {
                    isFavFilter=0;
                }
                if(hearted_cb.isChecked()){
                    isLikeFilter=1;
                }
                else {
                    isLikeFilter=0;
                }
                if(poems_cb.isChecked()){
                    isPoemFilter=1;
                }
                else {
                    isPoemFilter=0;
                }

                if(story_cb.isChecked()){
                    isStoryFilter=1;
                }
                else {
                    isStoryFilter=0;
                }
                dataListAdapter = new DataListAdapter(activity, dataListModel, databaseReference, isFavFilter, isLikeFilter, isPoemFilter, isStoryFilter);
                rvlistView.setAdapter(dataListAdapter);
                if (mDrawerLayout.isDrawerOpen(navigationView)) {
                    mDrawerLayout.closeDrawer(navigationView);
                } else if (!mDrawerLayout.isDrawerOpen(navigationView)) {
                    mDrawerLayout.openDrawer(navigationView);
                }
            }
        });

        setFilter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataListModel.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DataListModel dataList = postSnapshot.getValue(DataListModel.class);
                    dataListModel.add(dataList);
                }

                System.out.println("irshad vali======" + dataListModel.size());
                dataListAdapter = new DataListAdapter(activity, dataListModel, databaseReference, isFavFilter, isLikeFilter, isPoemFilter, isStoryFilter);
                rvlistView.setAdapter(dataListAdapter);

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //mDrawerToggle.syncState();
    }


    public void setFilter() {
        if (isFavFilter == 1) {
            favourte_cb.setChecked(true);
        } else {
            favourte_cb.setChecked(false);
        }

        if(isLikeFilter==1){
            hearted_cb.setChecked(true);
        }
        else {
            hearted_cb.setChecked(false);
        }
        if(isPoemFilter==1){
            poems_cb.setChecked(true);
        }else {
            poems_cb.setChecked(false);
        }
        if(isStoryFilter==1){
            story_cb.setChecked(true);
        }else {
            story_cb.setChecked(false);
        }
    }

}
