package com.example.app3.together_control;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.data_bean.FeedingTime;
import com.example.app3.http.HttpConnection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */

public class TogetherFeedingTimeControlActivity extends AppCompatActivity implements View.OnTouchListener {
    LinearLayout linearLayout;
    ListView lv;
    EditText edtTimeName;
    EditText edtTimeStart;
    EditText edtTimeEnd;
    ImageButton imgButton;
    List<FeedingTime> list;
    FeedingTimeAdapter mAdapter;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.together_feeding_time_control_activity);

        linearLayout= (LinearLayout) findViewById(R.id.linear_layout);
        lv= (ListView) findViewById(R.id.list_time);
        edtTimeName= (EditText) findViewById(R.id.edt_time_name);
        edtTimeStart= (EditText) findViewById(R.id.edt_time_start);
        edtTimeEnd= (EditText) findViewById(R.id.edt_time_end);
        imgButton= (ImageButton) findViewById(R.id.img_button);
        list=new ArrayList<>();

        btnConfirm = (Button) findViewById(R.id.btn_time_confirm);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTimeName.getText().toString().trim().equals("")){
                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"请填写名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edtTimeStart.getText().toString().trim().equals("")){
                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"请开始选择时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edtTimeEnd.getText().toString().trim().equals("")){
                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"请选择结束时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                FeedingTime feedingTime=new FeedingTime();
                feedingTime.setTimeName(edtTimeName.getText().toString());
                feedingTime.setTimeStart(edtTimeStart.getText().toString());
                feedingTime.setTimeEnd(edtTimeEnd.getText().toString());
                list.add(feedingTime);
                mAdapter=new FeedingTimeAdapter(list,TogetherFeedingTimeControlActivity.this);
                lv.setAdapter(mAdapter);

                if (linearLayout.getVisibility()==View.VISIBLE){
                    linearLayout.setVisibility(View.GONE);
                }
                Intent intent=getIntent();
                String data=intent.getStringExtra("data");
                String info="SetFeedingTime@" + data +"@"+ edtTimeStart.getText().toString() +"@"+ edtTimeEnd.getText().toString();
                String msg = HttpConnection.getMessage(info);
                if(msg.equals("nullok")){
                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"设置时间间隔成功",Toast.LENGTH_LONG).show();
                }

