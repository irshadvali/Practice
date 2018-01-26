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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.irshad.practice.MainActivity;
import com.irshad.practice.R;
import com.irshad.practice.model.DataListModel;
import com.irshad.practice.utils.TimeValidation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNewList extends AppCompatActivity {
    Activity activity;
    DatabaseReference databaseReference;
    Button btnSave;
    EditText edtText, detailsText;
    CheckBox favouritecb, likecb;
    int like, favourite, poem, story;
    RadioGroup rg_story_poem;
    RadioButton rd_poem, rd_story;
    String dateString;
    int createdFlag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_list);
        activity = AddNewList.this;
        databaseReference = FirebaseDatabase.getInstance().getReference("mainList");
        btnSave = (Button) findViewById(R.id.btnSave);
        edtText = (EditText) findViewById(R.id.edtText);
        detailsText = (EditText) findViewById(R.id.detailsText);
        favouritecb = (CheckBox) findViewById(R.id.favouritecb);
        likecb = (CheckBox) findViewById(R.id.likecb);
        rg_story_poem = (RadioGroup) findViewById(R.id.rg_story_poem);
        rd_poem = (RadioButton) findViewById(R.id.rd_poem);
        rd_story = (RadioButton) findViewById(R.id.rd_story);

        getDateTime();
        rg_story_poem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtText.getText().toString();
                String details = detailsText.getText().toString();
                String timeString = "6:45 PM";


                if (favouritecb.isChecked()) {
                    favourite = 1;
                } else {
                    favourite = 0;
                }
                if (likecb.isChecked()) {
                    like = 1;
                } else {
                    like = 0;
                }
                if (rd_poem.isChecked()) {
                    poem = 1;
                    story = 0;

                } else {
                    poem = 0;
                    story = 1;

                }
                if (rd_story.isChecked()) {
                    poem = 0;
                    story = 1;

                } else {
                    poem = 1;
                    story = 0;

                }

                if (TextUtils.isEmpty(MainActivity.mainId)) {
                    // Created new list
                    if (title.length() > 0) {
                        if (title.length() > 0) {
                            String id = databaseReference.push().getKey();
                            DataListModel dataListModel = new DataListModel(id, title, details, dateString, timeString, like, favourite, poem, story,createdFlag);
                            databaseReference.child(id).setValue(dataListModel);
                            Toast.makeText(getApplicationContext(), "Data insert", Toast.LENGTH_SHORT).show();
                            activity.finish();
                        } else {
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

    public void getDateTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd HH:mm a");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        dateString=TimeValidation.getDateTime(formattedDate);
        System.out.println("Current Date and Time : " +TimeValidation.getDateTime(formattedDate) );
        TimeValidation.getDateTime(formattedDate);

    }
}
