package com.example.app.androidelem;

import java.util.HashMap;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ListInListActivity extends Activity {
    private ListView mListView;
    private ListViewAdapter mListViewAdapter;
    private ArrayList<ArrayList<HashMap<String,Object>>> mArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_in_list);
        init();
    }
    private void init(){
        mListView=(ListView) findViewById(R.id.list);
        initData();
        mListViewAdapter=new ListViewAdapter(mArrayList, ListInListActivity.this);
        mListView.setAdapter(mListViewAdapter);
    }
    private void initData(){
        mArrayList=new ArrayList<>();
        HashMap<String, Object> hashMap=null;
        ArrayList<HashMap<String,Object>> arrayListForEveryGridView;

        for (int i = 0; i < 10; i++) {
            arrayListForEveryGridView=new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                hashMap=new HashMap<>();
                hashMap.put("content", "i="+i+" ,j="+j);
                arrayListForEveryGridView.add(hashMap);
            }
            mArrayList.add(arrayListForEveryGridView);
        }

    }

}