package com.beginagain.yourthinking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MakeChatRoomActivity extends AppCompatActivity {

    EditText mRoomTitleEditText, mBookTitleEditText, mRoomDescEditText; // DESC 만들기
    Button mOkayBtn;

    String roomTitle, bookTitle, desc;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_chat_room);

        mRoomTitleEditText = (EditText) findViewById(R.id.edittext_make_chatroom_roomtitle);
        mBookTitleEditText = (EditText) findViewById(R.id.edittext_make_chatroom_booktitle);
        mRoomDescEditText = (EditText) findViewById(R.id.edittext_make_chatroom_desc);

        mOkayBtn = (Button) findViewById(R.id.btn_make_chatroom_okay);
        mOkayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomTitle = mRoomTitleEditText.getText().toString();
                bookTitle = mBookTitleEditText.getText().toString();
                desc = mRoomDescEditText.getText().toString();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", roomTitle);
                map.put("book", bookTitle);
                map.put("desc", desc);
                databaseReference.child("chat").child(roomTitle).child("info").updateChildren(map);

                finish();
            }
        });
    }
}