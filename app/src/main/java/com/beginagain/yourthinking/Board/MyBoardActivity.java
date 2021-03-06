package com.beginagain.yourthinking.Board;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.beginagain.yourthinking.AccountActivity;
import com.beginagain.yourthinking.Adapter.BoardMyAdapter;
import com.beginagain.yourthinking.Item.BookBoardItem;
import com.beginagain.yourthinking.LoginActivity;
import com.beginagain.yourthinking.MainActivity;
import com.beginagain.yourthinking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MyBoardActivity extends AppCompatActivity {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private RecyclerView mSearchRecyclerView;
    public BoardMyAdapter mAdapter;
    private List<BookBoardItem> mSearchList;
    private TextView mCount;
    private int count=0;

    Toolbar mToolbar;

    String mName = user.getDisplayName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_board);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_myBoard);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#82b3c9")); // deep
        }

        mSearchRecyclerView = findViewById(R.id.recycler_myBoard);
        mSearchRecyclerView.clearOnChildAttachStateChangeListeners();
        mSearchRecyclerView.addItemDecoration(new DividerItemDecoration(getApplication(), 1));

        mCount = findViewById(R.id.text_view_search_count);

        mSearchList= new ArrayList<>();

        mStore.collection("board")
                .orderBy("date", Query.Direction.DESCENDING).limit(10000)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        for(QueryDocumentSnapshot dc : queryDocumentSnapshots) {
                            String id = (String) dc.getData().get("id");
                            String title = (String) dc.getData().get("title");
                            String contents = (String) dc.getData().get("contents");
                            String name = (String) dc.getData().get("name");
                            String date = (String) dc.getData().get("date");
                            String image = (String) dc.getData().get("image");
                            String author = (String)dc.getData().get("author");
                            String booktitle = (String)dc.getData().get("booktitle");
                            String recommend = String.valueOf(dc.getData().get("recount"));

                            if (mName.equals(name)){
                                BookBoardItem data = new BookBoardItem(id, title, contents, name, date, image, author, booktitle, recommend);
                                mSearchList.add(data);
                                count++;
                            }
                        }
                        mAdapter = new BoardMyAdapter(mSearchList);
                        mSearchRecyclerView.setAdapter(mAdapter);
                        mCount.setText(mName+"의 게시물 수 : "+count);
                    }
                });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        finish();
        Intent intent  = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}