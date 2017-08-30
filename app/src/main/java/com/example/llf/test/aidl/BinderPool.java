package com.example.llf.test.aidl;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * 2017/8/29 23
 */
public class BinderPool extends IBinderPool.Stub {
    public static final int Add    = 1;
    public static final int STRING = 2;

    public BinderPool() {
    }

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder iBinder = null;
        switch (binderCode) {
            case Add:
                iBinder = new Compute();
                break;
            case STRING:
                iBinder = new SecurityCenter();
                break;
        }

        return iBinder;
    }
}
