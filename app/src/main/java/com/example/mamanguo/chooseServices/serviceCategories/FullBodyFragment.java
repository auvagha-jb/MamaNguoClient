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
import com.example.mamanguo.chooseServices.Bill;
import com.example.mamanguo.chooseServices.ServicesLists;

import java.util.Map;

import static com.example.mamanguo.chooseServices.Bill.getBillTotal;
import static com.example.mamanguo.chooseServices.Bill.mapKeyToArray;
import static com.example.mamanguo.chooseServices.Bill.mapValueToArray;
import static com.example.mamanguo.chooseServices.Bill.orderItems;
import static com.example.mamanguo.chooseServices.Bill.orderQuantity;
import static com.example.mamanguo.chooseServices.Bill.orderSubtotal;

public class FullBodyFragment extends Fragment implements com.example.mamanguo.chooseServices.RecyclerDataAdapter.ServicesSelectedListener {
    private RecyclerView mRecyclerView;
    private View RootView;
    private Context mContext;
    private Button button_findMamaNguo;
    com.example.mamanguo.chooseServices.RecyclerDataAdapter recyclerDataAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.activity_services_recycler, container, false);
        init();
        recyclerDataAdapter = new com.example.mamanguo.chooseServices.RecyclerDataAdapter(ServicesLists.listOfServices(mContext), this);
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
        Bill.setBillTotal(billTotal);
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

    @Override
    public void onServicesUpdated(Map<String, Integer> serviceCount, Map<String, Integer> serviceTotal, int billTotal) {
        updateOrder(serviceCount, serviceTotal, billTotal);
    }

}
