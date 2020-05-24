package com.step.counting.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.step.counting.util.SharedPreferenceUtil;

public class SQLiteDbHelper extends SQLiteOpenHelper {

    //数据库名称
    public static final String DB_NAME = "stepcounting.db";
    //数据库版本号
    public static int DB_VERSION = 27;
    //用户表
    public static final String TAB_USER = "UserInfo";
    //步数表
    public static final String TAB_STEP = "Step";

    Context context;
    public SQLiteDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableUser(db);
        createTableStep(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        SharedPreferenceUtil.setFirstTimeUse(true,context);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_STEP);
        onCreate(db);
    }

    //创建人员表
    public void createTableUser(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_USER +
                "(USER_ID varchar(20) primary key, " +
                "USER_NAME varchar(20), " +
                "USER_PASSWORD varchar(20), " +
                "LIFT_PROCESSORPHONE varchar(20), " +
                "USER_MAIL varchar(20), " +
                "USER_CHARCTER varchar(20))");
    }


    //创建步数表
    public void createTableStep(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_STEP +
                "(STEP_ID varchar(20) primary key, " +
                "DATE varchar(20), " +  //日期
                "STEP_NUM varchar(20), " + //步数
                "LOCATIONS varchar(20))"); //经纬度集合
    }
}
