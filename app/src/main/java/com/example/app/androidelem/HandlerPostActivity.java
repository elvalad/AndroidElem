package com.example.app.androidelem;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Administrator on 2015/12/28.
 */
public class HandlerPostActivity extends Activity {

    private Button postButton1;
    private Button postButton2;
    private Button postButton3;
    private Button postButton4;
    private TextView textView;
    private ImageView imageView;
    private ProgressDialog dialog;
    private static Handler handler = new Handler();
    private static final String image_path = "http://proof.nationalgeographic.com/files/2015/06/prod-yourshot-237031-5845692.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_post);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Waiting");
        dialog.setMessage("Downloading now!!!");
        dialog.setCancelable(false);
        textView = (TextView) findViewById(R.id.post_text);
        imageView = (ImageView) findViewById(R.id.post_image);
        postButton1 = (Button) findViewById(R.id.post_button_01);
        postButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动一个子线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 使用post方式在子线程中通知UI线程更新UI组件
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Handler post demo : post");
                                textView.setTextColor(Color.RED);
                            }
                        });
                    }
                }).start();
            }
        });

        postButton2 = (Button) findViewById(R.id.post_button_02);
        postButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.postAtTime(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Handler post demo : postAtTime");
                                textView.setTextColor(Color.GREEN);
                            }
                        }, System.currentTimeMillis() + 1);
                    }
                }).start();
            }
        });

        postButton3 = (Button) findViewById(R.id.post_button_03);
        postButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Handler post demo : postDelayed");
                                textView.setTextColor(Color.BLUE);
                            }
                        }, 3000);
                    }
                }).start();
            }
        });

        postButton4 = (Button) findViewById(R.id.post_button_04);
        postButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new MyThread()).start();
                dialog.show();
            }
        });
    }

    class MyThread implements Runnable {
        @Override
        public void run() {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(image_path);
            HttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    byte[] data = EntityUtils.toByteArray(httpResponse.getEntity());
                    final Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bmp);
                        }
                    });
                    dialog.dismiss();
                }
            } catch (Exception e) {

            }
        }
    }
}
