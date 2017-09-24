package com.example.superclient.user_pond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.superclient.R;
import com.example.superclient.data_bean.Pond;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/17.
 */

public class FishpondAdapter extends BaseAdapter {
    private ArrayList<Pond> list;
    private Context context;

    public FishpondAdapter(ArrayList<Pond> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.pond_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_fishpond_name = (TextView) convertView.findViewById(R.id.fishpond_name_list);
            viewHolder.tv_fishpond_address = (TextView) convertView.findViewById(R.id.fishpond_address_list);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_fishpond_name.setText(list.get(position).getPondname());
        viewHolder.tv_fishpond_address.setText(list.get(position).getPondaddress());
        return convertView;
    }

    class ViewHolder {
        TextView tv_fishpond_name;
        TextView tv_fishpond_address;
    }
}
