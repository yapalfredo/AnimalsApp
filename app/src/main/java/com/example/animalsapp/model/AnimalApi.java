package com.example.animalsapp.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AnimalApi {

    @GET("getKey")
    public Single<ApiKeyModel> getApiKey();

    @FormUrlEncoded
    @POST("getAnimals")
    public Single<List<AnimalModel>> getAnimals(@Field("key") String key);
}
