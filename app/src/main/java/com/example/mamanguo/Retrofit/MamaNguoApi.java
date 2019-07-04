package com.example.mamanguo.Retrofit;

import com.example.mamanguo.getAvailableMamaNguo.MamaNguo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MamaNguoApi {

    //Display available Mamanguo
    @GET("getAvailableMamaNguo")
    Call<List<MamaNguo>> getMamaNguo();

    //Registration validation
    @POST("emailExists")
    Call <User> emailExists(String email);

    //Registration
    @POST("addUser")
    Call <User> addUser(@Body User user);

    //Login
    @POST("userLogin")
    Call <User> userLogin(@Body User user);
}
