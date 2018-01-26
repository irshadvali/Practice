package com.irshad.practice.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.irshad.practice.MainActivity;
import com.irshad.practice.R;
import com.irshad.practice.model.DataListModel;
import com.irshad.practice.utils.TimeValidation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailsPage extends AppCompatActivity {


    String title,details,id,datetimeStr,datetime;
    EditText tilteTV,detailsTV;
    ImageView backbutton;
    TextView undoButton,saveButton,editButton,datetimeTv;
    LinearLayout mainlay;
    DatabaseReference databaseReference;
    DataListModel dataListModel;
    int createdflag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        tilteTV=(EditText) findViewById(R.id.tilteTV);
        detailsTV=(EditText) findViewById(R.id.detailsTV);
        backbutton=(ImageView) findViewById(R.id.backbutton);
        undoButton=(TextView) findViewById(R.id.undoButton);
        saveButton=(TextView) findViewById(R.id.saveButton);
        editButton=(TextView) findViewById(R.id.editButton);
        datetimeTv=(TextView) findViewById(R.id.datetimeTv);
        mainlay=(LinearLayout) findViewById(R.id.mainlay);
        editabFlase();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            details = bundle.getString("details");
            id = bundle.getString("id");
            createdflag= bundle.getInt("createdflag");
            datetimeStr=bundle.getString("datetime");
        }


        tilteTV.setText(title);
        detailsTV.setText(details);
        if(createdflag==1){
            datetimeTv.setText("Last Updated "+datetimeStr);
        }
        else {
            datetimeTv.setText("Created on "+datetimeStr);
        }

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFunction();
            }
        });
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoFunction();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFunction(id);
            }
        });
    }

    public void editFunction(){
        undoButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.GONE);
        tilteTV.setClickable(true);
        tilteTV.setFocusable(true);
        tilteTV.setFocusableInTouchMode(true);
        tilteTV.setCursorVisible(true);

        detailsTV.setFocusable(true);
        detailsTV.setClickable(true);
        detailsTV.setFocusableInTouchMode(true);
        detailsTV.setCursorVisible(true);


    }

    public void saveFunction(String id){

        updateValue(id);

        editabFlase();
        getDateTime();
    }
    public void editabFlase(){
        undoButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        editButton.setVisibility(View.VISIBLE);

        tilteTV.setClickable(false);
        tilteTV.setFocusable(false);
        tilteTV.setFocusableInTouchMode(false);
        tilteTV.setCursorVisible(false);


        detailsTV.setFocusable(false);
        detailsTV.setClickable(false);
        detailsTV.setFocusableInTouchMode(false);
        detailsTV.setCursorVisible(false);

        HideKeyBoard();
    }

    public void undoFunction(){
        undoButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        editButton.setVisibility(View.VISIBLE);


        tilteTV.setClickable(false);
        tilteTV.setFocusable(false);
        tilteTV.setFocusableInTouchMode(false);
        tilteTV.setCursorVisible(false);
        detailsTV.setFocusable(false);
        detailsTV.setClickable(false);
        detailsTV.setFocusableInTouchMode(false);
        detailsTV.setCursorVisible(false);

        tilteTV.setText(title);
        detailsTV.setText(details);
        HideKeyBoard();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void HideKeyBoard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainlay.getWindowToken(), 0);
    }

    public void updateValue(String id){
        databaseReference=FirebaseDatabase.getInstance().getReference("mainList");
        String newTitle=tilteTV.getText().toString();
        String newDetails=detailsTV.getText().toString();
        databaseReference.child(id).child("title").setValue(newTitle);
        databaseReference.child(id).child("details").setValue(newDetails);
        databaseReference.child(id).child("dateString").setValue(datetime);
        databaseReference.child(id).child("createdFlag").setValue(1);


        Query idQuery = databaseReference.orderByChild("id").equalTo(id);
        idQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    dataListModel = singleSnapshot.getValue(DataListModel.class);
                    title=dataListModel.getTitle();
                    details=dataListModel.getDetails();
                    tilteTV.setText(title);
                    detailsTV.setText(details);
                    datetimeTv.setText("Last Updated "+dataListModel.getDateString());

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getDateTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd HH:mm a");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        datetime= TimeValidation.getDateTime(formattedDate);
        System.out.println("Current Date and Time : " +TimeValidation.getDateTime(formattedDate) );
        TimeValidation.getDateTime(formattedDate);

    }
}
