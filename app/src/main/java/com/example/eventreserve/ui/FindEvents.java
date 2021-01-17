package com.example.eventreserve.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eventreserve.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindEvents extends AppCompatActivity {
    @BindView(R.id.findeventsbtn) Button mFindRestaurantsButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_events);

        ButterKnife.bind(this);
        mFindRestaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = mLocationEditText.getText().toString();
                Intent intent = new Intent(FindEvents.this, EventsActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });

    }
}