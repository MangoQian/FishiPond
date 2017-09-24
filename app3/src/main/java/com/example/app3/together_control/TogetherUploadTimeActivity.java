package com.example.app3.together_control;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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

public class TogetherUploadTimeActivity extends AppCompatActivity{
    EditText edtTimeInterval;
    Button btnTimeInterval;
    Button btnTimeDefault;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.together_upload_time_activity);

        edtTimeInterval= (EditText) findViewById(R.id.edit_time_interval);
        btnTimeInterval= (Button) findViewById(R.id.btn_time_interval);
        btnTimeDefault= (Button) findViewById(R.id.btn_time_default);

        /**
         * 确定选择时间间隔
         */
        btnTimeInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if("".equals(edtTimeInterval.getText().toString().trim())){
                   Toast.makeText(TogetherUploadTimeActivity.this,"请填写时间",Toast.LENGTH_LONG).show();
               }else {
                   Intent intent=getIntent();
                   String data=intent.getStringExtra("data");
                   String info="SetSomeTimeInterval@" + edtTimeInterval.getText().toString().trim() + "@" +data;
                   String msg = HttpConnection.getMessage(info);
                   if(msg.equals("nullok")){
                       Toast.makeText(TogetherUploadTimeActivity.this,"设置时间间隔成功",Toast.LENGTH_LONG).show();
                   }
               }

            }
        });

        /**
         * 时间间隔恢复默认值
         */
        btnTimeDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                String data=intent.getStringExtra("data");
                String info="SetSomeTimeDefault@" + 15 + "@" +data;
                String msg = HttpConnection.getMessage(info);
                if(msg.equals("nullok")){
                    Toast.makeText(TogetherUploadTimeActivity.this,"设置时间间隔成功",Toast.LENGTH_LONG).show();
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
