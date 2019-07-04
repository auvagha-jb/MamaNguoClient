package com.example.mamanguo.chooseServices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamanguo.R;
import com.example.mamanguo.chooseMamaNguo.ChooseMamaNguoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceFragment extends Fragment implements RecyclerDataAdapter.ServicesSelectedListener {
    private RecyclerView mRecyclerView;
    private View RootView;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.activity_services_recycler, container, false);
        init();
        recyclerDataAdapter = new RecyclerDataAdapter(ServicesLists.listOfServices(mContext), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(recyclerDataAdapter);
        mRecyclerView.setHasFixedSize(true);
        button_findMamaNguo.setOnClickListener(v -> createIntent());
        return RootView;
    }

    private void init() {
        mContext = getActivity();
        mRecyclerView = RootView.findViewById(R.id.recyclerView);
        button_findMamaNguo = RootView.findViewById(R.id.button_findMamaNguo);
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

}
