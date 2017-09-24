package com.example.superclient.user_info;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.superclient.R;
import com.example.superclient.http.HttpConnection;
import com.example.superclient.user_base.BasePlaceActivity;


/**
 * Created by Administrator on 2017/8/1.
 */

public class UserInfoDetail extends AppCompatActivity {
    TextView tv_username;
    TextView tv_name;
    TextView tv_address;
    TextView tv_email;
    TextView tv_phone;
    Button tv_status;
    Button tv_base;
    private String[] received;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        tv_username = (TextView) findViewById(R.id.tv_user_username);
        tv_name = (TextView) findViewById(R.id.tv_user_name);
        tv_address = (TextView) findViewById(R.id.tv_user_address);
        tv_email = (TextView) findViewById(R.id.tv_user_email);
        tv_phone = (TextView) findViewById(R.id.tv_user_phone);
        tv_status = (Button) findViewById(R.id.btn_user_status);
        tv_base = (Button) findViewById(R.id.btn_user_base);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String info = "QueryUserInfo@" + username;
        String msg = HttpConnection.getMessage(info);
        received = msg.split(",");
        if (received[0].equals("nullok")) {
            tv_username.setText(received[2]);
            tv_name.setText(received[3]);
            tv_address.setText(received[4]);
            tv_email.setText(received[5]);
            tv_phone.setText(received[6]);
            if (Integer.parseInt(received[7]) == 0) {
                tv_status.setText("未激活");
            } else {
                tv_status.setText("已激活");
            }
        }

        tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_status.getText().equals("未激活")) {
                    tv_status.setText("已激活");
                }
            }
        });

        tv_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UserInfoDetail.this, BasePlaceActivity.class);
                intent1.putExtra("uid", received[1]);
                startActivity(intent1);
            }
        });


    }
}
