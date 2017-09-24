package com.example.app3.device;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.http.HttpConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/6/27.
 */

public class DeviceControl extends AppCompatActivity {
    RadioGroup rAerator;
    RadioGroup rFeeding;
    Button btnFeeding;
    Button btnAerator;
    String sendAerator;
    String sendFeeding;
    TextView tvControlLog;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_activity);

        rAerator = (RadioGroup) findViewById(R.id.radio_aerator);
        rFeeding = (RadioGroup) findViewById(R.id.radio_feeding);
        btnFeeding = (Button) findViewById(R.id.feeding_confirm);
        btnAerator = (Button) findViewById(R.id.aerator_confirm);
        tvControlLog = (TextView) findViewById(R.id.tv_control_log);

        Intent intent = getIntent();
        final String devicecode = intent.getStringExtra("devicecode");

        new Thread() {
            @Override
            public void run() {
                String info = "QueryControlLog@" + devicecode;
                String msg = HttpConnection.getMessage(info);
                String[] received = (msg.substring(0, msg.length() - 1)).split("@");
                String sendMsg = "";
                if (msg.equals("nolog")) {
                    Message message = handler.obtainMessage();
                    message.obj = 0;
                    handler.sendMessage(message);
                }

                if (received[0].equals("nullok")) {
                    for (int i = 1; i < received.length; i++) {
                        sendMsg += received[i] + "@";
                    }
                    Message message = handler.obtainMessage();
                    message.obj = sendMsg;
                    handler.sendMessage(message);
                } else {
                    Toast.makeText(DeviceControl.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = (String) msg.obj;
                if (result.equals("0")) {
                    tvControlLog.setText("");
                } else {
                    String[] received = (result.substring(0, result.length() - 1)).split("@");
                    for (int i = 0; i < received.length; i++) {
                        try {
                            JSONObject jsonObject = new JSONObject(received[i]);
                            tvControlLog.append("\n" + jsonObject.getString("children_dname") + "  " + jsonObject.getString("content") + "  " + jsonObject.getString("time"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };


        rAerator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sendAerator = "";
                switch (checkedId) {
                    case R.id.aerator_close:
                        sendAerator = "close";
                        break;
                    case R.id.aerator_open:
                        sendAerator = "open";
                        break;
                }
            }
        });

        btnAerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = "SetAerator@" + devicecode + "@" + sendAerator;
                String msg = HttpConnection.getMessage(info);
                if (msg.equals("nullok")) {
                    Toast.makeText(DeviceControl.this, "增氧机设置成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DeviceControl.this, "增氧机设置失败，请重新设置", Toast.LENGTH_SHORT).show();
                }

            }
        });

        rFeeding.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sendFeeding = "";
                switch (checkedId) {
                    case R.id.feeding_close:
                        sendFeeding = "close";
                        break;
                    case R.id.feeding_open:
                        sendFeeding = "open";
                        break;
                }
            }
        });

        btnFeeding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String info = "SetFeeding@" + devicecode + "@" + sendFeeding;
                String msg = HttpConnection.getMessage(info);
                if (msg.equals("nullok")) {
                    Toast.makeText(DeviceControl.this, "投饲机设置成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DeviceControl.this, "投饲机设置失败，请重新设置", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }
}
