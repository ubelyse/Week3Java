package com.example.eventreserve.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Environment;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerListMessageAdapter extends RecyclerView.Adapter<RecyclerListMessageAdapter.ViewHolder>  {

    private List<Message> listMessages;
    private int rMessageMine, rMessageFriend, rImageMessageMine, rImageMessageFriend, rAudioMessageMine, rAudioMessageFriend;
    Context context;
    private String mFileName = "";
    private MediaPlayer mPlayer=null;
    private String currentAudio = "";
    public static ImageButton btnCurrentPlay = null;
    public static MediaPlayer getCurrentMedia = null;

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


//            avatarSeen = itemView.findViewById(R.id.avatarSeen);
//            avatarSeenImage = itemView.findViewById(R.id.avatarSeenImage);
//            avatarSeenAudio = itemView.findViewById(R.id.avatarSeenAudio);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (listMessages.isEmpty())
            return 0;
        else if (listMessages.get(position).getUidSender().equals(FirebaseAuth.getInstance().getUid())
                && !listMessages.get(position).isImage() && !listMessages.get(position).isAudio()) // The message itself is text
            return 1;
        else if (!listMessages.get(position).getUidSender().equals(FirebaseAuth.getInstance().getUid())
                && !listMessages.get(position).isImage() && !listMessages.get(position).isAudio()) // Your message is text
            return 2;
        else if (listMessages.get(position).getUidSender().equals(FirebaseAuth.getInstance().getUid())
                && listMessages.get(position).isImage()) // my message is a picture
            return 3;
        else if (!listMessages.get(position).getUidSender().equals(FirebaseAuth.getInstance().getUid())
                && listMessages.get(position).isImage()) //your message is a photo
            return 4;
        else if (listMessages.get(position).getUidSender().equals(FirebaseAuth.getInstance().getUid())
                && listMessages.get(position).isAudio()) { // my message is an audio message
            return 5;
        } else if (!listMessages.get(position).getUidSender().equals(FirebaseAuth.getInstance().getUid())
                && listMessages.get(position).isAudio()) { // Your message is an audio message
            return 6;
        }
        return 0;
    }

    @Override
    public RecyclerListMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create view of my own text messages
        View viewMessageMine = LayoutInflater.from(parent.getContext()).inflate(rMessageMine, parent, false);
        // Create views of your text messages
        View viewMessageFriend = LayoutInflater.from(parent.getContext()).inflate(rMessageFriend, parent, false);
        // Create a view of your own picture message

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

    public void getAvatarSeen(final CircleImageView avtSeen, String uidFriend) {
        StorageReference ref = storageReference.child("avatar").child(uidFriend + "avatar.jpg");
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "be_images");
            if (!root.exists()) {
                root.mkdirs();
            }

            final File gpxfile = new File(root, uidFriend + "avatar.jpg");

            if (gpxfile.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(gpxfile.getAbsolutePath());
                avtSeen.setImageBitmap(bmp);
            } else {
                ref.getFile(gpxfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bmp = BitmapFactory.decodeFile(gpxfile.getAbsolutePath());
                        avtSeen.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        avtSeen.setImageResource(R.drawable.avatar_default);
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    }
                });
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void getImageStorage(final ImageView imageView, String senderUid, String receiverUid, String nameImage) {
        StorageReference ref = storageReference.child(senderUid).child(receiverUid).child(nameImage);
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "be_images");
            if (!root.exists()) {
                root.mkdirs();
            }

            final File gpxfile = new File(root, nameImage);

            if (gpxfile.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(gpxfile.getAbsolutePath());
                imageView.setImageBitmap(bmp);
            } else {
                ref.getFile(gpxfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bmp = BitmapFactory.decodeFile(gpxfile.getAbsolutePath());
                        imageView.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        imageView.setImageResource(R.drawable.not_found_image);
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    }
                });
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
