// ISecurityCenter.aidl
package com.example.llf.test.aidl;

// Declare any non-default types here with import statements

interface ISecurityCenter {
    String encrypt(String msg);
    String dncrypt(String msg);
}
