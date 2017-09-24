package com.example.app3.login;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/14.
 */

public class RegisterActivity extends AppCompatActivity {
    EditText reUsername;
    EditText rePassword;
    EditText reConfirmPassword;
    EditText reName;
    EditText rePhone;
    EditText reEmail;
    EditText reAddress;
    Button btnRegister;
    String strUsername;
    String strPassword;
    String strConfirmPass;
    String strPhone;
    String strEmail;
    String strName;
    String strAddress;
    String strTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        initView();
    }


    private void initView() {
        reUsername = (EditText) findViewById(R.id.register_username);
        rePassword = (EditText) findViewById(R.id.register_password);
        reConfirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        rePhone = (EditText) findViewById(R.id.register_mobile);
        reEmail = (EditText) findViewById(R.id.register_email);
        btnRegister = (Button) findViewById(R.id.btn_register);
        reName = (EditText) findViewById(R.id.register_name);
        reAddress = (EditText) findViewById(R.id.register_address);


        btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                strUsername = reUsername.getText().toString();
                strPassword = rePassword.getText().toString();
                strConfirmPass = reConfirmPassword.getText().toString();
                strPhone = rePhone.getText().toString();
                strEmail = reEmail.getText().toString();
                strName = reName.getText().toString();
                strAddress = reAddress.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                strTime = dateFormat.format(date);

                if (strUsername == null) {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (strPassword == null) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!((strPassword.trim()).equals(strConfirmPass.trim()))) {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (strPhone == null) {
                    Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (strEmail == null) {
                    Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String info = "Register@" + ReceivedFromMid();
                String msg = HttpConnection.getMessage(info);
                if (msg.equals("nullRegister")) {
                    Intent intent = new Intent(RegisterActivity.this, Login.class);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败，请重新注册", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public String ReceivedFromMid() {
        JSONObject jb = new JSONObject();
        try {
            jb.put("username", strUsername);
            jb.put("password", strPassword);
            jb.put("phone", strPhone);
            jb.put("email", strEmail);
            jb.put("name", strName);
            jb.put("address", strAddress);
            jb.put("time", strTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jb.toString();
    }
}
