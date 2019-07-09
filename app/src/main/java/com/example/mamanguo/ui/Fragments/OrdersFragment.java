package com.example.mamanguo.ui.Fragments;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamanguo.R;
import com.example.mamanguo.Retrofit.MamaNguoApi;
import com.example.mamanguo.Retrofit.RetrofitClient;
import com.example.mamanguo.getAvailableMamaNguo.ChooseMamaNguoActivity;
import com.example.mamanguo.Retrofit.Models.MamaNguo;
import com.example.mamanguo.ui.Activities.MapViewActivity;
import com.example.mamanguo.ui.Activities.RatingActivity;
import com.example.mamanguo.ui.Adapters.OrdersAdapter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mamanguo.helpers.Constants.USER_DATA;
import static com.example.mamanguo.helpers.Constants.USER_ID;
import static com.example.mamanguo.helpers.MamaNguo.CHANNEL_1_ID;


public class OrdersFragment extends Fragment implements OrdersAdapter.OnItemClickListener {

    private OrdersAdapter ordersAdapter;
    private RecyclerView myRecyclerView;
    private static String TAG = ChooseMamaNguoActivity.class.getSimpleName();
    private Button btn_history;
    private List<MamaNguo> ordersData;
    private int userId;
    private Context mContext;
    private View view;
    private NotificationManagerCompat notificationManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_orders, container, false);
        mContext = getActivity();
        SharedPreferences sharedPreferences = Objects.requireNonNull(mContext).getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        userId = sharedPreferences.getInt(USER_ID, 0);
        notificationManager = NotificationManagerCompat.from(mContext);
        //Create a handler for the RetrofitInstance interface//
        MamaNguoApi retrofitInstance = RetrofitClient.getRetrofitInstance();
        Call<List<MamaNguo>> call = retrofitInstance.getHistory(userId);

        //Execute the request asynchronously//
        call.enqueue(new Callback<List<MamaNguo>>() {
            @Override
            //Handle a successful response//
            public void onResponse(Call<List<MamaNguo>> call, Response<List<MamaNguo>> response) {
                Log.d(TAG, "onResponse: Response Received");
                ordersData = response.body();
                loadDataList(ordersData);
                attachListeners();
            }

            @Override
            //Handle execution failures//
            public void onFailure(Call<List<MamaNguo>> call, Throwable throwable) {
                //If the request fails, then display the following toast//
                Log.e(TAG, String.format("onFailure: %s", throwable));
                Toast.makeText(getActivity(), "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });


        Call<MamaNguo> call2 = retrofitInstance.hasAccepted(userId);
        call2.enqueue(new Callback<MamaNguo>() {
            @Override
            //Handle a successful response//
            public void onResponse(Call<MamaNguo> call, Response<MamaNguo> response) {
                Log.d(TAG, "onResponse Call 2: Response Received");
                if(Objects.requireNonNull(response.body()).isAccepted()) {
                    sendNotification("Request Accepted",
                            "There is someone willing to take your order");
                }
            }

            @Override
            //Handle execution failures//
            public void onFailure(Call<MamaNguo> call, Throwable throwable) {
                //If the request fails, then display the following toast//
                Log.e(TAG, String.format("onFailure: %s", throwable));
                Toast.makeText(getActivity(), "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });

        attachListeners();
        return view;
    }


    //Display the retrieved data as a list//
    private void loadDataList(List<MamaNguo> ordersList) {
        //Get a reference to the RecyclerView//
        myRecyclerView = view.findViewById(R.id.myRecyclerView);
        ordersAdapter = new OrdersAdapter(ordersList,this);

        //Use a LinearLayoutManager with default vertical orientation//
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        myRecyclerView.setLayoutManager(layoutManager);

        //Set the Adapter to the RecyclerView//
        myRecyclerView.setAdapter(ordersAdapter);
    }

    public void sendNotification(String title, String message) {

        Notification notification = new NotificationCompat.Builder(mContext, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }


    private void attachListeners() {
        /* On click listeners: */
       /* btn_history.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HistoryActivity.class);
            startActivity(intent);
        });*/
    }

    @Override
    public void onCardClick(int position) {
        int mamanguoId= ordersData.get(position).getMamanguoId();
        String mamanguoName= ordersData.get(position).getFullName();
        Bundle extras = new Bundle();
        extras.putInt("MAMANGUO_ID", mamanguoId);
        extras.putString("MAMANGUO_NAME", mamanguoName);

        Intent intent = new Intent(mContext, RatingActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
