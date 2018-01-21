package com.irshad.practice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.irshad.practice.R;

public class DetailsPage extends AppCompatActivity {


    String title,details,id;
    TextView tilteTV,detailsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        tilteTV=(TextView) findViewById(R.id.tilteTV);
        detailsTV=(TextView) findViewById(R.id.detailsTV);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            details = bundle.getString("details");
            id = bundle.getString("id");
        }

        tilteTV.setText(title);
        detailsTV.setText(details);
        System.out.println(id);
    }
}
