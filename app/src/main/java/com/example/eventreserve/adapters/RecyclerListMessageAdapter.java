package com.example.eventreserve.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventreserve.R;
import com.example.eventreserve.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerListMessageAdapter extends RecyclerView.Adapter<RecyclerListMessageAdapter.ViewHolder>  {

    private List<Message> listMessages;
    private int rMessageMine, rMessageFriend, rImageMessageMine, rImageMessageFriend, rAudioMessageMine, rAudioMessageFriend;
    Context context;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    boolean isFinish = false; // audio check has ended

    public RecyclerListMessageAdapter(List<Message> listMessages, Context context, int rMessageMine, int rMessageFriend) {
        this.listMessages = listMessages;
        this.rMessageMine = rMessageMine; // layout own message
        this.rMessageFriend = rMessageFriend; // layout your message
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessageMine, textViewMessageTimeMine, textViewMessageFriend, textViewMessageTimeFriend;
        TextView imageViewMessageMineTime, imageViewMessageFriendTime;
        ImageView imageViewMessageMine, imageViewMessageFriend;
        CircleImageView avatarSeen, avatarSeenImage, avatarSeenAudio;

        ImageButton btnPlayAudioMessageMine, btnPlayAudioMessageFriend;
        TextView textViewMessageTimeAudioMine, textViewMessageTimeAudioFriend;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewMessageMine = itemView.findViewById(R.id.textViewMessageMine);
            textViewMessageTimeMine = itemView.findViewById(R.id.textViewMessageTimeMine);
            textViewMessageFriend = itemView.findViewById(R.id.textViewMessageFriend);
            textViewMessageTimeFriend = itemView.findViewById(R.id.textViewMessageTimeFriend);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (listMessages.isEmpty())
            return 0;

        return position;
    }

    @Override
    public RecyclerListMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create view of my own text messages
        View viewMessageMine = LayoutInflater.from(parent.getContext()).inflate(rMessageMine, parent, false);
        // Create views of your text messages
        View viewMessageFriend = LayoutInflater.from(parent.getContext()).inflate(rMessageFriend, parent, false);
        // Create a view of your own picture message
        View viewImageMessageMine = LayoutInflater.from(parent.getContext()).inflate(rImageMessageMine, parent, false);
        // Create view of your picture messages
        View viewImageMessageFriend = LayoutInflater.from(parent.getContext()).inflate(rImageMessageFriend, parent, false);

        View viewAudioMessageMine = LayoutInflater.from(parent.getContext()).inflate(rAudioMessageMine, parent, false);

        View viewAudioMessageFriend = LayoutInflater.from(parent.getContext()).inflate(rAudioMessageFriend, parent, false);

        ViewHolder viewHolder = null;

        switch (viewType) {
            case 0:
                viewHolder = null;
                break;
            case 1:
                viewHolder = new ViewHolder(viewMessageMine);
                break;
            case 2:
                viewHolder = new ViewHolder(viewMessageFriend);
                break;
            case 3:
                viewHolder = new ViewHolder(viewImageMessageMine);
                break;
            case 4:
                viewHolder = new ViewHolder(viewImageMessageFriend);
                break;
            case 5:
                viewHolder = new ViewHolder(viewAudioMessageMine);
                break;
            case 6:
                viewHolder = new ViewHolder(viewAudioMessageFriend);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerListMessageAdapter.ViewHolder holder, int position) {
        if (listMessages.isEmpty()) {
            //holder.textViewMessageNow.setVisibility(View.VISIBLE);
        } else {
            final Message msg = listMessages.get(position);
            if (msg.getUidSender().equals(FirebaseAuth.getInstance().getUid()) && !msg.isImage() && !msg.isAudio()) {
                holder.textViewMessageMine.setText(msg.getContent());
                holder.textViewMessageTimeMine.setText(msg.getTimeMessage().substring(11, 16));
            } else if (!msg.getUidSender().equals(FirebaseAuth.getInstance().getUid()) && !msg.isImage() && !msg.isAudio()) {
                holder.textViewMessageFriend.setText(msg.getContent());
                holder.textViewMessageTimeFriend.setText(msg.getTimeMessage().substring(11, 16));
            }

        }
    }

    @Override
    public int getItemCount() {
        return listMessages.size();
    }

}
