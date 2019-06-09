package com.example.mamanguo.chooseServices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mamanguo.R;

import java.util.ArrayList;

public class DynamicRecyclerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private Button button_findMamaNguo;
    private TextView textView_billTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_recycler);
        mContext = DynamicRecyclerActivity.this;
        mRecyclerView = findViewById(R.id.recyclerView);
        button_findMamaNguo = findViewById(R.id.button_findMamaNguo);
        RecyclerDataAdapter recyclerDataAdapter = new RecyclerDataAdapter(getDummyDataToPass());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(recyclerDataAdapter);
        mRecyclerView.setHasFixedSize(true);

        button_findMamaNguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, com.example.mamanguo.chooseMamaNguo.DynamicRecyclerActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<DummyParentDataItem> getDummyDataToPass() {
        ArrayList<DummyParentDataItem> arrDummyData = new ArrayList<>();
        ArrayList<DummyChildDataItem> childDataItems;

        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("45"));
        arrDummyData.add(new DummyParentDataItem("Shirts, T-shirts & Blouses", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem("Trousers, Shorts & Skirts", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("100"));
        arrDummyData.add(new DummyParentDataItem("Jeans", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem("Dresses", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Price per pair"));
        childDataItems.add(new DummyChildDataItem("30"));
        arrDummyData.add(new DummyParentDataItem("Leather Shoes", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Price per pair"));
        childDataItems.add(new DummyChildDataItem("90"));
        arrDummyData.add(new DummyParentDataItem("Sport Shoes", childDataItems));
        /////////
        return arrDummyData;
    }


}
