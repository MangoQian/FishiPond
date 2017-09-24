package com.example.app3.device;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app3.R;
import com.example.app3.data_bean.Device;


import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 */

public class DeviceInfoAdapter extends BaseAdapter {
    private List<Device> list;
    private Context context;

    public DeviceInfoAdapter(List<Device> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_device_name = (TextView) convertView.findViewById(R.id.dev_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_device_name.setText(list.get(position).getDevicename());
        return convertView;
    }

    class ViewHolder {
        TextView tv_device_name;
    }
}
