package com.example.app.androidelem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private List<String> data = new ArrayList<>();
    private AndroidElemAdapter adapter;

    class AndroidElemAdapter extends BaseAdapter {
        private Context context;
        private List<String> data;
        private LayoutInflater inflater;
        private class ViewHolder {
            public TextView textView;
        }

        public AndroidElemAdapter(Context context, List<String> data) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.elem_list_item, parent, false);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.elem_list_id);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final Intent intent = new Intent();
            final String elemId = data.get(position);
            viewHolder.textView.setText(elemId);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            intent.setClass(MainActivity.this, HandlerPostActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent.setClass(MainActivity.this, HandlerMessageActivity.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent.setClass(MainActivity.this, AsyncTaskActivity.class);
                            startActivity(intent);
                            break;
                        case 3:
                            intent.setClass(MainActivity.this, NdkJniActivity.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent.setClass(MainActivity.this, TrafficStatesTestActivity.class);
                            startActivity(intent);
                            break;
                        case 5:
                            intent.setClass(MainActivity.this, CalendarViewActivity.class);
                            startActivity(intent);
                            break;
                        case 6:
                            intent.setClass(MainActivity.this, WebViewActivity.class);
                            startActivity(intent);
                            break;
                        case 7:
                            intent.setClass(MainActivity.this, MemoryLeakActivity.class);
                            startActivity(intent);
                            break;
                        case 8:
                            intent.setClass(MainActivity.this, ListInListActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.android_elem_list);
        getData();
        adapter = new AndroidElemAdapter(this, this.data);
        list.setAdapter(adapter);
    }

    private void getData() {
        data.add(getString(R.string.android_handler_post_demo));
        data.add(getString(R.string.android_handler_message_demo));
        data.add(getString(R.string.android_async_task));
        data.add(getString(R.string.android_ndk_jni_demo));
        data.add(getString(R.string.android_traffic_states_demo));
        data.add(getString(R.string.android_calendar_demo));
        data.add(getString(R.string.android_webview_demo));
        data.add(getString(R.string.android_memleak_demo));
        data.add(getString(R.string.android_list_in_list_demo));
    }
}
