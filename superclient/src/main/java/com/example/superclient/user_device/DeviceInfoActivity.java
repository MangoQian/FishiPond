package com.example.superclient.user_device;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.superclient.R;
import com.example.superclient.data_bean.Device;
import com.example.superclient.http.HttpConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 */

public class DeviceInfoActivity extends AppCompatActivity {
    ListView listView;
    List<Device> mList;
    String info = "";
    DeviceInfoAdapter mAdapter;
    int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_info_activity);

        listView = (ListView) findViewById(R.id.edv_list);

        Bundle bundle = getIntent().getExtras();
        pid = bundle.getInt("pid");

        info = "QueryDevice@" + pid;
        String msg = HttpConnection.getMessage(info);
        String[] received = msg.split("@");
        if (received[0].equals("nullok")) {
            String[] data = (received[1].substring(0, received[1].length() - 1)).split(",");

            for (int i = 0; i < data.length; i++) {
                Log.i("deviceInfo", data[i]);
            }
            mList = new ArrayList<>();
            for (int i = 0; i < data.length / 4; i++) {
                Device device = new Device();
                device.setDid(Integer.parseInt(data[4 * i]));
                device.setDevicecode(data[4 * i] + 1);
                device.setDevicename(data[4 * i + 2]);
                device.setDevicetype(Integer.parseInt(data[4 * i + 3]));
                mList.add(device);

            }
            mAdapter = new DeviceInfoAdapter(mList, this);
            listView.setAdapter(mAdapter);
        }

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "添加");
                menu.add(0, 1, 0, "修改");
//                menu.add(0, 2, 0, "删除");
            }

        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                Intent intent1 = new Intent(DeviceInfoActivity.this, DeviceAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("itemId", 0);
                bundle.putInt("pid", pid);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case 1:
                Intent intent2 = new Intent(DeviceInfoActivity.this, DeviceAddActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("itemId", 1);
                bundle1.putInt("did", mList.get(info.position).getDid());
                bundle1.putInt("pid", pid);
//                bundle1.putString("devicecode", mList.get(info.position).getDevicecode());
//                bundle1.putString("devicename", mList.get(info.position).getDevicename());
//                bundle1.putString("deviceaddress", mList.get(info.position).getDeviceaddress());
                intent2.putExtras(bundle1);
                startActivity(intent2);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

}
