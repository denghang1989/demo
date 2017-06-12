package com.example.llf.test;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * @date 2017/6/9 19
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
