package com.example.app.androidelem;

/**
 * Created by Administrator on 2015/12/29.
 */
public class NdkJniUtils {
    public native String getCLanguageString();

    public native long recursiveNative(int n);

    static {
        System.loadLibrary("AndroidElemJniLibName");
    }
}
