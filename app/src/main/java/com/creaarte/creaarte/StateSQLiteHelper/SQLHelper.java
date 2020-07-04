package com.creaarte.creaarte.StateSQLiteHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

    public static int version = 1;
    public static String DB_name = "crearte_data_base_info.db";
    public SQLiteDatabase db;

    public TableLoginUserInfo tableLoginUserInfo = new TableLoginUserInfo();

    public TableLoginUserInfo getTableLoginUserInfo() {
        return tableLoginUserInfo;
    }

    public SQLHelper(Context context) {
        super(context, DB_name, null, version);
        // TODO Auto-generated constructor stub
        db = getWritableDatabase();
        db.execSQL(tableLoginUserInfo.sql);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tableLoginUserInfo.sql);
        Log.d("base de datos operando", "tabla creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableLoginUserInfo.table_login_user_info);
        db.execSQL(tableLoginUserInfo.getSql());
    }
}