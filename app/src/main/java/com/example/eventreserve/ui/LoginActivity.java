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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.scottyab.aescrypt.AESCrypt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.buttonLogin) Button mlogin;
    @BindView(R.id.buttonSwitchRegister) Button mswitchregister;
    @BindView(R.id.editTextPassword) EditText mpsd;
    @BindView(R.id.editTextUsername) EditText mun;
    @BindView(R.id.buttonForgetPassword) TextView mforgot;
    String email,password;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mswitchregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mforgot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this,RestorePassword.class);
                startActivity(intent);
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        if(readFile().equals("")) {

        }
        else
        {
            String messageAfterDecrypt="";
            try {
                messageAfterDecrypt = AESCrypt.decrypt("123", readFile());
            }catch (GeneralSecurityException e){
                //handle error - could be due to incorrect password or tampered encryptedMsg
            }
            if(messageAfterDecrypt!="") {
                String[] fulluser = messageAfterDecrypt.split("[ ]");
                email = fulluser[0].trim();
                password = fulluser[1].trim();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    Toast.makeText(LoginActivity.this, "Your account information has been changed! Please check again!!",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    String uid = firebaseAuth.getCurrentUser().getUid();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("UID", uid);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        });
            }

        }


        mlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                email = mun.getText().toString();
                password = mpsd.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email before logging in!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter your password before logging in!", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Wrong account information or password. Please check again!!",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    final FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (!user.isEmailVerified()) {
                                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(LoginActivity.this,
                                                            "The account is not activated yet. The system has sent activation email, please activate before using your account " + user.getEmail(),
                                                            Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(LoginActivity.this,
                                                            "Account verification error please wait a moment!",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        return;
                                    }
                                    saveFile(email,password);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    String uid = firebaseAuth.getCurrentUser().getUid();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("UID", uid);
                                    intent.putExtras(bundle);
                                    //overridePendingTransition(R.anim.animation_in,R.anim.animation_out);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSwitchRegister:
                Intent iRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(iRegister);
        }
    }
    public  void saveFile(String email, String passWord)
    {
        try {

            FileOutputStream out = this.openFileOutput("session.txt", MODE_PRIVATE);

            String fulluser = email +" " + passWord;
            String encryptedMsg ="";
            try {
                encryptedMsg = AESCrypt.encrypt("123",fulluser);
                System.out.println("what is it: "+encryptedMsg);
            }catch (GeneralSecurityException e){
                //handle error
            }
            out.write(encryptedMsg.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private String readFile() {
        try {
            FileInputStream in = this.openFileInput("session.txt");
            if(in==null)
                return "";
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }

            return sb.toString();

        } catch (Exception e) {
            return "";
        }

    }

}