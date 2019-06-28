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
import com.example.mamanguo.getAvailableMamaNguo.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServicesActivity extends AppCompatActivity implements RecyclerDataAdapter.ServicesSelectedListener {
    private RecyclerView mRecyclerView;

    public Context getmContext() {
        return mContext;
    }

    private Context mContext;
    private Button button_findMamaNguo;
    private TextView textView_billTotal;
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
    }

    private void updateOrder(Map<String, Integer> serviceCount, Map<String, Integer> serviceTotal, int billTotal) {
        setBillTotal(billTotal);
        orderItems = mapKeyToArray(serviceCount);
        orderQuantity = mapValueToArray(serviceCount);
        orderSubtotal = mapValueToArray(serviceTotal);
        Toast.makeText(mContext, String.format(getString(R.string.bill_total_toast), getBillTotal()), Toast.LENGTH_SHORT).show();
    }

    private void createIntent() {
        Bundle extras = new Bundle();
        extras.putStringArray("ORDER_ITEMS", orderItems);
        extras.putIntegerArrayList("ORDER_QUANTITY", orderQuantity);
        extras.putIntegerArrayList("ORDER_SUBTOTAL", orderSubtotal);
        extras.putInt("BILL_TOTAL", getBillTotal());
        Intent intent = new Intent(mContext, MainActivity.class);
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
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("45"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.shirt), childDataItems));
        childDataItems = new ArrayList<>();

        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("45"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.t_shirt), childDataItems));
        childDataItems = new ArrayList<>();

        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.trousers), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.shorts), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.skirt), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("100"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.jeans), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.dresses), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_pair)));
        childDataItems.add(new DummyChildDataItem("30"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.leather_shoes), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_pair)));
        childDataItems.add(new DummyChildDataItem("90"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.sport_shoes), childDataItems));
        /////////
        return arrDummyData;
    }

}
