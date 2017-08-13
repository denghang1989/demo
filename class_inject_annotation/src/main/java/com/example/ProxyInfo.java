package com.example;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * 2017/8/13 08
 */
public class ProxyInfo {
    private Elements    mElements;
    private TypeElement mTypeElement;
    public Map<Integer,VariableElement> mInjectElement = new HashMap<>();
    private CharSequence mProxyClassFullName;

    public ProxyInfo(Elements elementUtils, TypeElement typeElement) {
        mElements = elementUtils;
        mTypeElement = typeElement;
    }

    public CharSequence getProxyClassFullName() {
        return mProxyClassFullName;
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }

    public int generateJavaCode() {
        return 0;
    }
}
