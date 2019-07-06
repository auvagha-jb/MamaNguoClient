package com.example.mamanguo.getAvailableMamaNguo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.mamanguo.R;
import com.example.mamanguo.Retrofit.MamaNguoApi;
import com.example.mamanguo.Retrofit.RetrofitClient;
import com.example.mamanguo.ui.Activities.MapViewActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private MyAdapter myAdapter;
    private RecyclerView myRecyclerView;
    private static String TAG = MainActivity.class.getSimpleName();
    private Button btn_history;
    private List<MamaNguo>listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mamanguo);
        btn_history = findViewById(R.id.button_viewHistory);

        //Create a handler for the RetrofitInstance interface//
        MamaNguoApi retrofitInstance = RetrofitClient.getRetrofitInstance().create(MamaNguoApi.class);
        Call<List<MamaNguo>> call = retrofitInstance.getMamaNguo();

        //Execute the request asynchronously//
        call.enqueue(new Callback<List<MamaNguo>>() {
            @Override
            //Handle a successful response//
            public void onResponse(Call<List<MamaNguo>> call, Response<List<MamaNguo>> response) {
                Log.d(TAG, "onResponse: Response Received");
                listData = response.body();
                loadDataList(listData);
            }

            @Override
            //Handle execution failures//
            public void onFailure(Call<List<MamaNguo>> call, Throwable throwable) {
                //If the request fails, then display the following toast//
                Log.d(TAG, String.format("onFailure: %s", throwable));
                Toast.makeText(MainActivity.this, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });
        attachListener();
    }

    //Display the retrieved data as a list//
    private void loadDataList(List<MamaNguo> usersList) {
        //Get a reference to the RecyclerView//
        myRecyclerView = findViewById(R.id.myRecyclerView);
        myAdapter = new MyAdapter(usersList);

        //Use a LinearLayoutManager with default vertical orientation//
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        myRecyclerView.setLayoutManager(layoutManager);

        //Set the Adapter to the RecyclerView//
        myRecyclerView.setAdapter(myAdapter);
    }

    private void attachListener() {
        /* On click listeners: */
        btn_history.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HistoryActivity.class);
            startActivity(intent);
        });

        myAdapter.setOnItemClickListener(position -> {
            String itemSelected = listData.get(position).getFullName();
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }
}