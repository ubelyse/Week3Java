package com.example.eventreserve.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eventreserve.Constants;
import com.example.eventreserve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindEvents extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.findeventsbtn) Button mfindeventsbtn;
    @BindView(R.id.savedEvents) Button msavedEventsbtn;
    @BindView(R.id.locationEditText) EditText mLocationEditText;

    private DatabaseReference mSearchedLocationReference;

    private ValueEventListener mSearchedLocationReferenceListener;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

        mSearchedLocationReferenceListener = mSearchedLocationReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Locations updated", "location: " + location);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

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
            String location = mLocationEditText.getText().toString();

            saveLocationToFirebase(location);
            Intent intent = new Intent(FindEvents.this, EventsActivity.class);
            intent.putExtra("location", location);
            startActivity(intent);
        }
        if (v == msavedEventsbtn) {
            Intent intent = new Intent(FindEvents.this, SavedEventstListActivity.class);
            startActivity(intent);
        }
    }

    public void saveLocationToFirebase(String location) {
        mSearchedLocationReference.push().setValue(location);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedLocationReference.removeEventListener(mSearchedLocationReferenceListener);
    }
}