package com.example.eventreserve.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventreserve.Constants;
import com.example.eventreserve.R;
import com.example.eventreserve.models.Event;
import com.example.eventreserve.ui.EventDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    Context mContext;

    public FirebaseEventViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindEvents(Event event){
        ImageView eventImageView = mView.findViewById(R.id.eventImageView);
        TextView nameTextView = mView.findViewById(R.id.eventNameTextView);
        TextView categoryTextView = mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = mView.findViewById(R.id.attendingTextView);

        nameTextView.setText(event.getName());
        categoryTextView.setText(event.getCategory());
        ratingTextView.setText(event.getInterestedCount());
        Picasso.get().load(event.getImageUrl()).into(eventImageView);
    }

    @Override
    public void onClick(View view){
        final ArrayList<Event> events = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_EVENTS).child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    events.add(snapshot.getValue(Event.class));
                }

                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("events", Parcels.wrap(events));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
