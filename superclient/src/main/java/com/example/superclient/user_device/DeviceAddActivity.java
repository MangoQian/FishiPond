package com.example.superclient.user_device;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.superclient.R;
import com.example.superclient.http.HttpConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class DeviceAddActivity extends AppCompatActivity {
    EditText edtDeviceCode;
    EditText edtDeviceName;
    EditText edtDeviceAddress;
    EditText edtDeviceType;
    Button btnDeviceConfirm;
//    LinearLayout linearLayout;
    int itemId;
    int did;
    int pid;
    String deviceCode;
    String deviceName;
    String deviceAddress;
    int deviceType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_add_activity);

        edtDeviceCode = (EditText) findViewById(R.id.device_code);
        edtDeviceName = (EditText) findViewById(R.id.device_name);
        edtDeviceType= (EditText) findViewById(R.id.device_type);

        edtDeviceAddress = (EditText) findViewById(R.id.device_address);
        btnDeviceConfirm = (Button) findViewById(R.id.btn_device_confirm);
//        linearLayout = (LinearLayout) findViewById(R.id.layout_devicecode);

        Bundle bundle = getIntent().getExtras();
        itemId = bundle.getInt("itemId");
        pid = bundle.getInt("pid");
        did = bundle.getInt("did");

        if (itemId == 1) {
            String info = "QueryDeviceInfo@"+ did;
            String msg = HttpConnection.getMessage(info);
            String[] received = msg.split("@");
            if(received[0].equals("nullok")){
                edtDeviceCode.setText(received[1]);
                edtDeviceName.setText(received[2]);
                if(received[3].equals("0")){
                    edtDeviceType.setText("溶氧设备");
                }else if(received[3].equals("1")){
                    edtDeviceType.setText("增氧设备");
                }else if(received[3].equals("2")){
                    edtDeviceType.setText("投饲设备");
                }
                edtDeviceAddress.setText(received[4]);
            }

        }

        btnDeviceConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceCode = edtDeviceCode.getText().toString();
                deviceName = edtDeviceName.getText().toString();
                deviceAddress = edtDeviceAddress.getText().toString();
                if(deviceName.equals("溶氧设备")){
                    deviceType = 0;
                }else if(deviceName.equals("增氧设备")){
                    deviceType = 1;
                }else if(deviceName.equals("投饲设备")){
                    deviceType = 2;
                }
                Toast.makeText(DeviceAddActivity.this, deviceName, Toast.LENGTH_SHORT).show();
                if (itemId == 0) {
                    String info = "AddDevice@" + pid + "@" + deviceCode + "@" + deviceName + "@"+ deviceType + "@" + deviceAddress;
                    String msg = HttpConnection.getMessage(info);
                    if (msg.equals("nullok")) {
                        Toast.makeText(DeviceAddActivity.this, "装置添加成功 ", Toast.LENGTH_SHORT).show();
                    }
                } else if (itemId == 1) {
                    String info = "UpdateDevice@" + did + "@" + deviceCode + "@" + deviceName + "@"+ deviceType + "@" + deviceAddress;
                    String msg = HttpConnection.getMessage(info);
                    if (msg.equals("nullok")) {
                        Toast.makeText(DeviceAddActivity.this, "装置修改成功 ", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}
