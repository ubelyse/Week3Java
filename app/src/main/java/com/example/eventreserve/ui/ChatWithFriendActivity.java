package com.example.eventreserve.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.eventreserve.R;
import com.example.eventreserve.models.Account;
import com.example.eventreserve.models.Message;
import com.example.eventreserve.models.MessageController;
import com.example.eventreserve.models.RecentlyChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class ChatWithFriendActivity extends AppCompatActivity implements ValueEventListener,
        View.OnClickListener{

    private Intent iChat;
    private String uidFriendChat, nameFriendChat;

    private TextView textViewNameFriend;
    private ImageButton btnSendMessage, btnBackButton, btnSendImage, btnMoreInfo, btnSendAudio,btnSendImageWithCamera;
    private EditText editTextMessage;
    private RecyclerView recyclerViewMessage;

    private DatabaseReference nodeRefreshMessage, nodeMessage, nodeInfoMine, nodeInfoFriend, nodeGetMyName;
    private String myName = "";

    private MessageController messageController;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_friend);

        iChat= getIntent();
        uidFriendChat = iChat.getStringExtra("UID_Friend"); // lấy uid người bạn chat cùng
        nameFriendChat = iChat.getStringExtra("Name_Friend"); // lấy tên hiển thị trên thanh toolbars

        recyclerViewMessage = (RecyclerView)findViewById(R.id.recyclerViewMessage);


        textViewNameFriend = (TextView)findViewById(R.id.textViewNameFriend);
        textViewNameFriend.setText(nameFriendChat + "");

        btnSendMessage = (ImageButton)findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(this);

        editTextMessage = (EditText)findViewById(R.id.editTextMessage);
        editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                messageController.scrollMessageEditText();
                if(editTextMessage.getText().toString().isEmpty()){
                    btnSendMessage.setImageResource(R.drawable.icon_message_empty);
                }
                else {
                    btnSendMessage.setImageResource(R.drawable.icon_send_message);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                messageController.scrollMessageEditText();
                if(editTextMessage.getText().toString().isEmpty()){
                    btnSendMessage.setImageResource(R.drawable.icon_message_empty);
                }
                else {
                    btnSendMessage.setImageResource(R.drawable.icon_send_message);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                messageController.scrollMessageEditText();
                if(editTextMessage.getText().toString().isEmpty()){
                    btnSendMessage.setImageResource(R.drawable.icon_message_empty);
                }
                else {
                    btnSendMessage.setImageResource(R.drawable.icon_send_message);
                }
            }
        });

        //nodeGetMyName = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid());
        nodeGetMyName= FirebaseDatabase.getInstance().getReference();
        nodeGetMyName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("users").child(FirebaseAuth.getInstance().getUid()).exists()){
                    Account acc = dataSnapshot.getValue(Account.class);
                    myName = acc.getFullName(); // get a name for the latest push message below
                }
                else if ((dataSnapshot.child("doctors").child(FirebaseAuth.getInstance().getUid()).exists()))
                {
                    Account acc = dataSnapshot.getValue(Account.class);
                    myName = acc.getFullName(); // get a name for the latest push message below
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        nodeRefreshMessage = FirebaseDatabase.getInstance().getReference();
        nodeRefreshMessage.addValueEventListener(this); // listen to events when there are new messages or sent
    }


    @Override
    protected void onStart() {
        super.onStart();
        messageController = new MessageController(ChatWithFriendActivity.this,uidFriendChat,recyclerViewMessage);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // get online status
        // get a list of messages
        DataSnapshot nodeMessage = dataSnapshot.child("messages");
        messageController.refreshMessage(nodeMessage,myName,nameFriendChat);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnBackMessages:
                Intent iFriendFragment = new Intent(ChatWithFriendActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                if(iChat.getStringExtra("From").equals("Friend_Fragment")){
                    bundle.putInt("ReturnTab", 1);
                }
                else if (iChat.getStringExtra("From").equals("Message_Fragment")
                        || iChat.getStringExtra("From").equals("MoreInfoMessage")){
                    bundle.putInt("ReturnTab", 0);
                }
                bundle.putString("UID",FirebaseAuth.getInstance().getUid());
                iFriendFragment.putExtras(bundle);
                startActivity(iFriendFragment);
                finish();
                break;

            case R.id.btnSendMessage:
                String contentMessage = editTextMessage.getText().toString();
                if(!contentMessage.isEmpty()){
                    pushMessage("text",contentMessage);
                }
                break;

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        nodeRefreshMessage.addValueEventListener(this);
    }

    public void pushMessage(String type, String contentMessage){
        TimeZone timeZone = TimeZone.getTimeZone("Africa/Kigali");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        simpleDateFormat.setTimeZone(timeZone);
        String timeMsg = simpleDateFormat.format(Calendar.getInstance().getTime());

        nodeMessage = FirebaseDatabase.getInstance().getReference().child("messages");

        Message msg = null;

        switch (type){
            case "text":
                msg = new Message(FirebaseAuth.getInstance().getUid(), uidFriendChat,
                        contentMessage, false, false, timeMsg,false);
                break;
            case "image":
                msg = new Message(FirebaseAuth.getInstance().getUid(), uidFriendChat,
                        contentMessage, true, false, timeMsg,false);
                break;
            case "audio":
                msg = new Message(FirebaseAuth.getInstance().getUid(), uidFriendChat,
                        contentMessage, false, true, timeMsg,false);
                break;
        }
        nodeMessage.push().setValue(msg);

        //push information required for retrieving recent listings
        nodeInfoMine = FirebaseDatabase.getInstance().getReference().child("more_info")
                .child(FirebaseAuth.getInstance().getUid()).child("last_messages");

        // push the last message to show it when retrieving the recent message list
        nodeInfoMine.child(uidFriendChat).setValue(new RecentlyChat(FirebaseAuth.getInstance().getUid(),uidFriendChat,nameFriendChat,
                contentMessage,type, timeMsg,false)); // Default sending last message is unread

        nodeInfoFriend = FirebaseDatabase.getInstance().getReference().child("more_info")
                .child(uidFriendChat).child("last_messages");

        nodeInfoFriend.child(FirebaseAuth.getInstance().getUid()).setValue(new RecentlyChat(FirebaseAuth.getInstance().getUid(),
                FirebaseAuth.getInstance().getUid(), myName, contentMessage,type, timeMsg,false));

        editTextMessage.setText(""); // delete previous messages
    }


}