package com.example.superclient.user_info;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.superclient.R;
import com.example.superclient.data_bean.User;
import com.example.superclient.http.HttpConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/30.
 */

public class UserInfoList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<User> list;
    ListView listView;
    UserInfoAdapter mAdapter;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_list_activity);

        listView = (ListView) findViewById(R.id.lv_user_info);

        String info = "QueryUsers@all";
        String msg = HttpConnection.getMessage(info);
        Log.i("Users","users" + msg);
        String[] received = msg.split("@");
        if (received[0].equals("nullok")) {
            String[] data = (received[1].substring(0, received[1].length() - 1)).split(",");
            list = new ArrayList<>();
            for (int i = 0; i < (data.length) / 3; i++) {
                User user = new User();
                user.setUsername(data[3 * i]);
                user.setName(data[3 * i + 1]);
                user.setStatus(Integer.parseInt(data[3 * i + 2]));
                list.add(user);
            }
            mAdapter = new UserInfoAdapter(list, this);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(this);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(UserInfoList.this, UserInfoDetail.class);
        intent.putExtra("username", list.get(position).getUsername());
        startActivity(intent);
    }
}
