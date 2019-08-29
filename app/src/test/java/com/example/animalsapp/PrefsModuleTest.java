package com.example.animalsapp;

import android.app.Application;

import com.example.animalsapp.di.PrefsModule;
import com.example.animalsapp.util.SharePreferencesHelper;

public class PrefsModuleTest extends PrefsModule {

    SharePreferencesHelper mockPrefs;

    public PrefsModuleTest(SharePreferencesHelper mockPrefs)
    {
        this.mockPrefs = mockPrefs;
    }

    @Override
    public SharePreferencesHelper providesSharedPreferences(Application app) {
        return mockPrefs;
    }
}
