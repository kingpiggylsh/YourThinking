package com.beginagain.yourthinking.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beginagain.yourthinking.Item.ChatDTO;
import com.beginagain.yourthinking.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatItemAdapter extends RecyclerView.Adapter<ChatItemAdapter.MyViewHolder> {
    Context context;
    ArrayList<ChatDTO> items;
    int itemLayout;

    public ChatItemAdapter(Context context, ArrayList<ChatDTO> items, int itemLayout) {
        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public ChatItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_bubble, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatItemAdapter.MyViewHolder myViewHolder, int position) {
        if (getItemCount() <= 0 && position >= getItemCount()) {
            return;
        }

        ChatDTO item = items.get(myViewHolder.getAdapterPosition());

        //
        String myUid = item.getUid();

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://beginagains.appspot.com");
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("ProfilePhotos/" + myUid + "_" + "photo");

        Glide.with(context).using(new FirebaseImageLoader())
                .load(pathReference)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(myViewHolder.mProfilePhoto);

        myViewHolder.cardView.setTag(position);
        myViewHolder.mName.setText(item.getUserName());
        myViewHolder.mChat.setText(item.getMessage());
        myViewHolder.mTime.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        // null error 방지
        if (items == null) {
            return 0;
        }
        return this.items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mName, mChat, mTime;
        public ImageView mProfilePhoto;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardview_chat);
            mProfilePhoto = (ImageView) itemView.findViewById(R.id.image_chat_profile);
            mName = (TextView) itemView.findViewById(R.id.text_view_chat_name);
            mChat = (TextView) itemView.findViewById(R.id.text_view_chat_chat);
            mTime = (TextView) itemView.findViewById(R.id.text_view_chat_time);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
