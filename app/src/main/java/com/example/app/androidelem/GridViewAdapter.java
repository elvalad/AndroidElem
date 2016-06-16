package com.example.app.androidelem;

import android.widget.Button;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class GridViewAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<HashMap<String, Object>> mList;

    public GridViewAdapter(Context mContext,ArrayList<HashMap<String, Object>> mList) {
        super();
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return this.mList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mList == null) {
            return null;
        } else {
            return this.mList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.gridview_item, null, false);
            holder.button = (Button)convertView.findViewById(R.id.gridview_item_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.mList != null) {
            HashMap<String, Object> hashMap = this.mList.get(position);
            if (holder.button != null) {
                holder.button.setText(hashMap.get("content").toString());
                holder.button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "第"+(position+1)+"个", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        return convertView;
    }

    private class ViewHolder {
        Button button;
    }
}
