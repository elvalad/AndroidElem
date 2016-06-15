package com.example.app.androidelem;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/14.
 */
public class MemoryLeakActivity extends Activity {

    private TextView memText;
    private Button memBtn2;
    private static boolean bMemLeak = false;
    private static MemLeakTest memLeakTest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak);
        memLeak01();
        memLeak02();
        memLeak02_ok();
    }

    private void memLeak01() {
        Button memBtn1 = (Button) findViewById(R.id.mem_btn_1);
        memText = (TextView) findViewById(R.id.mem_text);
        memBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncTask();
                if (bMemLeak) {
                    memText.setTextColor(Color.GREEN);
                    bMemLeak = false;
                } else {
                    memText.setTextColor(Color.RED);
                    bMemLeak = true;
                }
            }
        });
    }

    // 避免在activity里面实例化其非静态内部类的静态实例
    private void memLeak02() {
        if (null == memLeakTest) {
            memLeakTest = new MemLeakTest();
            memLeakTest.doSomething();
        }
    }

    private void memLeak02_ok() {
        ElemInstance.getInstance().doSomething();
    }

    private void startAsyncTask() {
        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>() {
            @Override protected Void doInBackground(Void... params) {
                // Do some slow work in background
                SystemClock.sleep(20000);
                return null;
            }
        }.execute();
    }

    class MemLeakTest {

        public void doSomething() {
            Toast.makeText(MemoryLeakActivity.this, "memory leak", Toast.LENGTH_LONG).show();
        }
    }
}
