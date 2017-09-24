package com.example.app3.together_control;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.data_bean.Pond;
import com.example.app3.http.HttpConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class TogetherManualControlActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ExpandableListView expandableListView;
    MyExpendableListAdapter mAdapter;
    TextView tv_show;
    Button btnSelectAll;
    Button btnCancelSelectedAll;
    Button btnDeselectAll;
    ListView lv;

    private TogetherControlAdapter mListAdapter;
    int checkNum;
    ArrayList<Pond> list;
    Handler handler = null;
    String type;
//    List<Integer> pondPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.together_manual_control_activity);

        expandableListView = (ExpandableListView) findViewById(R.id.expand_list);
        tv_show = (TextView) findViewById(R.id.tv_show);
        btnSelectAll = (Button) findViewById(R.id.btn_select_all);
        btnCancelSelectedAll = (Button) findViewById(R.id.btn_cancel_select_all);
        btnDeselectAll = (Button) findViewById(R.id.btn_deselect_all);
        lv = (ListView) findViewById(R.id.pond_list);

        list = new ArrayList<>();

        handler = new Handler();

        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        String msg = bundle.getString("data");
        String[] data = (msg.substring(0, msg.length() - 1)).split(",");
        for (int i = 0; i < (data.length) / 3; i++) {
            Pond pond = new Pond();
            pond.setPid(Integer.valueOf(data[3 * i]));
            pond.setPondname(data[3 * i + 1]);
            pond.setPondaddress(data[3 * i + 2]);
            list.add(pond);
        }

        expandableListView.setGroupIndicator(null);


        ArrayList<String> header = new ArrayList<String>();
        header.add("增氧机");
        header.add("投饲机");
        ArrayList<String> child1 = new ArrayList<>();
        child1.add("开");
        child1.add("关");
        ArrayList<String> child2 = new ArrayList<>();
        child2.add("开");
        child2.add("关");
        HashMap<String, ArrayList<String>> child = new HashMap<>();
        child.put(header.get(0), child1);
        child.put(header.get(1), child2);

        mAdapter = new MyExpendableListAdapter(this, header, child);
        expandableListView.setAdapter(mAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Object mAdapterChild = mAdapter.getChild(groupPosition, childPosition);
                Object group = mAdapter.getGroup(groupPosition);
                String info = "";
                if (checkNum == 0) {
                    Toast.makeText(TogetherManualControlActivity.this, "请选择鱼塘", Toast.LENGTH_LONG).show();
                    return false;
                }

                Toast.makeText(TogetherManualControlActivity.this, "选中的值为：" + group.toString() + mAdapterChild.toString(), Toast.LENGTH_LONG).show();
                if ("增氧机".equals(group.toString())) {
                    if ("开".equals(mAdapterChild.toString())) {
                        info = "OpenSomeAerator@";
                        for (int i = 0; i < list.size(); i++) {
                            if (TogetherControlAdapter.getIsSelected().get(i)) {
                                info += list.get(i).getPid() + "%";
//                                pondPosition = new ArrayList<Integer>();
//                                pondPosition.add(i);
                                Pond pond = list.get(i);
                                pond.setPondStatus("打开增氧机成功");
                                list.remove(i);
                                list.add(i, pond);
                                mListAdapter.notifyDataSetChanged();
                            }
                        }

                        String msg = HttpConnection.getMessage(info);
                        if (msg.equals("nullok")) {
//                            for (int i : pondPosition) {
//                                Pond pond = list.get(i);
//                                pond.setPondStatus("打开增氧机成功");
//                                list.remove(i);
//                                list.add(i, pond);
//                                mListAdapter.notifyDataSetChanged();
//                            }

                            Toast.makeText(TogetherManualControlActivity.this, "打开增氧机成功", Toast.LENGTH_SHORT).show();
                        }

                    } else if ("关".equals(mAdapterChild.toString())) {
                        info = "CloseSomeAerator@";
                        for (int i = 0; i < list.size(); i++) {
                            if (TogetherControlAdapter.getIsSelected().get(i)) {
                                info += list.get(i).getPid() + "%";
                                Pond pond = list.get(i);
                                pond.setPondStatus("关闭增氧机成功");
                                list.remove(i);
                                list.add(i, pond);
                                mListAdapter.notifyDataSetChanged();
                            }
                        }

                        String msg = HttpConnection.getMessage(info);
                        if (msg.equals("nullok")) {
                            Toast.makeText(TogetherManualControlActivity.this, "关闭增氧机成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if ("投饲机".equals(group.toString())) {
                    if ("开".equals(mAdapterChild.toString())) {
                        info = "OpenSomeFeeding@";
                        for (int i = 0; i < list.size(); i++) {
                            if (TogetherControlAdapter.getIsSelected().get(i)) {
                                info += list.get(i).getPid() + "%";
                                Pond pond = list.get(i);
                                pond.setPondStatus("打开投饲机成功");
                                list.remove(i);
                                list.add(i, pond);
                                mListAdapter.notifyDataSetChanged();
                            }
                        }

                        String msg = HttpConnection.getMessage(info);
                        if (msg.equals("nullok")) {
                            Toast.makeText(TogetherManualControlActivity.this, "打开投饲机成功", Toast.LENGTH_SHORT).show();
                        }
                    } else if ("关".equals(mAdapterChild.toString())) {
                        info = "CloseSomeFeeding@";
                        for (int i = 0; i < list.size(); i++) {
                            if (TogetherControlAdapter.getIsSelected().get(i)) {
                                info += list.get(i).getPid() + "%";
                                Pond pond = list.get(i);
                                pond.setPondStatus("关闭投饲机成功");
                                list.remove(i);
                                list.add(i, pond);
                                mListAdapter.notifyDataSetChanged();
                            }
                        }

                        String msg = HttpConnection.getMessage(info);
                        if (msg.equals("nullok")) {
                            Toast.makeText(TogetherManualControlActivity.this, "关闭投饲机成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                return true;
            }
        });


        mListAdapter = new TogetherControlAdapter(list, this);
        lv.setAdapter(mListAdapter);
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

    }


    private void dataChanged() {
        mListAdapter.notifyDataSetChanged();
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
