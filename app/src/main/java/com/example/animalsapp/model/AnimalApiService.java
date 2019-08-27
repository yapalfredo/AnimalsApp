package com.example.animalsapp.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalApiService {

    private static final String BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net/";

    AnimalApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AnimalApi.class);

    public Single<ApiKeyModel> getApiKey(){
        return  api.getApiKey();
    }

    public  Single<List<AnimalModel>> getAnimals(String key){
        return api.getAnimals(key);
    }
}
