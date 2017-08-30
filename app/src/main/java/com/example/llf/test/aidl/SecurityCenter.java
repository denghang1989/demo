package com.example.llf.test.aidl;

import android.os.RemoteException;

/**
 * 2017/8/29 23
 */
public class SecurityCenter extends ISecurityCenter.Stub {
    @Override
    public String encrypt(String msg) throws RemoteException {
        return null;
    }

    @Override
    public String dncrypt(String msg) throws RemoteException {
        return null;
    }
}
