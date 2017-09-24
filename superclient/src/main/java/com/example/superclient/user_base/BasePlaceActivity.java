package com.example.superclient.user_base;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.superclient.R;
import com.example.superclient.data_bean.Base;
import com.example.superclient.http.HttpConnection;
import com.example.superclient.user_pond.FishpondActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/11.
 */

public class BasePlaceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayList<Base> list;
    BasePlaceAdapter mAdapter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        listView = (ListView) findViewById(R.id.base_list);

        uid = getIntent().getStringExtra("uid");
        String info = "QueryUserBase@" + uid;
        String msg = HttpConnection.getMessage(info);
        String[] received = msg.split("@");
        if (received[0].equals("nullok")) {
            String[] data = (received[1].substring(0, received[1].length() - 1)).split(",");

            list = new ArrayList<>();
            for (int i = 0; i < (data.length) / 3; i++) {
                Base base = new Base();
                base.setBid(Integer.valueOf(data[3 * i]));
                base.setBasename(data[3 * i + 1]);
                base.setAddress(data[3 * i + 2]);
                list.add(base);
            }

            mAdapter = new BasePlaceAdapter(list, this);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(this);
            listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(0, 0, 0, "添加");
                    menu.add(0, 1, 0, "修改");
//                    menu.add(0, 2, 0, "删除");
                }

            });
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent intent = new Intent(BasePlaceActivity.this, BaseAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("uid", Integer.parseInt(uid));
        switch (item.getItemId()) {
            case 0:
                bundle.putInt("itemId", 0);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 1:
                Base base = list.get(menuInfo.position);
                int bid = base.getBid();
                bundle.putInt("bid", bid);
                bundle.putInt("itemId", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Base base = list.get(position);
        Integer bid = base.getBid();
        String baseName = base.getAddress();
        Intent intent = new Intent(BasePlaceActivity.this, FishpondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("bid", bid);
        bundle.putString("basename", baseName);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
