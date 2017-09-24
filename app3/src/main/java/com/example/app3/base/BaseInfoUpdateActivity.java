package com.example.app3.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.http.HttpConnection;

/**
 * Created by Administrator on 2017/8/19.
 */

public class BaseInfoUpdateActivity extends AppCompatActivity {
    private EditText editBaseName;
    private EditText editBaseIntro;
    Button btnBaseIntro;
    String bid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_update_activity);

        editBaseName = (EditText) findViewById(R.id.edt_base_name);
        editBaseIntro = (EditText) findViewById(R.id.edt_base_intro);
        btnBaseIntro = (Button) findViewById(R.id.btn_base_intro);

        Intent intent = getIntent();
        bid = intent.getStringExtra("bid");
        String info = "QueryBaseInfo@" + bid;
        String msg = HttpConnection.getMessage(info);
        String[] received = msg.split("@");

        if (received[0].equals("nullok")) {
            editBaseName.setText(received[1]);
            editBaseIntro.setText(received[2]);
        }

        btnBaseIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaseInfoUpdateActivity.this, editBaseIntro.getText().toString(), Toast.LENGTH_SHORT).show();
                String name = editBaseName.getText().toString();
                String intro = editBaseIntro.getText().toString();
                String info1 = "UpdateUserBase@" + bid + "@" + name + "@" + intro;
                String msg = HttpConnection.getMessage(info1);
                if (msg.equals("nullok")) {
                    Toast.makeText(BaseInfoUpdateActivity.this, "Update Success!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
