package com.example.app3.together_control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app3.R;
import com.example.app3.base.BasePlaceAdapter;
import com.example.app3.data_bean.FeedingTime;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class FeedingTimeAdapter extends BaseAdapter {
    private List<FeedingTime> list;
    private Context context;

    public FeedingTimeAdapter(List<FeedingTime> list, Context context) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.feeding_time_list_item,null);
            holder=new ViewHolder();
            holder.tvTimeName= (TextView) convertView.findViewById(R.id.tv_item_time_name);
            holder.tvTimeStart= (TextView) convertView.findViewById(R.id.tv_item_start_time);
            holder.tvTimeEnd= (TextView) convertView.findViewById(R.id.tv_item_end_time);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tvTimeName.setText(list.get(position).getTimeName());
        holder.tvTimeStart.setText(list.get(position).getTimeStart());
        holder.tvTimeEnd.setText(list.get(position).getTimeEnd());
        return convertView;
    }

    class ViewHolder{
        private TextView tvTimeName;
        private TextView tvTimeStart;
        private TextView tvTimeEnd;
    }
}
