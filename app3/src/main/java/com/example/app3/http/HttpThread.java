package com.example.app3.http;


import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/6/13.
 */

public class HttpThread extends Thread {
    private final static String Url = "http://10.33.4.15:8888/fishpond";
    private String info =null;
    private Handler handler;

    public HttpThread(String info, Handler handler){
        this.info =info;
        this.handler = handler;
    }

    private HttpURLConnection connection=null;
    private OutputStream os=null;
    private PrintWriter pw=null;
    private InputStream is =null;
    private InputStreamReader isr=null;
    private BufferedReader br=null;
    private String result=null;
    @Override
    public void run() {
        super.run();
        try {
            URL httpUrl = new URL(Url);
            connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setConnectTimeout(5000);
            os = connection.getOutputStream();
            pw = new PrintWriter(os);
            pw.write(info);
            pw.flush();

            is = connection.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            String received;
            while ((received = br.readLine()) != null){
                //接收到的数据
                result+=received;
            }
            Message message = handler.obtainMessage();
            message.obj = result;
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if(pw!=null){
                    pw.close();
                }
                if(br!=null){
                    br.close();
                }
                if(connection!=null){
                    connection.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


}
