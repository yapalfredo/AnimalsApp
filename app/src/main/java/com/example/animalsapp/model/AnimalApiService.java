package com.example.animalsapp.model;

import com.example.animalsapp.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class AnimalApiService {

    @Inject
    AnimalApi api;

    public AnimalApiService() {

        //Dagger
        DaggerApiComponent.create().inject(this);
    }

    public Single<ApiKeyModel> getApiKey() {
        return api.getApiKey();
    }

    public Single<List<AnimalModel>> getAnimals(String key) {
        return api.getAnimals(key);
    }
}
