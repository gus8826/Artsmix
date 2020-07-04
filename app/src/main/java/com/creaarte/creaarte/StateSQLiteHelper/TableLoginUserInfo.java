package com.creaarte.creaarte.StateSQLiteHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableLoginUserInfo {

    private Activity activity;

    private String USRL_id = "USRL_id";
    private String USRL_alias = "USRL_alias";
    private String USRL_name = "USRL_name";
    private String USRL_email = "USRL_email";
    private String USRT_id = "USRT_id";
    private String USRL_img_url = "USRL_img_url";

    String table_login_user_info = "table_login_user_info";
    String sql = "CREATE TABLE IF NOT EXISTS " + table_login_user_info + " (" + USRL_id + " TEXT," + USRL_alias + " TEXT," + USRL_name + " TEXT," + USRL_email + " TEXT," + USRT_id + " TEXT," + USRL_img_url + " TEXT)";

    public TableLoginUserInfo() {

    }

    public TableLoginUserInfo(Activity activity) {
        this.activity = activity;
    }

    public String getSql() {
        return sql;
    }

    public void AddInformationTableLoginUserInfo(SQLiteDatabase db, String USRL_id, String USRL_alias, String USRL_name, String USRL_email, String USRT_id, String USRL_img_url) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.USRL_id, USRL_id);
        contentValues.put(this.USRL_alias, USRL_alias);
        contentValues.put(this.USRL_name, USRL_name);
        contentValues.put(this.USRL_email, USRL_email);
        contentValues.put(this.USRT_id, USRT_id);
        contentValues.put(this.USRL_img_url, USRL_img_url);
        db.insert(table_login_user_info, null, contentValues);
        Log.d("DataBase procesado", "una fila insertada");
    }

    public String upDateInformation(SQLiteDatabase db, String activated, String typeUser) {
        return "UPDATE table_session SET " + this.USRL_id + " = " + activated + "," + this.USRL_email + "=" + typeUser;
    }

    public String getUSRL_id() {
        String USRL_id = "";
        SQLHelper helper = new SQLHelper(activity);
        SQLiteDatabase db = helper.getWritableDatabase();
        String mQueryCount = "SELECT USRL_id FROM table_login_user_info";
        try {
            Cursor mCur = db.rawQuery(mQueryCount, new String[]{});
            mCur.moveToFirst();
            if (!mCur.isAfterLast()) {
                USRL_id = mCur.getString(mCur.getColumnIndex("USRL_id"));
                mCur.close();
                db.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return USRL_id;
    }

    public String getUSRL_alias() {
        String USRL_alias = "";
        SQLHelper helper = new SQLHelper(activity);
        SQLiteDatabase db = helper.getWritableDatabase();
        String mQueryCount = "SELECT USRL_alias FROM table_login_user_info";
        try {
            Cursor mCur = db.rawQuery(mQueryCount, new String[]{});
            mCur.moveToFirst();
            if (!mCur.isAfterLast()) {
                USRL_alias = mCur.getString(mCur.getColumnIndex("USRL_alias"));
                mCur.close();
                db.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return USRL_alias;
    }

    public String getUSRL_name() {
        String USRL_name = "";
        SQLHelper helper = new SQLHelper(activity);
        SQLiteDatabase db = helper.getWritableDatabase();
        String mQueryCount = "SELECT USRL_name FROM table_login_user_info";
        try {
            Cursor mCur = db.rawQuery(mQueryCount, new String[]{});
            mCur.moveToFirst();
            if (!mCur.isAfterLast()) {
                USRL_name = mCur.getString(mCur.getColumnIndex("USRL_name"));
                mCur.close();
                db.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return USRL_name;
    }

    public String getUSRL_email() {
        String USRL_email = "";
        SQLHelper helper = new SQLHelper(activity);
        SQLiteDatabase db = helper.getWritableDatabase();
        String mQueryCount = "SELECT USRL_email FROM table_login_user_info";
        try {
            Cursor mCur = db.rawQuery(mQueryCount, new String[]{});
            mCur.moveToFirst();
            if (!mCur.isAfterLast()) {
                USRL_email = mCur.getString(mCur.getColumnIndex("USRL_email"));
                mCur.close();
                db.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return USRL_email;
    }

    public String getUSRT_id() {
        String getUSRT_id = "";
        SQLHelper helper = new SQLHelper(activity);
        SQLiteDatabase db = helper.getWritableDatabase();
        String mQueryCount = "SELECT getUSRT_id FROM table_login_user_info";
        try {
            Cursor mCur = db.rawQuery(mQueryCount, new String[]{});
            mCur.moveToFirst();
            if (!mCur.isAfterLast()) {
                getUSRT_id = mCur.getString(mCur.getColumnIndex("getUSRT_id"));
                mCur.close();
                db.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getUSRT_id;
    }

    public String getUSRL_img_url() {
        String USRL_img_url = "";
        SQLHelper helper = new SQLHelper(activity);
        SQLiteDatabase db = helper.getWritableDatabase();
        String mQueryCount = "SELECT USRL_img_url FROM table_login_user_info";
        try {
            Cursor mCur = db.rawQuery(mQueryCount, new String[]{});
            mCur.moveToFirst();
            if (!mCur.isAfterLast()) {
                USRL_img_url = mCur.getString(mCur.getColumnIndex("USRL_img_url"));
                mCur.close();
                db.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return USRL_img_url;
    }

    public void deleteSesionUser() {

        String sql = "DROP TABLE IF EXISTS table_login_user_info";
        SQLHelper dataLogin = new SQLHelper(activity);
        SQLiteDatabase db = dataLogin.getWritableDatabase();
        db.execSQL(sql);
        activity.deleteDatabase("crearte_data_base_info.db");
        db.close();

    }
}
