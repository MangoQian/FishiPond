package com.example.app3.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.app3.R;
import com.example.app3.data_bean.Base;
import com.example.app3.http.HttpConnection;
import com.example.app3.pond.FishpondActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/11.
 */

public class BasePlaceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayList<Base> list;
    BasePlaceAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        listView = (ListView) findViewById(R.id.base_list);

        Bundle bundle = getIntent().getExtras();
        String uid = bundle.getString("uid");
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
                    menu.add(0, 1, 0, "修改");
                }
            });
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                Base base= list.get(info.position);
                int bid = base.getBid();
                Intent bassUpdate= new Intent(BasePlaceActivity.this,BaseInfoUpdateActivity.class);
                bassUpdate.putExtra("bid",bid + "");
                startActivity(bassUpdate);
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
