package com.example.app3.together_control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app3.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/19.
 */

public class MyExpendableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> header;
    private HashMap<String, ArrayList<String>> child;

    public MyExpendableListAdapter(Context context, ArrayList<String> header, HashMap<String, ArrayList<String>> child) {
        this.context = context;
        this.header = header;
        this.child = child;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 返回父列表个数
     *
     * @return
     */
    @Override
    public int getGroupCount() {
        return header.size();
    }

    /**
     * 返回子列表个数
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(header.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(header.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.header_item, null);
            groupHolder.tv_header = (TextView) convertView.findViewById(R.id.tv_header);
            groupHolder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();

        }
        groupHolder.tv_header.setText((CharSequence) getGroup(groupPosition));

        if (isExpanded) {
            groupHolder.img.setImageResource(R.drawable.ic_find_previous_holo_light);
        } else {
            groupHolder.img.setImageResource(R.drawable.ic_find_next_holo_light);
//            groupHolder.tv_header.setTypeface(null, Typeface.NORMAL);
//            groupHolder.tv_header.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_find_next_holo_light, 0);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
            childHolder = new ChildHolder();
            childHolder.tv_child = (TextView) convertView.findViewById(R.id.tv_child);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        childHolder.tv_child.setText((CharSequence) getChild(groupPosition, childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupHolder {
        TextView tv_header;
        ImageView img;
    }

    private class ChildHolder {
        TextView tv_child;
    }
}
