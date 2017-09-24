package com.example.app3.together_control;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.data_bean.Pond;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/18.
 */

public class TogetherAutomaticControlActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    Button btnUploadInterval;
    Button btnOxygenLimit;
    Button btnFeedingTime;
    TextView tv_show;
    Button btnSelectAll;
    Button btnCancelSelectedAll;
    Button btnDeselectAll;
    ListView lv;
    Button btnOpen;
    Button btnClose;
    private TogetherControlAdapter mAdapter;
    int checkNum;
    ArrayList<Pond> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.together_automatic_control_activity);

        btnUploadInterval= (Button) findViewById(R.id.btn_upload_time_interval);
        btnOxygenLimit= (Button) findViewById(R.id.btn_oxygen_limit);
        btnFeedingTime = (Button) findViewById(R.id.btn_feeding_time);

        tv_show = (TextView) findViewById(R.id.tv_show);
        btnSelectAll = (Button) findViewById(R.id.btn_select_all);
        btnCancelSelectedAll = (Button) findViewById(R.id.btn_cancel_select_all);
        btnDeselectAll = (Button) findViewById(R.id.btn_deselect_all);
        lv = (ListView) findViewById(R.id.pond_list);
        btnOpen = (Button) findViewById(R.id.btn_open);
        btnClose = (Button) findViewById(R.id.btn_close);

        list = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        String msg = bundle.getString("data");
        String[] data = (msg.substring(0, msg.length() - 1)).split(",");
        for (int i = 0; i < (data.length) / 3; i++) {
            Pond pond = new Pond();
            pond.setPid(Integer.valueOf(data[3 * i]));
            pond.setPondname(data[3 * i + 1]);
            pond.setPondaddress(data[3 * i + 2]);
            list.add(pond);
        }

        mAdapter = new TogetherControlAdapter(list, this);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(this);

        /**
         * 全选
         */
        btnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < list.size(); i++) {
                    TogetherControlAdapter.getIsSelected().put(i, true);
                }
                checkNum = list.size();
                dataChanged();
            }


        });

        /**
         * 反选
         */
        btnCancelSelectedAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    if (TogetherControlAdapter.getIsSelected().get(i)) {
                        TogetherControlAdapter.getIsSelected().put(i, false);
                        checkNum--;
                    } else {
                        TogetherControlAdapter.getIsSelected().put(i, true);
                        checkNum++;
                    }
                }
                dataChanged();
            }
        });

        /**
         * 取消选择
         */
        btnDeselectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    if (TogetherControlAdapter.getIsSelected().get(i)) {
                        TogetherControlAdapter.getIsSelected().put(i, false);
                        checkNum--;
                    }
                }
                dataChanged();
            }
        });

        /**
         * 上传时间间隔
         */
        btnUploadInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info ="";
                if(checkNum != 0){
                    for (int i = 0; i < list.size(); i++) {
                        if (TogetherControlAdapter.getIsSelected().get(i)) {
                            info+= list.get(i).getPid()+"%";
                        }
                    }

                    Intent intent=new Intent(TogetherAutomaticControlActivity.this,TogetherUploadTimeActivity.class);
                    intent.putExtra("data",info);
                    startActivity(intent);
                }else {
                    Toast.makeText(TogetherAutomaticControlActivity.this,"请选择鱼塘",Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * 溶氧值上下限
         */
        btnOxygenLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info="";
                if(checkNum != 0){
                    for (int i = 0; i < list.size(); i++) {
                        if (TogetherControlAdapter.getIsSelected().get(i)) {
                            info+= list.get(i).getPid()+"%";
                        }
                    }

                    Intent intent=new Intent(TogetherAutomaticControlActivity.this,TogetherControlOxygenLimitActivity.class);
                    intent.putExtra("data",info);
                    startActivity(intent);
                }else {
                    Toast.makeText(TogetherAutomaticControlActivity.this,"请选择鱼塘",Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * 投饲设置
         */
        btnFeedingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info ="";
                if(checkNum != 0){
                    for (int i = 0; i < list.size(); i++) {
                        if (TogetherControlAdapter.getIsSelected().get(i)) {
                            info+= list.get(i).getPid()+"%";
                        }
                    }

                    Intent intent=new Intent(TogetherAutomaticControlActivity.this,TogetherFeedingTimeControlActivity.class);
                    intent.putExtra("data",info);
                    startActivity(intent);
                }else {
                    Toast.makeText(TogetherAutomaticControlActivity.this,"请选择鱼塘",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void dataChanged() {
        mAdapter.notifyDataSetChanged();
        tv_show.setText("已选中" + checkNum + "项");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TogetherControlAdapter.ViewHolder holder = (TogetherControlAdapter.ViewHolder) view.getTag();
        holder.cb.toggle();
        TogetherControlAdapter.getIsSelected().put(position, holder.cb.isChecked());
        if (holder.cb.isChecked()) {
            checkNum++;
        } else {
            checkNum--;
        }
        tv_show.setText("已选中" + checkNum + "项");
    }
}
