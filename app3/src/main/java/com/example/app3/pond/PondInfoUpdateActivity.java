package com.example.app3.pond;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.http.HttpConnection;
import com.example.app3.province_datebase.Area;
import com.example.app3.province_datebase.DBHepler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class PondInfoUpdateActivity extends AppCompatActivity {
    EditText edtPondName;
    EditText edtPondIntro;
    Button btnAddress;
    int itemId;
    int pid;
    int bid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pond_update_activity);

        edtPondName = (EditText) findViewById(R.id.edt_pond_name);
        edtPondIntro= (EditText) findViewById(R.id.edt_pond_intro);
        btnAddress = (Button) findViewById(R.id.btn_address);

        Bundle bundle = getIntent().getExtras();
        itemId = bundle.getInt("itemId");
        bid = bundle.getInt("bid");
        if (itemId == 1) {
            edtPondName.setText(bundle.getString("pondname"));
            edtPondIntro.setText(bundle.getString("pondintro"));
            pid = bundle.getInt("pid");
        }

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pondName = edtPondName.getText().toString();
                String pondIntro=edtPondIntro.getText().toString();
//                if (itemId == 0) {
//                    String info = "AddPond@" + bid + "@" + pondName + "@" + address;
//                    String msg = HttpConnection.getMessage(info);
//                    if (msg.equals("nullok")) {
//                        Toast.makeText(PondInfoUpdateActivity.this, "鱼塘添加成功 ", Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.makeText(PondInfoUpdateActivity.this, "pondName: " + pondName + "  address: " + address, Toast.LENGTH_SHORT).show();
//                } else
                if (itemId == 1) {
                    String info = "UpdatePond@" + pid + "@" + pondName + "@" + pondIntro;
                    String msg = HttpConnection.getMessage(info);
                    if (msg.equals("nullok")) {
                        Toast.makeText(PondInfoUpdateActivity.this, "鱼塘修改成功 ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });




    }
}
