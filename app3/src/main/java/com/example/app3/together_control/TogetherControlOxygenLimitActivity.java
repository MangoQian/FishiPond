package com.example.app3.together_control;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.data_bean.Pond;
import com.example.app3.http.HttpConnection;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/14.
 */

public class TogetherControlOxygenLimitActivity extends AppCompatActivity{
    EditText edtStartOxygen;
    EditText edtEndOxygen;
    Button btnOxygenConfirm;
    Button btnOxygenDefault;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.together_control_oxygen_limit_activity);

        edtStartOxygen = (EditText) findViewById(R.id.edt_start_oxygen);
        edtEndOxygen = (EditText) findViewById(R.id.edt_end_oxygen);
        btnOxygenConfirm = (Button) findViewById(R.id.btn_oxygen_confirm);
        btnOxygenDefault = (Button) findViewById(R.id.btn_oxygen_default);


        /**
         * 确定溶氧值的上下限
         */
        btnOxygenConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(edtStartOxygen.getText().toString().trim())) {
                    Toast.makeText(TogetherControlOxygenLimitActivity.this, "上限不能为空", Toast.LENGTH_LONG).show();
                }
                if ("".equals(edtEndOxygen.getText().toString().trim())) {
                    Toast.makeText(TogetherControlOxygenLimitActivity.this, "下限不能为空", Toast.LENGTH_LONG).show();
                }
                Intent intent=getIntent();
                String data=intent.getStringExtra("data");
                String info="SetSomeOxygenLimit@" + edtStartOxygen.getText().toString().trim() + "@"
                        + edtEndOxygen.getText().toString().trim()+"@" +data;
                String msg = HttpConnection.getMessage(info);
                if(msg.equals("nullok")){
                    Toast.makeText(TogetherControlOxygenLimitActivity.this,"设置溶氧值上下限",Toast.LENGTH_LONG).show();
                }

            }
        });

        /**
         * 恢复溶氧值上下限的默认值
         */
        btnOxygenDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                String data=intent.getStringExtra("data");
                String info="SetSomeOxygenLimitDefualt@" + data;
                String msg = HttpConnection.getMessage(info);
                if(msg.equals("nullok")){
                    Toast.makeText(TogetherControlOxygenLimitActivity.this,"设置时间间隔成功",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
