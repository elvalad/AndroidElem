package com.example.app.androidelem;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/12/30.
 */
public class TrafficStatesTestActivity extends Activity {
    private static final int MSG_UPDATE_NET_SPEED = 0;
    private static final int MSG_UPDATE_MOBILE_TX_PACKETS = 1;
    private static final int MSG_UPDATE_MOBILE_RX_PACKETS = 2;
    private static final int MSG_UPDATE_MOBILE_TX_BYTES = 3;
    private static final int MSG_UPDATE_MOBILE_RX_BYTES = 4;
    private static final int MSG_UPDATE_TOTAL_TX_PACKETS = 5;
    private static final int MSG_UPDATE_TOTAL_RX_PACKETS = 6;
    private static final int MSG_UPDATE_TOTAL_TX_BYTES = 7;
    private static final int MSG_UPDATE_TOTAL_RX_BYTES = 8;
    private static final int MSG_HTTP_REQUEST_OK = 9;

    private TextView mobileTxPackets;
    private TextView mobileRxPackets;
    private TextView mobileTxBytes;
    private TextView mobileRxBytes;
    private TextView totalTxPackets;
    private TextView totalRxPackets;
    private TextView totalTxBytes;
    private TextView totalRxBytes;
    private TextView netSpeed;
    private Button startHttpRequest;
    private Button stopHttpRequest;
    private ImageView httpImage;
    private Timer timer = new Timer();
    private Thread thread;
    private boolean bRunning = false;
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    private String[] imageUrl = {
            "http://proof.nationalgeographic.com/files/2015/06/prod-yourshot-237031-5845692.jpg",
            "http://proof.nationalgeographic.com/files/2015/06/prod-yourshot-742217-5758939.jpg",
            "http://proof.nationalgeographic.com/files/2015/06/prod-yourshot-106943-5840433.jpg",
            "http://proof.nationalgeographic.com/files/2015/06/prod-yourshot-273064-5652045.jpg",
            "http://proof.nationalgeographic.com/files/2015/06/prod-yourshot-653777-5675135.jpg",
            "http://proof.nationalgeographic.com/files/2015/06/prod-yourshot-26900-5812605-c.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_states);

        mobileTxPackets = (TextView) findViewById(R.id.mobile_tx_packets);
        mobileRxPackets = (TextView) findViewById(R.id.mobile_rx_packets);
        mobileTxBytes = (TextView) findViewById(R.id.mobile_tx_bytes);
        mobileRxBytes = (TextView) findViewById(R.id.mobile_rx_bytes);

        totalTxPackets = (TextView) findViewById(R.id.total_tx_packets);
        totalRxPackets = (TextView) findViewById(R.id.total_rx_packets);
        totalTxBytes = (TextView) findViewById(R.id.total_tx_bytes);
        totalRxBytes = (TextView) findViewById(R.id.total_rx_bytes);

        netSpeed = (TextView) findViewById(R.id.net_speed);
        startShowNetSpeed();

        startHttpRequest = (Button) findViewById(R.id.start_http_request);
        startHttpRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bRunning = true;
                thread = new Thread(new HttpRequestThread());
                thread.start();
            }
        });

        stopHttpRequest = (Button) findViewById(R.id.stop_http_request);
        stopHttpRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread == null) return;
                bRunning = false;
                thread.interrupt();
                thread = null;
            }
        });

        httpImage = (ImageView) findViewById(R.id.http_download_image);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopShowNetSpeed();
    }

    private Handler m_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE_NET_SPEED:
                    netSpeed.setText((String) msg.obj);
                    break;
                case MSG_UPDATE_MOBILE_TX_PACKETS:
                    mobileTxPackets.setText((String) msg.obj);
                    break;
                case MSG_UPDATE_MOBILE_RX_PACKETS:
                    mobileRxPackets.setText((String) msg.obj);
                    break;
                case MSG_UPDATE_MOBILE_TX_BYTES:
                    mobileTxBytes.setText((String) msg.obj);
                    break;
                case MSG_UPDATE_MOBILE_RX_BYTES:
                    mobileRxBytes.setText((String) msg.obj);
                    break;
                case MSG_UPDATE_TOTAL_TX_PACKETS:
                    totalTxPackets.setText((String) msg.obj);
                    break;
                case MSG_UPDATE_TOTAL_RX_PACKETS:
                    totalRxPackets.setText((String) msg.obj);
                    break;
                case MSG_UPDATE_TOTAL_TX_BYTES:
                    totalTxBytes.setText((String) msg.obj);
                    break;
                case MSG_UPDATE_TOTAL_RX_BYTES:
                    totalRxBytes.setText((String) msg.obj);
                    break;
                case MSG_HTTP_REQUEST_OK:
                    Toast.makeText(TrafficStatesTestActivity.this.getApplicationContext(),
                            R.string.android_traffic_http_request, Toast.LENGTH_SHORT).show();
                    httpImage.setImageBitmap((Bitmap) msg.obj);
                    break;
            }
        }
    };

    private void showMobileTxPackets() {
        long mobileTxPackets = TrafficStats.getMobileTxPackets();
        Message msg = m_handler.obtainMessage();
        msg.what = MSG_UPDATE_MOBILE_TX_PACKETS;
        msg.obj = "Mobile Tx Packets : " + String.valueOf(mobileTxPackets);
        m_handler.sendMessage(msg);
    }

    private void showMobileRxPackets() {
        long mobileRxPackets = TrafficStats.getMobileRxPackets();
        Message msg = m_handler.obtainMessage();
        msg.what = MSG_UPDATE_MOBILE_RX_PACKETS;
        msg.obj = "Mobile Rx Packets : " + String.valueOf(mobileRxPackets);
        m_handler.sendMessage(msg);
    }

    private void showMobileTxBytes() {
        long mobileTxBytes = TrafficStats.getMobileTxBytes();
        Message msg = m_handler.obtainMessage();
        msg.what = MSG_UPDATE_MOBILE_TX_BYTES;
        msg.obj = "Mobile Tx Bytes : " + String.valueOf(mobileTxBytes);
        m_handler.sendMessage(msg);
    }

    private void showMobileRxBytes() {
        long mobileRxBytes = TrafficStats.getMobileRxBytes();
        Message msg = m_handler.obtainMessage();
        msg.what = MSG_UPDATE_MOBILE_RX_BYTES;
        msg.obj = "Mobile Rx Bytes : " + String.valueOf(mobileRxBytes);
        m_handler.sendMessage(msg);
    }

    private void showTotalTxPackets() {
        long totalTxPackets = TrafficStats.getTotalTxPackets();
        Message msg = m_handler.obtainMessage();
        msg.what = MSG_UPDATE_TOTAL_TX_PACKETS;
        msg.obj = "Total Tx Packets : " + String.valueOf(totalTxPackets);
        m_handler.sendMessage(msg);
    }

    private void showTotalRxPackets() {
        long totalRxPackets = TrafficStats.getTotalRxPackets();
        Message msg = m_handler.obtainMessage();
        msg.what = MSG_UPDATE_TOTAL_RX_PACKETS;
        msg.obj = "Total Rx Packets : " + String.valueOf(totalRxPackets);
        m_handler.sendMessage(msg);
    }

    private void showTotalTxBytes() {
        long totalTxBytes = TrafficStats.getTotalTxBytes();
        Message msg = m_handler.obtainMessage();
        msg.what = MSG_UPDATE_TOTAL_TX_BYTES;
        msg.obj = "Total Tx Bytes : " + String.valueOf(totalTxBytes);
        m_handler.sendMessage(msg);
    }

    private void showTotalRxBytes() {
        long totalRxBytes = TrafficStats.getTotalRxBytes();
        Message msg = m_handler.obtainMessage();
        msg.what = MSG_UPDATE_TOTAL_RX_BYTES;
        msg.obj = "Total Rx Bytes : " + String.valueOf(totalRxBytes);
        m_handler.sendMessage(msg);
    }

    private void showNetSpeed() {
        long nowTotalRxBytes = getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        Message msg = m_handler.obtainMessage();
        msg.what = MSG_UPDATE_NET_SPEED;
        msg.obj = "Net Speed : " + String.valueOf(speed) + " kb/s";
        m_handler.sendMessage(msg);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            showMobileTxPackets();
            showMobileRxPackets();
            showMobileTxBytes();
            showMobileRxBytes();
            showTotalTxPackets();
            showTotalRxPackets();
            showTotalTxBytes();
            showTotalRxBytes();
            showNetSpeed();
        }
    };

    private long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(this.getApplicationInfo().uid) ==
                TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes() / 1024);
    }

    public void startShowNetSpeed() {
        lastTotalRxBytes = getTotalRxBytes();
        lastTimeStamp = System.currentTimeMillis();
        timer.schedule(task, 1000, 2000);
    }

    public void stopShowNetSpeed() {
        timer.cancel();
        task.cancel();
    }

    class HttpRequestThread implements Runnable {
        @Override
        public void run() {
            int index = 0;
            InputStream in = null;
            HttpURLConnection conn = null;
            while (bRunning) {
                try {
                    // 创建一个URL对象
                    index = index % imageUrl.length;
                    URL url = new URL(imageUrl[index]);
                    index = index + 1;
                    // 利用HttpURLConnection对象从网络中获取网页数据
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    // 获取输入流，此时才真正建立链接
                    in = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    // 对响应码进行判断
                    if (conn.getResponseCode() == 200) {
                        Message msg = m_handler.obtainMessage();
                        msg.what = MSG_HTTP_REQUEST_OK;
                        msg.obj = bitmap;
                        m_handler.sendMessage(msg);
                    }
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }
    }
}
