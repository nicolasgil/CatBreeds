package com.app.catbreeds.network;

import com.app.catbreeds.model.ModelLanding;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GetDataService {

    @Headers("x-api-key: bda53789-d59e-46cd-9bc4-2936630fde39")
    @GET("v1/breeds")
    Call<List<ModelLanding>> getAll();
}
