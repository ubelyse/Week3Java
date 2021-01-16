package com.example.eventreserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.editTextUsername) EditText mun;
    @BindView(R.id.editTextPassword) EditText mpsd;
    @BindView(R.id.editTextRetypePassword) EditText mretypepsd;
    @BindView(R.id.editTextFullName) EditText mfn;
    @BindView(R.id.editTextAddress) EditText madd;
    @BindView(R.id.editTextPhoneNumber) EditText mphone;
    @BindView(R.id.editTextDateOfBirth) EditText mdob;

    @BindView(R.id.buttonRegister) Button mregister;
    @BindView(R.id.buttonCancelRegister) Button mcancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}