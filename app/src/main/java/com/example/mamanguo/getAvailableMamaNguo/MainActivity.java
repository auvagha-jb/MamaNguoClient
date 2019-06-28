package com.example.mamanguo.getAvailableMamaNguo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.mamanguo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MyAdapter myAdapter;
    private RecyclerView myRecyclerView;
    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_recycler);

        //Create a handler for the RetrofitInstance interface//
        GetMamaNguo service = RetrofitClient.getRetrofitInstance().create(GetMamaNguo.class);

        Call<List<FetchMamaNguo>> call = service.getMamaNguo();

        //Execute the request asynchronously//
        call.enqueue(new Callback<List<FetchMamaNguo>>() {
            @Override
            //Handle a successful response//
            public void onResponse(Call<List<FetchMamaNguo>> call, Response<List<FetchMamaNguo>> response) {
                Log.d(TAG, "onResponse: Response Received");
                loadDataList(response.body());
            }

            @Override
            //Handle execution failures//
            public void onFailure(Call<List<FetchMamaNguo>> call, Throwable throwable) {
                //If the request fails, then display the following toast//
                Log.d(TAG, String.format("onFailure: %s", throwable));
                Toast.makeText(MainActivity.this, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display the retrieved data as a list//
    private void loadDataList(List<FetchMamaNguo> usersList) {
        //Get a reference to the RecyclerView//
        myRecyclerView = findViewById(R.id.myRecyclerView);
        myAdapter = new MyAdapter(usersList);

        //Use a LinearLayoutManager with default vertical orientation//
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        myRecyclerView.setLayoutManager(layoutManager);

        //Set the Adapter to the RecyclerView//
        myRecyclerView.setAdapter(myAdapter);
    }

}