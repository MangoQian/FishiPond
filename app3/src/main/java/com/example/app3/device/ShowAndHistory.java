package com.example.app3.device;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.chart_bean.MyChart;
import com.example.app3.http.HttpConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2017/6/24.
 */

public class ShowAndHistory extends AppCompatActivity implements View.OnTouchListener {
    public TextView showTime;
    public EditText showOxygen;
    public EditText showAirTem;
    public EditText showWaterTem;
    public EditText showPressure;
    public EditText showPH;
    private LineChartView rongyangChart;
    private LineChartView qiwenChart;
    private LineChartView shuiwenChart;
    private LineChartView phChart;
    private EditText etStartTime;
    private EditText etEndTime;
    private Button btnQuery;
    String devicecode;

    List<Float> oxygenList = new ArrayList<>();
    List<Float> airTemList = new ArrayList<>();
    List<Float> waterTemList = new ArrayList<>();
    List<Float> pressureList = new ArrayList<>();
    List<Float> phList = new ArrayList<>();
    List<String> timeList = new ArrayList<>();


    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_and_history_activity);
        initView();
        doMethods();

    }

    private void initView() {
        showTime = (TextView) findViewById(R.id.tv_time);
        showOxygen = (EditText) findViewById(R.id.show_oxygen);
        showAirTem = (EditText) findViewById(R.id.show_air_tem);
        showWaterTem = (EditText) findViewById(R.id.show_water_tem);
        showPressure = (EditText) findViewById(R.id.show_pressure);
        showPH = (EditText) findViewById(R.id.show_PH);
        etStartTime = (EditText) findViewById(R.id.edit_date_start);
        etEndTime = (EditText) findViewById(R.id.edit_date_end);
        btnQuery = (Button) findViewById(R.id.btn_data_query);

        rongyangChart = (LineChartView) findViewById(R.id.line_chart);
        shuiwenChart = (LineChartView) findViewById(R.id.shuiwen_chart);
        qiwenChart = (LineChartView) findViewById(R.id.qiwen_chart);
        phChart = (LineChartView) findViewById(R.id.ph_chart);

        etStartTime.setOnTouchListener(this);
        etEndTime.setOnTouchListener(this);
    }

    private void doMethods() {

        Intent intent = getIntent();
        devicecode = intent.getStringExtra("devicecode");
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendMessage = "QueryPartData@" + devicecode + "@" + etStartTime.getText().toString() + "@" + etEndTime.getText().toString();
                String msg = HttpConnection.getMessage(sendMessage);

//                String[] received = (msg.substring(0, msg.length() - 1)).split("@");
                String[] received = msg.split("@");
                String sendMsg = "";
                if (received[0].equals("nullok")) {
                    for (int i = 1; i < received.length; i++) {
                        sendMsg += received[i] + "@";
                    }
                    Message message = handler.obtainMessage();
                    message.obj = sendMsg;
                    handler.sendMessage(message);
                } else {
                    Toast.makeText(ShowAndHistory.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                Calendar now = Calendar.getInstance();
//                StringBuffer sb = new StringBuffer();
//                sb.append(String.format("%d-%02d-%02d",
//                        now.get(Calendar.YEAR),
//                        (now.get(Calendar.MONTH) + 1),
//                        now.get(Calendar.DAY_OF_MONTH)));
//                info = "QueryDeviceDetail@" + devicecode + "@" + sb.toString();
//                String msg = HttpConnection.getMessage(info);
//                Log.i("DeviceInfo", msg);
//                if(msg.equals("nullfail")){
//                    Toast.makeText(ShowAndHistory.this, "没有数据", Toast.LENGTH_SHORT).show();
//                    showOxygen.setText("0");
//                    showAirTem.setText("0");
//                    showWaterTem.setText("0");
//                    showPressure.setText("0");
//                    showPH.setText("0");
//                    showTime.setText("0");
//
//                }else {
//                    String[] received = (msg.substring(0, msg.length() - 1)).split("@");
//                    String sendMsg = "";
//
//                    if (received[0].equals("nullok")) {
//                        for (int i = 1; i < received.length; i++) {
//                            sendMsg += received[i] + "@";
//                        }
//                        Message message = handler.obtainMessage();
//                        message.obj = sendMsg;
//                        handler.sendMessage(message);
//                    } else {
//                        Toast.makeText(ShowAndHistory.this, "网络连接失败", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        }.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = (String) msg.obj;
                String[] received = (result.substring(0, result.length() - 1)).split("@");
                JSONObject jb = null;
                try {
                    jb = new JSONObject(received[received.length - 1]);
                    showOxygen.setText(jb.getString("rongyangzhi"));
                    showAirTem.setText(jb.getString("qiwen"));
                    showWaterTem.setText(jb.getString("shuiwen"));
                    showPressure.setText(jb.getString("qiya"));
                    showPH.setText(jb.getString("ph"));
                    showTime.setText(jb.getString("sctime"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("device_info", received[0]);
                for (int i = 0; i < received.length; i++) {
                    try {
                        jb = new JSONObject(received[i]);
                        oxygenList.add(Float.parseFloat(jb.getString("rongyangzhi")));
                        airTemList.add(Float.parseFloat(jb.getString("qiwen")));
                        waterTemList.add(Float.parseFloat(jb.getString("shuiwen")));
                        pressureList.add(Float.parseFloat(jb.getString("qiya")));
                        phList.add(Float.parseFloat(jb.getString("ph")));
                        timeList.add(jb.getString("sctime"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                MyChart myChart = new MyChart(timeList, oxygenList, rongyangChart);
                myChart.initLineChart("溶氧值");

                MyChart airTemChart = new MyChart(timeList, airTemList, qiwenChart);
                airTemChart.initLineChart("气温");

                MyChart waterTemChart = new MyChart(timeList, waterTemList, shuiwenChart);
                waterTemChart.initLineChart("水温");

                MyChart pHChart = new MyChart(timeList, phList, phChart);
                pHChart.initLineChart("ph值");

            }
        };


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.date_dialog, null);
            final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
            builder.setView(view);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

            switch (v.getId()) {
                case R.id.edit_date_start:
                    int inType = etStartTime.getInputType();
                    etStartTime.setInputType(InputType.TYPE_NULL);
                    etStartTime.onTouchEvent(event);
                    etStartTime.setInputType(inType);
                    etStartTime.setSelection(etStartTime.getText().length());

                    builder.setTitle("选取起始时间");
                    builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StringBuffer sb = new StringBuffer();
                            sb.append(String.format("%d-%02d-%02d",
                                    datePicker.getYear(),
                                    (datePicker.getMonth() + 1),
                                    datePicker.getDayOfMonth()));
                            etStartTime.setText(sb);

                            etEndTime.requestFocus();

                            dialog.cancel();
                        }
                    });
                    break;
                case R.id.edit_date_end:
                    int inType1 = etEndTime.getInputType();
                    etEndTime.setInputType(InputType.TYPE_NULL);
                    etEndTime.onTouchEvent(event);
                    etEndTime.setInputType(inType1);
                    etEndTime.setSelection(etEndTime.getText().length());

                    builder.setTitle("选取结束时间");
                    builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StringBuffer sb = new StringBuffer();
                            sb.append(String.format("%d-%02d-%02d",
                                    datePicker.getYear(),
                                    (datePicker.getMonth() + 1),
                                    datePicker.getDayOfMonth()));
                            etEndTime.setText(sb);

                            dialog.cancel();
                        }
                    });
                    break;
                default:
                    break;
            }
            Dialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }
}
