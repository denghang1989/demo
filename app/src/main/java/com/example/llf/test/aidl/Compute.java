package com.example.llf.test.aidl;

import android.os.RemoteException;

/**
 * 2017/8/29 23
 */
public class Compute extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
