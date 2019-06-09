package com.example.mamanguo.chooseMamaNguo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mamanguo.R;
import com.example.mamanguo.RetroFit.MainActivity;

import java.util.ArrayList;

public class DynamicRecyclerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Button btn_history;
    private Context mContext;
    private RecyclerDataAdapter recyclerDataAdapter;
    ArrayList<DummyParentDataItem> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_recycler_find);
        mContext = DynamicRecyclerActivity.this;
        mRecyclerView = findViewById(R.id.recyclerView);
        btn_history = findViewById(R.id.button_viewHistory);
        listData = getDummyDataToPass();
        recyclerDataAdapter = new RecyclerDataAdapter(listData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(recyclerDataAdapter);
        mRecyclerView.setHasFixedSize(true);


        /* On click listeners: */
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);
            }
        });

        recyclerDataAdapter.setOnItemClickListener(new RecyclerDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String itemSelected = listData.get(position).getParentName();
                Toast.makeText(mContext, itemSelected, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<DummyParentDataItem> getDummyDataToPass() {
        ArrayList<DummyParentDataItem> arrDummyData = new ArrayList<>();
        ArrayList<DummyChildDataItem> childDataItems;

        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Highly reviewed"));
        arrDummyData.add(new DummyParentDataItem("Eunice", Float.parseFloat("5.0"), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Quick"));
        arrDummyData.add(new DummyParentDataItem("Gloria", Float.parseFloat("4.8"), childDataItems));
        /////////
        return arrDummyData;
    }

}
