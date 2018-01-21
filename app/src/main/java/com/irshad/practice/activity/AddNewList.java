package com.irshad.practice.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.irshad.practice.MainActivity;
import com.irshad.practice.R;
import com.irshad.practice.model.DataListModel;

public class AddNewList extends AppCompatActivity {
Activity activity;
    DatabaseReference databaseReference;
    Button btnSave;
    EditText edtText,detailsText;
    CheckBox  favouritecb,likecb;
    int like,favourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_list);
        activity=AddNewList.this;
        databaseReference = FirebaseDatabase.getInstance().getReference("mainList");
        btnSave = (Button) findViewById(R.id.btnSave);
        edtText = (EditText) findViewById(R.id.edtText);
        detailsText = (EditText) findViewById(R.id.detailsText);
       favouritecb=(CheckBox) findViewById(R.id.favouritecb) ;
       likecb=(CheckBox) findViewById(R.id.likecb) ;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtText.getText().toString();
                String details = detailsText.getText().toString();
                String dateString = "today";
                String timeString = "6:45 PM";


                if(favouritecb.isChecked()){
                    favourite=1 ;
                }
                else {
                    favourite=0 ;
                }
                if(likecb.isChecked()){
                    like=1;
                }
                else {
                    like=0;
                }


                if (TextUtils.isEmpty(MainActivity.mainId)) {
                    // Created new list
                    if (title.length() > 0) {
                        if (title.length() > 0) {
                            String id = databaseReference.push().getKey();
                            DataListModel dataListModel = new DataListModel(id, title, details, dateString, timeString, like, favourite);
                            databaseReference.child(id).setValue(dataListModel);
                            Toast.makeText(getApplicationContext(), "Data insert", Toast.LENGTH_SHORT).show();
                            activity.finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "please enter details", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                    }
                } else {
                    databaseReference.child(MainActivity.mainId).child("title").setValue(title);
                    // update list

                }


            }
        });
    }
}
