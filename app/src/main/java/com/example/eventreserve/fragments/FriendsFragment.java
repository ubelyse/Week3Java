package com.example.eventreserve.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.eventreserve.R;
import com.example.eventreserve.adapters.ListFriendAdapter;
import com.example.eventreserve.models.Account;
import com.example.eventreserve.models.AccountRequest;
import com.example.eventreserve.ui.ChatWithFriendActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendsFragment extends Fragment{

    private Toolbar toolbar;
    private ListView listViewFriend;
    private HashMap<String, Account> hashMapFriends;
    private ListFriendAdapter listFriendAdapter;
    private DatabaseReference nodeRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        /*toolbar = v.findViewById(R.id.toolBarSearch);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        listViewFriend = (ListView)v.findViewById(R.id.listViewFriend);
        hashMapFriends = new HashMap<>();
        listFriendAdapter = new ListFriendAdapter(getActivity(), R.layout.item_friend_in_list_friend, hashMapFriends);
        listViewFriend.setAdapter(listFriendAdapter);

        listViewFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AccountRequest fr = (AccountRequest)parent.getAdapter().getItem(position);
                Intent iChat = new Intent(getActivity(), ChatWithFriendActivity.class);
                iChat.putExtra("UID_Friend",fr.getUid());
                iChat.putExtra("Name_Friend",fr.getFullName());
                iChat.putExtra("From","Friend_Fragment");
                startActivity(iChat);
            }
        });

        nodeRoot = FirebaseDatabase.getInstance().getReference();
        nodeRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> uidFriends = new ArrayList<>();
                DataSnapshot nodeFriends = snapshot.child("users");
                for (DataSnapshot dataSnapshot : nodeFriends.getChildren()) {
                    if (!dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())) {
                        Account account = dataSnapshot.getValue(Account.class);
                        if (!hashMapFriends.containsValue(account)) {
                            hashMapFriends.put(dataSnapshot.getKey(), account);
                        }
                    }
                }
                listFriendAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }
}