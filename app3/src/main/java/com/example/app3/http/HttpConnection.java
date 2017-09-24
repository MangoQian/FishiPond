package com.example.app3.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import java.net.URL;

/**
 * Created by Administrator on 2017/6/15.
 */

public class HttpConnection {

    public static String getMessage(String info) {
        String Url = "http://211.87.6.84:8888/fishpond";
//        String Url = "http://192.168.0.113:8888/fishpond";
        String received = null;
        PrintWriter pw = null;
        BufferedReader br=null;
        HttpURLConnection connection=null;
        try {
            URL httpUrl = new URL(Url);
            connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
//            connection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            connection.setReadTimeout(5000);
            OutputStream os = connection.getOutputStream();
            pw = new PrintWriter(os);
            pw.write(info);
            pw.flush();


            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String temp = null;
            while ((temp = br.readLine()) != null) {
                //接收到的数据
                received += temp;
                Log.i("Received message", received);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (br != null) {
                    br.close();
                }
                if(pw!=null){
                    pw.close();
                }
                if(connection!=null){
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return received;
    }

}
