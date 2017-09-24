package com.example.superclient.user_base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.superclient.R;
import com.example.superclient.http.HttpConnection;
import com.example.superclient.province_datebase.Area;
import com.example.superclient.province_datebase.DBHelper;
import com.example.superclient.user_pond.PondAddActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/30.
 */

public class BaseAddActivity extends AppCompatActivity {
    EditText edtBaseName;
    EditText edtBaseIntro;
    Spinner provinceSpinner;
    Spinner citySpinner;
    Spinner districtSpinner;
    ArrayAdapter<String> provinceAdapter;
    ArrayAdapter<String> cityAdapter;
    ArrayAdapter<String> districtAdapter;
    List<String> cityName;
    List<String> districtName;
    String strProvince;
    String strCity;
    String strDistrict;
    String address;
    Button btnBaseAdd;
    int itemId;
    int uid;
    int bid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_add_activity);

        edtBaseName = (EditText) findViewById(R.id.edt_base_name);
        edtBaseIntro = (EditText) findViewById(R.id.edt_base_intro);
        provinceSpinner = (Spinner) findViewById(R.id.spinner_province);
        citySpinner = (Spinner) findViewById(R.id.spinner_city);
        districtSpinner = (Spinner) findViewById(R.id.spinner_district);
        btnBaseAdd = (Button) findViewById(R.id.btn_base_add);

        Bundle bundle = getIntent().getExtras();
        itemId = bundle.getInt("itemId");
        uid = bundle.getInt("uid");

        if (itemId == 1) {
            bid = bundle.getInt("bid");
            String info = "QueryBaseInfo@"+ bid;
            String msg = HttpConnection.getMessage(info);
            String received[]= msg.split("@");
            edtBaseName.setText(received[1]);
            edtBaseIntro.setText(received[2]);
        }

        final DBHelper dbHelper = new DBHelper(this);
        List<String> provinceName = new ArrayList<>();
        final ArrayList<Area> province = dbHelper.getProvince();
        for (int i = 0; i < province.size(); i++) {
            provinceName.add(province.get(i).getName());
        }

        address = "";
        provinceSpinner.setPrompt("请选择省份");
        provinceAdapter = new ArrayAdapter<>(BaseAddActivity.this, android.R.layout.simple_spinner_item, provinceName);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final ArrayList<Area> city = dbHelper.getCity(province.get(position).getCode());
                strProvince = "";
                strProvince = province.get(position).getName();

                citySpinner.setPrompt("请选择城市");
                cityName = new ArrayList<>();
                for (int i = 0; i < city.size(); i++) {
                    cityName.add(city.get(i).getName());
                    Log.i("City", cityName.get(i));
                }
                cityAdapter = new ArrayAdapter<>(BaseAddActivity.this, android.R.layout.simple_spinner_item, cityName);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(cityAdapter);
                citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        final ArrayList<Area> district = dbHelper.getDistrict(city.get(position).getCode());
                        strCity = "";
                        strCity = city.get(position).getName();

                        districtSpinner.setPrompt("请选择地区");
                        districtName = new ArrayList<>();
                        for (int i = 0; i < district.size(); i++) {
                            districtName.add(district.get(i).getName());
                        }
                        districtAdapter = new ArrayAdapter<>(BaseAddActivity.this, android.R.layout.simple_spinner_item, districtName);
                        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSpinner.setAdapter(districtAdapter);
                        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strDistrict = "";
                                strDistrict = district.get(position).getName();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBaseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = strProvince + strCity + strDistrict;
                String baseName = edtBaseName.getText().toString();
                String baseIntro = edtBaseIntro.getText().toString();

                if (itemId == 0) {
                    String info = "AddBase@" + uid + "@" + baseName + "@" + baseIntro + "@" + address;
                    String msg = HttpConnection.getMessage(info);
                    if (msg.equals("nullok")) {
                        Toast.makeText(BaseAddActivity.this, "基地添加成功 ", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(BaseAddActivity.this, "baseName: " + baseName + "  address: " + address, Toast.LENGTH_SHORT).show();
                } else if (itemId == 1) {
                    String info = "UpdateUserBase@" + bid + "@" + baseName + "@" + baseIntro + "@" + address;
                    String msg = HttpConnection.getMessage(info);
                    if (msg.equals("nullok")) {
                        Toast.makeText(BaseAddActivity.this, "基地修改成功 ", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(BaseAddActivity.this, "baseName: " + baseName + "  address: " + address, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
