package com.example.superclient.user_info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.superclient.R;
import com.example.superclient.data_bean.User;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class UserInfoAdapter extends BaseAdapter {
    List<User> list;
    Context context;

    public UserInfoAdapter(List<User> list, Context context) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.user_info_adpter, null);
            holder.tv_user_username = (TextView) convertView.findViewById(R.id.tv_user_username);
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tv_user_status = (TextView) convertView.findViewById(R.id.tv_user_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_user_username.setText(list.get(position).getUsername());
        holder.tv_user_name.setText(list.get(position).getName());
        if(list.get(position).getStatus() == 1 ){
            holder.tv_user_status.setText("已激活");
        }else if(list.get(position).getStatus() == 0 ){
            holder.tv_user_status.setText("未激活");
        }
//        holder.tv_user_status.setText(list.get(position).getStatus() + "");
        return convertView;
    }

    class ViewHolder {
        TextView tv_user_username;
        TextView tv_user_name;
        TextView tv_user_status;
    }
}
