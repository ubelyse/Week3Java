package com.example.eventreserve.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventreserve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestorePassword extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    @BindView(R.id.buttonBack) Button btnBack;
    @BindView(R.id.buttonRestorePassWord) Button btnReStore;
    @BindView(R.id.editTextEmailNeedRestore) EditText txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        final Intent intent = new Intent(RestorePassword.this,LoginActivity.class);
        if(v.getId() == R.id.buttonBack)
        {
            startActivity(intent);

        }
        if(v.getId()==R.id.buttonRestorePassWord)
        {
            final String email = txtEmail.getText().toString().trim();

            firebaseAuth = FirebaseAuth.getInstance();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Please enter your registered email!", Toast.LENGTH_SHORT).show();
                return;
            }
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(), "We have sent the verification to your email, please check your email!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Invalid email. Please check your Email!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}