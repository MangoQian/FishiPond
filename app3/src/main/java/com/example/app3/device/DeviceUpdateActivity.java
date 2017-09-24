package com.example.app3.device;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.http.HttpConnection;

/**
 * Created by Administrator on 2017/7/2.
 */

public class DeviceUpdateActivity extends AppCompatActivity {
    EditText edtDeviceName;
    Button btnDeviceConfirm;
    int itemId;
    int did;
    int pid;
    String deviceName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_add_activity);


        edtDeviceName = (EditText) findViewById(R.id.device_name);
        btnDeviceConfirm = (Button) findViewById(R.id.btn_device_confirm);

        Bundle bundle = getIntent().getExtras();
        itemId = bundle.getInt("itemId");
        pid = bundle.getInt("pid");
        if (itemId == 1) {
            did = bundle.getInt("did");
            String deviceName1 = bundle.getString("devicename");
            edtDeviceName.setText(deviceName1);
        }

        btnDeviceConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceName = edtDeviceName.getText().toString();
                if (itemId == 1) {
                    String info = "UpdatePartDevice@" + did + "@" + deviceName;
                    String msg = HttpConnection.getMessage(info);
                    if (msg.equals("nullok")) {
                        Toast.makeText(DeviceUpdateActivity.this, "装置修改成功 ", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}
