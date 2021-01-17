package com.example.eventreserve.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventreserve.R;
import com.example.eventreserve.models.Event;
import com.example.eventreserve.ui.EventDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    private List<Event> mevents;
    private Context mContext;

    public EventListAdapter(Context context, List<Event> events){
        mContext = context;
        mevents = events;
    }


    @Override
    public EventListAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        EventViewHolder viewHolder = new EventViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventListAdapter.EventViewHolder holder, int position){
        holder.bindEvent(mevents.get(position));
    }

    @Override
    public int getItemCount(){
        return mevents.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.eventImageView) ImageView mEventImageView;
        @BindView(R.id.eventNameTextView) TextView mNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        @BindView(R.id.attendingTextView) TextView mattendingTextView;

        private Context mContext;

        public EventViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindEvent(Event event) {
            mNameTextView.setText(event.getName());
            mCategoryTextView.setText(event.getCategory());
            //mattendingTextView.setText(event.getInterestedCount());
            Picasso.get().load(event.getImageUrl()).into(mEventImageView);
        }

        @Override
        public void onClick(View v){
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, EventDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("events", Parcels.wrap(mevents));
            mContext.startActivity(intent);
        }
    }
}
