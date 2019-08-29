package com.example.animalsapp.di;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animalsapp.util.SharePreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.animalsapp.di.TypeOfContext.CONTEXT_ACTIVITY;
import static com.example.animalsapp.di.TypeOfContext.CONTEXT_APP;

@Module
public class PrefsModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    public SharePreferencesHelper providesSharedPreferences(Application app) {
        return new SharePreferencesHelper(app);
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    public SharePreferencesHelper providesActivitySharedPreferences(AppCompatActivity activity){
        return new SharePreferencesHelper(activity);
    }
}
