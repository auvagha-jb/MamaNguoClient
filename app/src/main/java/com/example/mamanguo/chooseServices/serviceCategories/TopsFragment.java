package com.example.mamanguo.chooseServices.serviceCategories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamanguo.R;
import com.example.mamanguo.chooseMamaNguo.ChooseMamaNguoActivity;
import com.example.mamanguo.chooseServices.helperClasses.Bill;
import com.example.mamanguo.chooseServices.helperClasses.ServicesLists;
import com.example.mamanguo.chooseServices.helperClasses.RecyclerDataAdapter;

import java.util.Map;

import static com.example.mamanguo.chooseServices.helperClasses.Bill.getBillTotal;
import static com.example.mamanguo.chooseServices.helperClasses.Bill.mapKeyToArray;
import static com.example.mamanguo.chooseServices.helperClasses.Bill.mapValueToArray;
import static com.example.mamanguo.chooseServices.helperClasses.Bill.orderItems;
import static com.example.mamanguo.chooseServices.helperClasses.Bill.orderQuantity;
import static com.example.mamanguo.chooseServices.helperClasses.Bill.orderSubtotal;

public class TopsFragment extends Fragment implements RecyclerDataAdapter.ServicesSelectedListener {
    private RecyclerView mRecyclerView;
    private View RootView;
    private Context mContext;
    private Button button_findMamaNguo;
    RecyclerDataAdapter recyclerDataAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.activity_services_recycler, container, false);
        init();
        recyclerDataAdapter = new RecyclerDataAdapter(ServicesLists.topsList(mContext), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(recyclerDataAdapter);
        mRecyclerView.setHasFixedSize(true);
        return RootView;
    }

    private void init() {
        mContext = getActivity();
        mRecyclerView = RootView.findViewById(R.id.recyclerView);
        button_findMamaNguo = RootView.findViewById(R.id.button_findMamaNguo);
    }

    public void updateOrder(Map<String, Integer> unitPrice, Map<String, Integer> serviceCount, Map<String, Integer> serviceTotal) {
        Bill.orderItems = mapKeyToArray(serviceCount);
        Bill.unitPrice = mapValueToArray(unitPrice);
        Bill.orderQuantity = mapValueToArray(serviceCount);
        Bill.orderSubtotal = mapValueToArray(serviceTotal);
        Toast.makeText(mContext, String.format(getString(R.string.bill_total_toast), getBillTotal()), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onServicesUpdated(Map<String, Integer> serviceUnitPrice, Map<String, Integer> serviceCount, Map<String, Integer> serviceTotal) {
        updateOrder(serviceUnitPrice, serviceCount, serviceTotal);
    }

}
