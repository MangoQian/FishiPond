package com.example.superclient.user_base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.superclient.R;
import com.example.superclient.data_bean.Base;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/11.
 */

public class BasePlaceAdapter extends BaseAdapter {
    private ArrayList<Base> list;
    private Context context;

    public BasePlaceAdapter(ArrayList<Base> list, Context context) {
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
        ViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.base_list_item,null);
            holder=new ViewHolder();
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_base_name);
            holder.tv_address= (TextView) convertView.findViewById(R.id.tv_base_address);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(list.get(position).getBasename());
        holder.tv_address.setText(list.get(position).getAddress());
        return convertView;
    }

    public class ViewHolder{
        TextView tv_name;
        TextView tv_address;
    }
}
