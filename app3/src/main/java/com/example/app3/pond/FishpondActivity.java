package com.example.app3.pond;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.data_bean.Pond;
import com.example.app3.device.DeviceInfoActivity;
import com.example.app3.http.HttpConnection;
import com.example.app3.together_control.TogetherAutomaticControlActivity;
import com.example.app3.together_control.TogetherManualControlActivity;

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
    Button btnManualControl;
    Button btnAutomaticControl;
    String[] received;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fishpond_activity);

        listView = (ListView) findViewById(R.id.list);
        btnManualControl = (Button) findViewById(R.id.btn_manual_control);
        btnAutomaticControl= (Button) findViewById(R.id.btn_automatic_control);

        Bundle bundle = getIntent().getExtras();
        bid = bundle.getInt("bid") +"";
        info = "QueryPond@" + bid;
//        int type = bundle.getInt("type");
//        switch (type) {
//            case 0:
//                bid = bundle.getInt("bid") +"";
//                info = "QueryPond@" + bid;
//                break;
//            case 1:
//                break;
//            case 2:
//                address = bundle.getString("address");
//                info = "QueryPondByAddress@" + address;
//                break;
//            default:
//                break;
//        }

        String msg = HttpConnection.getMessage(info);
        received = msg.split("@");
        if (received[0].equals("nullok")) {

            String[] data = (received[1].substring(0, received[1].length() - 1)).split(",");

            list = new ArrayList<>();
            for (int i = 0; i < (data.length) / 3; i++) {
                Pond fishpond = new Pond();
                fishpond.setPid(Integer.valueOf(data[3 * i]));
                fishpond.setPondname(data[3 * i + 1]);
                fishpond.setPondIntro(data[3 * i + 2]);
                list.add(fishpond);
            }
            mAdapter = new FishpondAdapter(list, this);
            listView.setOnItemClickListener(this);
            listView.setAdapter(mAdapter);

//            if (type == 0) {
                listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                        menu.add(0, 0, 0, "添加");
                        menu.add(0, 1, 0, "修改");
//                        menu.add(0, 2, 0, "删除");
                    }

                });
//            }
        }else if(msg.equals("nullnopond")){
            Toast.makeText(FishpondActivity.this,"没有鱼塘",Toast.LENGTH_SHORT).show();
        }

        /**
         * 手动控制
         */
        btnManualControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(FishpondActivity.this,TogetherManualControlActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("data",received[1]);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
        /**
         * 自动控制
         */
        btnAutomaticControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(FishpondActivity.this,TogetherAutomaticControlActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("data",received[1]);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case 1:
                Pond pond = list.get(info.position);
                String pondname = pond.getPondname();
                Intent intent1 = new Intent(FishpondActivity.this, PondInfoUpdateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("itemId", 1);
                bundle.putInt("pid", pond.getPid());
                bundle.putString("pondname", pondname);
                bundle.putString("pondintro",pond.getPondIntro());
                intent1.putExtras(bundle);
                startActivity(intent1);
                Toast.makeText(FishpondActivity.this, list.get(info.position).getPondname(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Integer pid = list.get(position).getPid();
        Intent intent = new Intent(FishpondActivity.this, DeviceInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("pid", pid);
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(FishpondActivity.this, "fishpond id = " + pid, Toast.LENGTH_SHORT).show();
    }


}
