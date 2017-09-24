package com.example.app3.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.base.BasePlaceActivity;
import com.example.app3.http.HttpConnection;


/**
 * Created by Administrator on 2017/3/20.
 */

public class Login extends AppCompatActivity {
    Button btnLogin;
    EditText username;
    EditText password;
    String strUser;
    String strPassword;
    String info = null;
    CheckBox rememberPass;
    TextView register;
    //记住密码
    SharedPreferences pref;
    SharedPreferences.Editor editor;
//    ConnectivityManager connectivityManager;
//    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

//        connectivityManager = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
//        networkInfo = connectivityManager.getActiveNetworkInfo();

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");

        initView();
    }

    public void initView() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        register = (TextView) findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Login.this, RegisterActivity.class);
                startActivity(intent1);
            }
        });

        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String account = pref.getString("account", "");
            String pass = pref.getString("pass", "");
            username.setText(account);
            password.setText(pass);
            rememberPass.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (networkInfo == null || !networkInfo.isAvailable()) {
//                    Toast.makeText(Login.this, "网络不可用，请检查网络状态", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                strUser = username.getText().toString().trim();
                strPassword = password.getText().toString().trim();

                if (strUser.isEmpty()) {
                    Toast.makeText(Login.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (strPassword.isEmpty()) {
                    Toast.makeText(Login.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if (strUser.equals("zhu") && strPassword.equals("123")) {
////                    String info ="Super@";
////                    Intent intent1 = new Intent(Login.this, PondAddressActivity.class);
////                    startActivity(intent1);
//                } else {
                    info = "LoginIn@" + strUser + "@" + strPassword;
                    String msg = HttpConnection.getMessage(info);
                    Log.i("Login", msg);
                    String[] received = msg.split("@");
                    if (received[0].equals("nullok")) {
                        editor = pref.edit();

                        if (rememberPass.isChecked()) {
                            editor.putBoolean("remember_password", true);
                            editor.putString("account", strUser);
                            editor.putString("pass", strPassword);
                        } else {
                            editor.clear();
                        }
                        editor.apply();

                        Intent intent = new Intent(Login.this, BasePlaceActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("uid", received[1]);
                        bundle.putInt("type", 0);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else if (msg.equals("nullfail")) {
                        Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
//                }

            }
        });

    }

}
