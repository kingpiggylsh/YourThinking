package com.beginagain.yourthinking.MenuFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.beginagain.yourthinking.Adapter.BookMaratonHistoryAdapter;
import com.beginagain.yourthinking.BookMaraton.BookMaratonActivity;
import com.beginagain.yourthinking.Item.MaratonBookItem;
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

public class Menu4Fragment extends Fragment implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FloatingActionButton mFloatingMaraton;

    View view;

    MainActivity activity;
    private Button mMakeMaraton;
    private RecyclerView mMainRecyclerView;
    private BookMaratonHistoryAdapter mAdapter;
    private List<MaratonBookItem> mMaratonList = null;
    private List<MaratonBookItem> mList = null; // 89
    private EditText etSearch;
    private ImageButton btnSearch;
    private String text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu4, container, false);
        final String myUid = user.getUid();
        mMainRecyclerView = view.findViewById(R.id.recycler_maraton_doing);
        etSearch = view.findViewById(R.id.et_maraton_search);
        btnSearch = view.findViewById(R.id.btn_maraton_search_data);

        mMaratonList = new ArrayList<>();
        mList = new ArrayList<>();

        mMainRecyclerView.clearOnChildAttachStateChangeListeners();
        mMainRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        mStore.collection("maraton")
                // 실시간 조회하려고 스냅 사용
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                        for (QueryDocumentSnapshot dc : queryDocumentSnapshots) {

                            String id = (String) dc.getData().get("id");
                            String title = (String) dc.getData().get("title");
                            String author = (String) dc.getData().get("author");
                            String image = (String) dc.getData().get("image");
                            String date = (String) dc.getData().get("date");
                            String user = (String) dc.getData().get("user");
                            String category = (String) dc.getData().get("category");
                            String totalPage = (String) dc.getData().get("totalPage");
                            String readPage = (String)dc.getData().get("readPage");
                            String publisher = (String)dc.getData().get("publisher");
                            String isbn = (String)dc.getData().get("isbn");
                            String pubdate = (String)dc.getData().get("pubDate");
                            if(myUid.equals(user)) {
                                MaratonBookItem data = new MaratonBookItem(id, title, author, image, date, user, category, totalPage, readPage, publisher, isbn, pubdate);
                                mMaratonList.add(data);
                            }
                        }
                        mAdapter = new BookMaratonHistoryAdapter(mMaratonList);
                        mAdapter.notifyDataSetChanged();
                        mMainRecyclerView.setAdapter(mAdapter);
                    }
                });



        mFloatingMaraton = view.findViewById(R.id.fab_maraton_add);
        mFloatingMaraton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BookMaratonActivity.class);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_maraton_search_data:
                text = etSearch.getText().toString();
                if(etSearch.getText().toString().length()==0){
                    Toast.makeText(getContext(),"아무것도 없어요",Toast.LENGTH_SHORT).show();
                }else{
                    mMaratonList = new ArrayList<>();
                    mStore.collection("maraton")
                            .orderBy("date", Query.Direction.DESCENDING).limit(10000)
                            // 실시간 조회하려고 스냅 사용
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                                    for (QueryDocumentSnapshot dc : queryDocumentSnapshots) {
                                        String id = (String) dc.getData().get("id");
                                        String title = (String) dc.getData().get("title");
                                        String author = (String) dc.getData().get("author");
                                        String image = (String) dc.getData().get("image");
                                        String date = (String) dc.getData().get("date");
                                        String user = (String) dc.getData().get("user");
                                        String category = (String) dc.getData().get("category");
                                        String totalPage = (String) dc.getData().get("totalPage");
                                        String readPage = (String)dc.getData().get("readPage");
                                        String publisher = (String)dc.getData().get("publisher");
                                        String isbn = (String)dc.getData().get("isbn");
                                        String pubdate = (String)dc.getData().get("pubDate");

                                        if(text.toLowerCase().contains(title)||text.toUpperCase().contains(title)||text.contains(title)){
                                            MaratonBookItem data = new MaratonBookItem(id, title, author, image, date, user, category, totalPage, readPage, publisher, isbn, pubdate);
                                            mMaratonList.add(data);
                                        }else if(title.toLowerCase().contains(text)||title.toUpperCase().contains(text)||title.contains(text)) {
                                            MaratonBookItem data = new MaratonBookItem(id, title, author, image, date, user, category, totalPage, readPage, publisher, isbn, pubdate);
                                            mMaratonList.add(data);
                                        }
                                    }
                                    mAdapter = new BookMaratonHistoryAdapter(mMaratonList);
                                    mAdapter.notifyDataSetChanged();
                                    mMainRecyclerView.setAdapter(mAdapter);
                                }
                            });
                }
                break;
        }
    }
}