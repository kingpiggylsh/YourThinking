package com.beginagain.yourthinking.MenuFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.beginagain.yourthinking.MyChatActivity;
import com.beginagain.yourthinking.R;
import com.beginagain.yourthinking.UserInfoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Menu5Fragment extends Fragment {
    View view;
    ViewGroup profileLayout;
    TextView mUserName, mUserEmail;
    de.hdodenhof.circleimageview.CircleImageView mUserImage;

    String name, email;
    Uri profilePhotoUrl;

    private Button mMyChatBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu5, container, false);
        init();

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        });

        mMyChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyChatActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    void init() {
        profileLayout = (ViewGroup) view.findViewById(R.id.layout_menu5_profile);
        mUserName = (TextView) view.findViewById(R.id.text_view_menu5_profile_name);
        mUserEmail = (TextView) view.findViewById(R.id.text_view_menu5_profile_email);
        mUserImage = (CircleImageView) view.findViewById(R.id.image_menu5_profile_image);

        mMyChatBtn = (Button) view.findViewById(R.id.btn_menu5_my_chat);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            name = user.getDisplayName();
            email = user.getEmail();
            profilePhotoUrl = user.getPhotoUrl();

            mUserName.setText(name);
            mUserEmail.setText(email);
            Picasso.get().load(profilePhotoUrl).into(mUserImage);
        }
    }
}