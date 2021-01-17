package com.example.eventreserve.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eventreserve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindEvents extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.findeventsbtn) Button mfindeventsbtn;
    @BindView(R.id.savedEvents) Button msavedEventsbtn;
    //@BindView(R.id.locationEditText) EditText mLocationEditText;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_events);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                }else {

                }
            }
        };

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