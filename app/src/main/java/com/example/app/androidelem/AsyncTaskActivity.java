package com.example.app.androidelem;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/12/28.
 */
public class AsyncTaskActivity extends Activity {

    private Button asyncTaskButton;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        textView = (TextView) findViewById(R.id.async_task_text);
        progressBar = (ProgressBar) findViewById(R.id.async_task_progressbar);
        asyncTaskButton = (Button) findViewById(R.id.async_task_button);
        asyncTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBarAsyncTask asyncTask = new ProgressBarAsyncTask(textView, progressBar);
                asyncTask.execute(1000);
            }
        });
    }

    class ProgressBarAsyncTask extends AsyncTask {
        private TextView textView;
        private ProgressBar progressBar;

        public ProgressBarAsyncTask(TextView textView, ProgressBar progressBar) {
            this.textView = textView;
            this.progressBar = progressBar;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            int i;
            for (i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return i + params[0].toString() + "";
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            textView.setText("异步操作执行结束");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setText("开始执行异步线程");
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
            int value = (int) values[0];
            progressBar.setProgress(value);
            textView.setText("已经完成:" + value + "%");
        }
    }
}
