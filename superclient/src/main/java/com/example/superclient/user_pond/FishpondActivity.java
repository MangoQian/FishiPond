package com.example.superclient.user_pond;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.superclient.R;
import com.example.superclient.data_bean.Pond;
import com.example.superclient.http.HttpConnection;
import com.example.superclient.user_device.DeviceInfoActivity;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/6/15.
 */

public class FishpondActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    FishpondAdapter mAdapter;
    String info;
    ArrayList<Pond> list;
    String bid;
    String address;
    String[] received;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fishpond_activity);

        listView = (ListView) findViewById(R.id.list);

        Bundle bundle = getIntent().getExtras();
        bid = bundle.getInt("bid") + "";
        info = "QueryPond@" + bid;

        String msg = HttpConnection.getMessage(info);
        received = msg.split("@");
        if (received[0].equals("nullok")) {
            String[] data = (received[1].substring(0, received[1].length() - 1)).split(",");
            list = new ArrayList<>();
            for (int i = 0; i < (data.length) / 3; i++) {
                Pond fishpond = new Pond();
                fishpond.setPid(Integer.valueOf(data[3 * i]));
                fishpond.setPondname(data[3 * i + 1]);
                fishpond.setPondaddress(data[3 * i + 2]);
                list.add(fishpond);
            }
            mAdapter = new FishpondAdapter(list, this);
            listView.setOnItemClickListener(this);
            listView.setAdapter(mAdapter);

            listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(0, 0, 0, "添加");
                    menu.add(0, 1, 0, "修改");
//                    menu.add(0, 2, 0, "删除");
                }

            });

        } else if (msg.equals("nullnopond")) {
            Toast.makeText(FishpondActivity.this, "没有鱼塘", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case 0:
                Intent intent2 = new Intent(FishpondActivity.this, PondAddActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("itemId", 0);
                bundle1.putInt("bid", Integer.parseInt(bid));
                intent2.putExtras(bundle1);
                startActivity(intent2);
                Toast.makeText(FishpondActivity.this, list.get(menuInfo.position).getPondname(), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Pond pond = list.get(menuInfo.position);
                int pid = pond.getPid();
                Intent intent1 = new Intent(FishpondActivity.this, PondAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("itemId", 1);
                bundle.putInt("bid", Integer.parseInt(bid));
                bundle.putInt("pid", pid);
                intent1.putExtras(bundle);
                startActivity(intent1);
                Toast.makeText(FishpondActivity.this, list.get(menuInfo.position).getPondname(), Toast.LENGTH_SHORT).show();
                break;
//            case 2:
//                final Pond pond = list.get(menuInfo.position);
//                new AlertDialog.Builder(FishpondActivity.this).setTitle("提示")
//                        .setMessage("确定删除该鱼塘？")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                Integer pid = pond.getPid();
//                                String send = "DeletePond@" + pid;
//                                String msg = HttpConnection.getMessage(send);
//                                list.remove(menuInfo.position);
//                                mAdapter.notifyDataSetChanged();
//                                listView.invalidate();
//                                if (msg.equals("nullok")) {
//                                    Toast.makeText(FishpondActivity.this, "删除" + pond.getPondname() + "成功", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }).show();
//                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Integer pid = list.get(position).getPid();
//        String pondaddress = list.get(position).getPondaddress();
        Intent intent = new Intent(FishpondActivity.this, DeviceInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("pid", pid);
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(FishpondActivity.this, "fishpond id = " + pid, Toast.LENGTH_SHORT).show();
    }


}
