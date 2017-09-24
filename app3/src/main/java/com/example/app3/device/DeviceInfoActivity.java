package com.example.app3.device;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.data_bean.Device;
import com.example.app3.http.HttpConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 */

public class DeviceInfoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    List<Device> mList;
    String info = "";
    DeviceInfoAdapter mAdapter;
    int fishpondId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edv_activity);

        listView = (ListView) findViewById(R.id.edv_list);
        Bundle bundle = getIntent().getExtras();
        fishpondId = bundle.getInt("pid");
        info = "QueryDevice@" + fishpondId;
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
                device.setDid(Integer.parseInt(data[4*i]));
                device.setDevicecode(data[4 * i+1]);
                device.setDevicename(data[4 * i + 2]);
                device.setDevicetype(Integer.parseInt(data[4 * i + 3]));
                mList.add(device);

            }
            mAdapter = new DeviceInfoAdapter(mList, this);
            listView.setOnItemClickListener(this);
            listView.setAdapter(mAdapter);
        }

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                menu.add(0, 0, 0, "添加");
                menu.add(0, 1, 0, "修改");
//                menu.add(0, 2, 0, "删除");
            }

        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            case 1:
                Intent intent2 = new Intent(DeviceInfoActivity.this, DeviceUpdateActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("itemId", 1);
                bundle1.putInt("did", mList.get(info.position).getDid());
                bundle1.putInt("pid",fishpondId);
//                bundle1.putString("devicecode", mList.get(info.position).getDevicecode());
                bundle1.putString("devicename", mList.get(info.position).getDevicename());
//                bundle1.putString("deviceaddress", mList.get(info.position).getDeviceaddress());
                intent2.putExtras(bundle1);
                startActivity(intent2);
                break;

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String devicecode = mList.get(position).getDevicecode();
        Toast.makeText(DeviceInfoActivity.this, "devicecode :" + devicecode, Toast.LENGTH_SHORT).show();
        int devicetype = mList.get(position).getDevicetype();
        switch (devicetype) {
            case 0:
                Intent intent1 = new Intent(DeviceInfoActivity.this, ShowAndHistory.class);
                intent1.putExtra("devicecode", devicecode);
                startActivity(intent1);
                break;
            case 1:
                Intent intent = new Intent(DeviceInfoActivity.this, DeviceControl.class);
                intent.putExtra("devicecode", devicecode);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
