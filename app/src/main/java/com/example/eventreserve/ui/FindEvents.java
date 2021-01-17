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

public class FindEvents extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.findeventsbtn) Button mfindeventsbtn;
    @BindView(R.id.savedEvents) Button msavedEventsbtn;
    @BindView(R.id.locationEditText) EditText mLocationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_events);

        ButterKnife.bind(this);
        mfindeventsbtn.setOnClickListener(this);
        msavedEventsbtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v == mfindeventsbtn) {
            Intent intent = new Intent(FindEvents.this, EventsActivity.class);
            startActivity(intent);
        }
        if (v == msavedEventsbtn) {
            Intent intent = new Intent(FindEvents.this, SavedEventstListActivity.class);
            startActivity(intent);
        }
    }
}