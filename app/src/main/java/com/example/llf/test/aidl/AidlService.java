package com.example.llf.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 2017/8/29 23
 */
public class AidlService extends Service {
    private IBinder mIBinder = new BinderPool();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

}