//                StringBuffer sb = new StringBuffer();
//                Calendar c = Calendar.getInstance();
//                sb.append("开始时间："
//                        + edtMorningStart.getText().toString()
//                        + "   结束时间："
//                        + edtMorningEnd.getText().toString()
//                        + "\n"
//                        + "开始时间："
//                        + edtNoonStart.getText().toString()
//                        + "   结束时间："
//                        + edtNoonEnd.getText().toString()
//                        + "\n"
//                        + "开始时间："
//                        + edtEveningStart.getText().toString()
//                        + "   结束时间："
//                        + edtEveningEnd.getText().toString()
//                        + "\n"
//                        + "当前时间："
//                        + c.get(Calendar.HOUR_OF_DAY)
//                        + ":"
//                        + c.get(Calendar.MINUTE));
//
//                Toast.makeText(TogetherFeedingTimeControlActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
//
//                if("".equals(edtMorningStart.getText().toString().trim())){
//                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"请选择第一次投饲开始时间",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if("".equals(edtMorningEnd.getText().toString().trim())){
//                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"请选择第一次投饲结束时间",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if("".equals(edtNoonStart.getText().toString().trim())){
//                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"请选择第二次投饲开始时间",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if("".equals(edtNoonEnd.getText().toString().trim())){
//                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"请选择第二次投饲结束时间",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if("".equals(edtEveningStart.getText().toString().trim())){
//                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"请选择第三次投饲开始时间",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if("".equals(edtEveningEnd.getText().toString().trim())){
//                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"请选择第三次投饲结束时间",Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                Intent intent=getIntent();
//                String data=intent.getStringExtra("data");
//                String info = "SetFeedingTime@" + data + "@"
//                        + edtMorningStart.getText().toString().trim() + "@"
//                        + edtMorningEnd.getText().toString().trim() +  "@"
//                        + edtNoonStart.getText().toString().trim() + "@"
//                        + edtNoonEnd.getText().toString().trim() + "@"
//                        + edtEveningStart.getText().toString().trim() + "@"
//                        + edtEveningEnd.getText().toString().trim();
//                String msg = HttpConnection.getMessage(info);
//                if(msg.equals("nullok")){
//                    Toast.makeText(TogetherFeedingTimeControlActivity.this,"设置时间间隔成功",Toast.LENGTH_LONG).show();
//                }

            }
        });

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayout.getVisibility()==View.GONE){
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.time_dialog, null);
            final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
            timePicker.setIs24HourView(true);
            builder.setView(view);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));

            switch (v.getId()) {
//                case R.id.edit_morning_time_start:
//                    int inType = edtMorningStart.getInputType();
//                    edtMorningStart.setInputType(InputType.TYPE_NULL);
//                    edtMorningStart.onTouchEvent(event);
//                    edtMorningStart.setInputType(inType);
//                    edtMorningStart.setSelection(edtMorningStart.getText().length());
//
//                    builder.setTitle("选取起始时间");
//                    builder.setPositiveButton("确   定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                                StringBuffer sb = new StringBuffer();
//                                sb.append(String.format("%02d:%02d",
//                                        timePicker.getHour(),
//                                        timePicker.getMinute()));
//                                edtMorningStart.setText(sb);
//                            }
//                            edtMorningEnd.requestFocus();
//                            dialog.cancel();
//                        }
//                    });
//                    break;
//                case R.id.edit_morning_time_end:
//                    int inType1 = edtMorningEnd.getInputType();
//                    edtMorningEnd.setInputType(InputType.TYPE_NULL);
//                    edtMorningEnd.onTouchEvent(event);
//                    edtMorningEnd.setInputType(inType1);
//                    edtMorningEnd.setSelection(edtMorningEnd.getText().length());
//
//                    builder.setTitle("选取起始时间");
//                    builder.setPositiveButton("确   定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                                StringBuffer sb = new StringBuffer();
//                                sb.append(String.format("%02d:%02d",
//                                        timePicker.getHour(),
//                                        timePicker.getMinute()));
//                                edtMorningEnd.setText(sb);
//                            }
//                            edtNoonStart.requestFocus();
//                            dialog.cancel();
//                        }
//                    });
//                    break;
//                case R.id.edit_noon_time_start:
//                    int inType2 = edtNoonStart.getInputType();
//                    edtNoonStart.setInputType(InputType.TYPE_NULL);
//                    edtNoonStart.onTouchEvent(event);
//                    edtNoonStart.setInputType(inType2);
//                    edtNoonStart.setSelection(edtNoonStart.getText().length());
//
//                    builder.setTitle("选取起始时间");
//                    builder.setPositiveButton("确   定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                                StringBuffer sb = new StringBuffer();
//                                sb.append(String.format("%02d:%02d",
//                                        timePicker.getHour(),
//                                        timePicker.getMinute()));
//                                edtNoonStart.setText(sb);
//                            }
//                            edtNoonEnd.requestFocus();
//                            dialog.cancel();
//                        }
//                    });
//                    break;
//                case R.id.edit_noon_time_end:
//                    int inType3 = edtNoonEnd.getInputType();
//                    edtNoonEnd.setInputType(InputType.TYPE_NULL);
//                    edtNoonEnd.onTouchEvent(event);
//                    edtNoonEnd.setInputType(inType3);
//                    edtNoonEnd.setSelection(edtNoonEnd.getText().length());
//
//                    builder.setTitle("选取起始时间");
//                    builder.setPositiveButton("确   定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                                StringBuffer sb = new StringBuffer();
//                                sb.append(String.format("%02d:%02d",
//                                        timePicker.getHour(),
//                                        timePicker.getMinute()));
//                                edtNoonEnd.setText(sb);
//                            }
//                            edtEveningStart.requestFocus();
//                            dialog.cancel();
//                        }
//                    });
//                    break;
//                case R.id.edit_evening_time_start:
//                    int inType4 = edtEveningStart.getInputType();
//                    edtEveningStart.setInputType(InputType.TYPE_NULL);
//                    edtEveningStart.onTouchEvent(event);
//                    edtEveningStart.setInputType(inType4);
//                    edtEveningStart.setSelection(edtEveningStart.getText().length());
//
//                    builder.setTitle("选取起始时间");
//                    builder.setPositiveButton("确   定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                                StringBuffer sb = new StringBuffer();
//                                sb.append(String.format("%02d:%02d",
//                                        timePicker.getHour(),
//                                        timePicker.getMinute()));
//                                edtEveningStart.setText(sb);
//                            }
//                            edtEveningEnd.requestFocus();
//                            dialog.cancel();
//                        }
//                    });
//                    break;
//                case R.id.edit_evening_time_end:
//                    int inType5 = edtEveningEnd.getInputType();
//                    edtEveningEnd.setInputType(InputType.TYPE_NULL);
//                    edtEveningEnd.onTouchEvent(event);
//                    edtEveningEnd.setInputType(inType5);
//                    edtEveningEnd.setSelection(edtEveningEnd.getText().length());
//
//                    builder.setTitle("选取起始时间");
//                    builder.setPositiveButton("确   定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                                StringBuffer sb = new StringBuffer();
//                                sb.append(String.format("%02d:%02d",
//                                        timePicker.getHour(),
//                                        timePicker.getMinute()));
//                                edtEveningEnd.setText(sb);
//                            }
//                            dialog.cancel();
//                        }
//                    });
//                    break;
                default:
                    break;
            }
            Dialog dialog = builder.create();
            dialog.show();
        }
        return true;

    }
}
