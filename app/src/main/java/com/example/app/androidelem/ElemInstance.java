package com.example.app.androidelem;

/**
 * Created by Administrator on 2016/6/14.
 */
public class ElemInstance {

    private static ElemInstance instance = null;

    private ElemInstance() {}

    public static ElemInstance getInstance() {
        if (instance == null) {
            synchronized (ElemInstance.class) {
                if (instance == null) {
                    instance = new ElemInstance();
                }
            }
        }
        return instance;
    }

    public void doSomething() {

    }
}
