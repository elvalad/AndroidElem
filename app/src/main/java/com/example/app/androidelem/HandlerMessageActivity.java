package com.example.app.androidelem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
public class HandlerMessageActivity extends Activity {

    private Button messageButton1;
    private Button messageButton2;
    private Button messageButton3;
    private ImageView imageView;
    private TextView textView;
    private ProgressDialog dialog;
    private static final int IS_FINISH = 1;
    private static final int HANDLER_MESSAGE_2 = 2;
    private static final int HANDLER_MESSAGE_3 = 3;
    private static final String image_path = "http://proof.nationalgeographic.com/files/2015/06/prod-yourshot-237031-5845692.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_message);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Waiting");
        dialog.setMessage("Downloading now!!!");
        dialog.setCancelable(false);
        imageView = (ImageView) findViewById(R.id.message_image);
        textView = (TextView) findViewById(R.id.message_text);

        messageButton1 = (Button) findViewById(R.id.message_button_01);
        messageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new MyThread()).start();
                dialog.show();
            }
        });

        messageButton2 = (Button) findViewById(R.id.message_button_02);
        messageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用Message.obtain + Handler.sendMessage发送消息
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        msg.what = HANDLER_MESSAGE_2;
                        msg.obj = getString(R.string.android_handler_message_obtain);
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });

        messageButton3 = (Button) findViewById(R.id.message_button_03);
        messageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain(handler);
                        msg.what = HANDLER_MESSAGE_3;
                        msg.obj = getString(R.string.android_handler_message_target);
                        msg.sendToTarget();
                    }
                }).start();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IS_FINISH:
                    byte[] data = (byte[])msg.obj;
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    imageView.setImageBitmap(bmp);
                    dialog.dismiss();
                    break;
                case HANDLER_MESSAGE_2:
                    textView.setText(msg.obj.toString());
                    textView.setTextColor(Color.RED);
                    break;
                case HANDLER_MESSAGE_3:
                    textView.setText(msg.obj.toString());
                    textView.setTextColor(Color.GREEN);
                    break;
            }
        }
    };

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
                    Message msg = Message.obtain();
                    msg.obj = data;
                    msg.what = IS_FINISH;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {

            }
        }
    }
}
