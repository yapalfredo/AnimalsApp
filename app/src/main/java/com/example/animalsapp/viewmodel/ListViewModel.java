package com.example.animalsapp.viewmodel;

import android.app.Application;
import android.app.backup.SharedPreferencesBackupHelper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalsapp.model.AnimalApiService;
import com.example.animalsapp.model.AnimalModel;
import com.example.animalsapp.model.ApiKeyModel;
import com.example.animalsapp.util.SharePreferencesHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {

    private AnimalApiService apiService = new AnimalApiService();

    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<List<AnimalModel>> animals = new MutableLiveData<List<AnimalModel>>();
    public MutableLiveData<Boolean> loadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    //flag
    private Boolean invalidApikey = false;

    private SharePreferencesHelper prefs;

    public ListViewModel(@NonNull Application application) {
        super(application);
        prefs = new SharePreferencesHelper(application);
    }


    public void refresh() {
        loading.setValue(true);
        invalidApikey = false;
        String key = prefs.getApiKey();
        if (key == null || key.equals("")) {
            getKey();
        } else {
            getAnimals(key);
        }
    }

    public void hardRefresh(){
        loading.setValue(true);
        getKey();
    }

    private void getKey() {
        disposable.add(
                apiService.getApiKey()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ApiKeyModel>() {
                            @Override
                            public void onSuccess(ApiKeyModel key) {
                                if (key.key.isEmpty()) {
                                    loadError.setValue(true);
                                    loading.setValue(false);
                                } else {
                                    prefs.saveApiKey(key.key);
                                    getAnimals(key.key);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                loading.setValue(false);
                                loadError.setValue(true);
                            }
                        }));
    }

    private void getAnimals(String key) {
        disposable.add(
                apiService.getAnimals(key)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<AnimalModel>>() {
                            @Override
                            public void onSuccess(List<AnimalModel> animalModels) {
                                loadError.setValue(false);
                                animals.setValue(animalModels);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (!invalidApikey) {
                                    invalidApikey = true;
                                    getKey();
                                } else {
                                    e.printStackTrace();
                                    loading.setValue(false);
                                    loadError.setValue(true);
                                }
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
