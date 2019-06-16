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
import android.widget.Toast;

import com.example.mamanguo.R;
import com.example.mamanguo.chooseMamaNguo.ChooseMamaNguoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServicesActivity extends AppCompatActivity implements RecyclerDataAdapter.ServicesSelectedListener {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private Button button_findMamaNguo;
    private TextView textView_billTotal;
    public String default_string;
    RecyclerDataAdapter recyclerDataAdapter;
    //Bill calculation
    private int billTotal;
    private String[] orderItems;
    private ArrayList<Integer> orderQuantity;
    private ArrayList<Integer> orderSubtotal;
    //Data structures to hold the activity data
    private Map<String, Integer> serviceCount = new HashMap<>();
    private Map<String, Integer> serviceTotal = new HashMap<>();

    public int getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(int billTotal) {
        this.billTotal = billTotal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_recycler);
        init();
        recyclerDataAdapter = new RecyclerDataAdapter(listOfServices(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(recyclerDataAdapter);
        mRecyclerView.setHasFixedSize(true);

        button_findMamaNguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIntent();
            }
        });
    }

    private void init() {
        mContext = ServicesActivity.this;
        mRecyclerView = findViewById(R.id.recyclerView);
        button_findMamaNguo = findViewById(R.id.button_findMamaNguo);
        default_string = getString(R.string.default_subtotal);
    }

    private void updateOrder(Map<String, Integer> serviceCount, Map<String, Integer> serviceTotal, int billTotal) {
        setBillTotal(billTotal);
        orderItems = mapKeyToArray(serviceCount);
        orderQuantity = mapValueToArray(serviceCount);
        orderSubtotal = mapValueToArray(serviceTotal);
        Toast.makeText(mContext, "Bill total: " + getBillTotal(), Toast.LENGTH_SHORT).show();
    }

    private void createIntent() {
        Bundle extras = new Bundle();
        extras.putStringArray("ORDER_ITEMS", orderItems);
        extras.putIntegerArrayList("ORDER_QUANTITY", orderQuantity);
        extras.putIntegerArrayList("ORDER_SUBTOTAL", orderSubtotal);
        extras.putInt("BILL_TOTAL", getBillTotal());
        Intent intent = new Intent(mContext, ChooseMamaNguoActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public String[] mapKeyToArray(Map<String, Integer> map) {
        int size = map.size();
        String[] keys = map.keySet().toArray(new String[size]);
        return keys;
    }

    public ArrayList<Integer> mapValueToArray(Map<String, Integer> map) {
        ArrayList<Integer> arrayList = new ArrayList<>(map.values());
        return arrayList;
    }

    @Override
    public void onServicesUpdated(Map<String, Integer> serviceCount, Map<String, Integer> serviceTotal, int billTotal) {
        updateOrder(serviceCount, serviceTotal, billTotal);
    }

    private ArrayList<DummyParentDataItem> listOfServices() {
        ArrayList<DummyParentDataItem> arrDummyData = new ArrayList<>();
        ArrayList<DummyChildDataItem> childDataItems;
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("45"));
        arrDummyData.add(new DummyParentDataItem("Shirts", childDataItems));
        childDataItems = new ArrayList<>();

        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("45"));
        arrDummyData.add(new DummyParentDataItem("T-shirts", childDataItems));
        childDataItems = new ArrayList<>();

        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("45"));
        arrDummyData.add(new DummyParentDataItem("Blouses", childDataItems));
        childDataItems = new ArrayList<>();

        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("45"));
        arrDummyData.add(new DummyParentDataItem("Shirts", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem("Trousers", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem("Shorts", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("Price per item"));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem("Skirts", childDataItems));
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
