package com.beginagain.yourthinking.Board;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.lang.String;

import com.beginagain.yourthinking.Adapter.BookBoardAdapter;
import com.beginagain.yourthinking.Item.BookBoardItem;
import com.beginagain.yourthinking.MainActivity;
import com.beginagain.yourthinking.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class BookBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private RecyclerView mMainRecyclerView;
    private BookBoardAdapter mAdapter;
    private List<BookBoardItem> mBoardList = null;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fabAdd, fabSearch, fabWrite, fabRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_board);



        init();
        /**
        mMainRecyclerView = findViewById(R.id.recycler_Board);

        mBoardList = new ArrayList<>();
        mBoardList.clear();

        mStore.collection("board")
                .orderBy("date", Query.Direction.DESCENDING).limit(10000)
                // 실시간 조회하려고 스냅 사용
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        for (QueryDocumentSnapshot dc : queryDocumentSnapshots) {
                            String id = (String) dc.getData().get("id");
                            String title = (String) dc.getData().get("title");
                            String contents = (String) dc.getData().get("contents");
                            String name = (String) dc.getData().get("name");
                            String date = (String) dc.getData().get("date");
                            String image = (String) dc.getData().get("image");
                            String author = (String)dc.getData().get("author"); // 8.3
                            String booktitle = (String)dc.getData().get("booktitle"); // 8.3

                            BookBoardItem data = new BookBoardItem(id, title, contents, name, date, image, author, booktitle);
                            mBoardList.add(data);
                        }
                       // mAdapter.notifyDataSetChanged();
                        mAdapter = new BookBoardAdapter(mBoardList);
                        mAdapter.notifyDataSetChanged();
                        mMainRecyclerView.setAdapter(mAdapter);
                    }
                });
         **/

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabSearch = (FloatingActionButton) findViewById(R.id.fab_search);
        fabWrite = (FloatingActionButton) findViewById(R.id.fab_write);
        fabRec = (FloatingActionButton)findViewById(R.id.fab_recommend);

        fabAdd.setOnClickListener(this);
        fabSearch.setOnClickListener(this);
        fabWrite.setOnClickListener(this);
        fabRec.setOnClickListener(this);
    }
    public void onResume(){
        super.onResume();
    }

    private void init() {

        mMainRecyclerView = findViewById(R.id.recycler_Board);

        mBoardList = new ArrayList<>();
        mBoardList.clear();
        mMainRecyclerView.clearOnChildAttachStateChangeListeners();

        mStore.collection("board")
                .orderBy("date", Query.Direction.DESCENDING).limit(10000)
                // 실시간 조회하려고 스냅 사용
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        for (QueryDocumentSnapshot dc : queryDocumentSnapshots) {
                            String id = (String) dc.getData().get("id");
                            String title = (String) dc.getData().get("title");
                            String contents = (String) dc.getData().get("contents");
                            String name = (String) dc.getData().get("name");
                            String date = (String) dc.getData().get("date");
                            String image = (String) dc.getData().get("image");
                            String author = (String)dc.getData().get("author"); // 8.3
                            String booktitle = (String)dc.getData().get("booktitle"); // 8.3

                            BookBoardItem data = new BookBoardItem(id, title, contents, name, date, image, author, booktitle);
                            mBoardList.add(data);
                        }
                        mAdapter = new BookBoardAdapter(mBoardList);
                        mMainRecyclerView.setAdapter(mAdapter);
                    }
                });

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab_add:
                anim();
                break;
            case R.id.fab_search:
                anim();
                Toast.makeText(this, "검색", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SearchBoardActivity.class));
                break;
            case R.id.fab_write:
                anim();
                Toast.makeText(this, "글 쓰기", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, WriteActivity.class));
                break;
            case R.id.fab_recommend:
                anim();
                mBoardList.clear();
                mBoardList = new ArrayList<>();
                mStore.collection("board").orderBy("recount", Query.Direction.DESCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                                for(QueryDocumentSnapshot dc : queryDocumentSnapshots) {
                                    String id = (String) dc.getData().get("id");
                                    String title = (String) dc.getData().get("title");
                                    String contents = (String) dc.getData().get("contents");
                                    String name = (String) dc.getData().get("name");
                                    String date = (String) dc.getData().get("date");
                                    String image = (String) dc.getData().get("image");
                                    String author = (String) dc.getData().get("author");
                                    String booktitle = (String) dc.getData().get("booktitle");

                                    BookBoardItem data = new BookBoardItem(id, title, contents, name, date, image, author, booktitle);
                                    mBoardList.add(data);
                                }
                                mAdapter = new BookBoardAdapter(mBoardList);
                                mMainRecyclerView.setAdapter(mAdapter);
                            }
                        });
                break;
        }
    }
    public void anim() {
        if (isFabOpen) {
            fabSearch.startAnimation(fab_close);
            fabWrite.startAnimation(fab_close);
            fabRec.startAnimation(fab_close);
            fabSearch.setClickable(false);
            fabWrite.setClickable(false);
            fabRec.setClickable(false);
            isFabOpen = false;
        } else {
            fabSearch.startAnimation(fab_open);
            fabWrite.startAnimation(fab_open);
            fabRec.startAnimation(fab_open);
            fabSearch.setClickable(true);
            fabWrite.setClickable(true);
            fabRec.setClickable(true);
            isFabOpen = true;
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookBoardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }
}