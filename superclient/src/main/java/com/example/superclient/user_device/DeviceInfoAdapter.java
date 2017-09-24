package com.example.superclient.user_device;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.superclient.R;
import com.example.superclient.data_bean.Device;

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
            convertView = LayoutInflater.from(context).inflate(R.layout.device_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_device_name = (TextView) convertView.findViewById(R.id.tv_device_name);
            viewHolder.tv_device_type = (TextView) convertView.findViewById(R.id.tv_device_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_device_name.setText(list.get(position).getDevicename());
        if (list.get(position).getDevicetype() == 0) {
            viewHolder.tv_device_type.setText("溶氧设备");
        } else if (list.get(position).getDevicetype() == 1) {
            viewHolder.tv_device_type.setText("增氧设备");
        } else if (list.get(position).getDevicetype() == 2) {
            viewHolder.tv_device_type.setText("投饲设备");
        }
//        viewHolder.tv_device_type.setText(list.get(position).getDevicetype() + "");
        return convertView;
    }

    class ViewHolder {
        TextView tv_device_name;
        TextView tv_device_type;
    }
}
