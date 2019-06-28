package com.example.mamanguo.getAvailableMamaNguo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetMamaNguo {

    //Specify the request type and pass the relative URL//
    @GET("getAvailableMamaNguo")

    //Wrap the response in a Call object with the type of the expected result//
    Call<List<FetchMamaNguo>> getMamaNguo();
}
