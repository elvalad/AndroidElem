package com.example.app.androidelem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/12/29.
 */
public class NdkJniActivity extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk_jni);
        textView = (TextView) findViewById(R.id.ndk_jni_text);

        NdkJniUtils jniUtils = new NdkJniUtils();
        textView.setText(jniUtils.getCLanguageString() + jniUtils.recursiveNative(10));
    }
}
