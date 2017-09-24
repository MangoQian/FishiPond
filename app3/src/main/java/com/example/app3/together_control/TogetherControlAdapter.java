package com.example.app3.together_control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.app3.R;
import com.example.app3.data_bean.Pond;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/10.
 */

public class TogetherControlAdapter extends BaseAdapter {
    private ArrayList<Pond> list;
    private Context context;
    private LayoutInflater inflater = null;
    private static HashMap<Integer, Boolean> isSelected;

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        TogetherControlAdapter.isSelected = isSelected;
    }

    public TogetherControlAdapter(ArrayList<Pond> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();

        initData();
    }

    private void initData() {
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i,false);
        }
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.together_aerator_control_adapter, null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.item_tv);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
            viewHolder.tv_status= (TextView) convertView.findViewById(R.id.tv_device_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(list.get(position).getPondname());
        viewHolder.tv_status.setText(list.get(position).getPondStatus());
        viewHolder.cb.setChecked(getIsSelected().get(position));
        //
        return convertView;
    }
    public static class ViewHolder {
        TextView tv;
        CheckBox cb;
        TextView tv_status;
    }
}
