package com.example.app3.province_datebase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/1.
 */

public class DBHepler {

    private SQLiteDatabase db;
    private Context context;
    private DBManager dbm;

    public DBHepler(Context context) {
        super();
        this.context = context;
        dbm = new DBManager(context);
    }

    public ArrayList<Area> getCity(String pcode) {
        dbm.openDatabase();
        db = dbm.getDatabase();
        ArrayList<Area> list = new ArrayList<Area>();

        try {
            String sql = "select * from city where pcode='"+pcode+"'";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while (!cursor.isLast()){
                String code=cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[]=cursor.getBlob(2);
                String name=new String(bytes,"gbk");
                Area area=new Area();
                area.setName(name);
                area.setCode(code);
                area.setPcode(pcode);
                list.add(area);
                cursor.moveToNext();
            }
            String code=cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[]=cursor.getBlob(2);
            String name=new String(bytes,"gbk");
            Area area=new Area();
            area.setName(name);
            area.setCode(code);
            area.setPcode(pcode);
            list.add(area);

        } catch (Exception e) {
            return null;
        }
        dbm.closeDatabase();
        db.close();

        return list;

    }
    public ArrayList<Area> getProvince() {
        dbm.openDatabase();
        db = dbm.getDatabase();
        ArrayList<Area> list = new ArrayList<Area>();

        try {
            String sql = "select * from province";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while (!cursor.isLast()){
                String code=cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[]=cursor.getBlob(2);
                String name=new String(bytes,"gbk");
                Area area=new Area();
                area.setName(name);
                area.setCode(code);
                list.add(area);
                cursor.moveToNext();
            }
            String code=cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[]=cursor.getBlob(2);
            String name=new String(bytes,"gbk");
            Area area=new Area();
            area.setName(name);
            area.setCode(code);
            list.add(area);

        } catch (Exception e) {
            return null;
        }
        dbm.closeDatabase();
        db.close();
        return list;

    }
    public ArrayList<Area> getDistrict(String pcode) {
        dbm.openDatabase();
        db = dbm.getDatabase();
        ArrayList<Area> list = new ArrayList<Area>();
        try {
            String sql = "select * from district where pcode='"+pcode+"'";
            Cursor cursor = db.rawQuery(sql,null);
            if (cursor.moveToFirst()) {
                while (!cursor.isLast()) {
                    String code = cursor.getString(cursor
                            .getColumnIndex("code"));
                    byte bytes[] = cursor.getBlob(2);
                    String name = new String(bytes, "gbk");
                    Area Area = new Area();
                    Area.setName(name);
                    Area.setPcode(code);
                    list.add(Area);
                    cursor.moveToNext();
                }
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(2);
                String name = new String(bytes, "gbk");
                Area Area = new Area();
                Area.setName(name);
                Area.setPcode(code);
                list.add(Area);
            }

        } catch (Exception e) {
            Log.i("wer", e.toString());
        }
        dbm.closeDatabase();
        db.close();
        return list;

    }
}
