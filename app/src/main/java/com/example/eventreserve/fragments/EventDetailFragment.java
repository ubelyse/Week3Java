package com.example.eventreserve.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eventreserve.R;
import com.example.eventreserve.models.Event;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailFragment extends Fragment {
    @BindView(R.id.eventImageView) ImageView mImageLabel;
    @BindView(R.id.eventNameTextView) TextView mNameLabel;
    @BindView(R.id.categoryTextView) TextView mCategoriesLabel;
    @BindView(R.id.attendingTextView) TextView mattendinggLabel;
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView) TextView mPhoneLabel;
    @BindView(R.id.addressTextView) TextView mAddressLabel;
    @BindView(R.id.saveEventButton) TextView mSaveEventButton;

    private Event mevent;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    public static EventDetailFragment newInstance(Event event){
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("event", Parcels.wrap(event));
        eventDetailFragment.setArguments(args);
        return eventDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mevent = Parcels.unwrap(getArguments().getParcelable("event"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, view);
        Picasso.get().load(mevent.getImageUrl()).into(mImageLabel);
        mNameLabel.setText(mevent.getName());
        mCategoriesLabel.setText(mevent.getCategory());
        mattendinggLabel.setText(mevent.getAttendingCount());
        //mPhoneLabel.setText(mevent.getAttendingCount());
        mAddressLabel.setText(mevent.getLocation().getCity());
        return view;
    }

}