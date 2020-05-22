package com.step.counting.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

import com.step.counting.bean.Step;
import com.step.counting.bean.User;
import com.step.counting.util.SharedPreferenceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBManger {
    private Context mContext;
    private SQLiteDbHelper mDBHelper;
    public User mUser;
    public StepMgr mStepMgr;
    public static  DBManger instance;

    public static DBManger getInstance(Context mContext){
        if (instance == null){
            instance = new DBManger(mContext);
        }
        return instance;
    };

    public DBManger(final Context mContext){
        this.mContext = mContext;
        mDBHelper = new SQLiteDbHelper(mContext);
        mStepMgr = new StepMgr(mContext);

    }


    //用户登陆
    public void login(String name,String password,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME =? and USER_PASSWORD=?",new String[]{name,password});
            if (cursor.moveToFirst()){
                String USER_ID = cursor.getString(cursor.getColumnIndex("USER_ID"));
                String USER_NAME = cursor.getString(cursor.getColumnIndex("USER_NAME"));
                String USER_MAIL = cursor.getString(cursor.getColumnIndex("USER_MAIL"));
                String LIFT_PROCESSORPHONE = cursor.getString(cursor.getColumnIndex("LIFT_PROCESSORPHONE"));
                String USER_CHARCTER = cursor.getString(cursor.getColumnIndex("USER_CHARCTER"));

                mUser = new User();
                mUser.setUserId(USER_ID);
                mUser.setUserName(USER_NAME);
                mUser.setTelephone(LIFT_PROCESSORPHONE);
                mUser.setMail(USER_MAIL);
                mUser.setRole(USER_CHARCTER);
                listener.onSuccess();
            }else{
                listener.onError("未查询到该用户");
            }
            db.close();
            return;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        listener.onError("未查询到该用户");
    }

    //修改用户信息
    public void updateUser(User user,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME=?",new String[]{user.getUserName()});
            if (cursor.moveToFirst()){
                ContentValues values = new ContentValues();
                values.put("USER_NAME",user.getUserName());
                values.put("USER_MAIL",user.getMail());
                values.put("LIFT_PROCESSORPHONE",user.getTelephone());
                values.put("USER_CHARCTER",user.getRole());

                int code = db.update(SQLiteDbHelper.TAB_USER,values,"USER_NAME =?",new String[]{user.getUserName()+""});
                listener.onSuccess();
            }else {
                insertUser(user,listener);
            }
            db.close();
        }catch (Exception e){

        }
    }

    //注册用户
    public void registerUser(User user,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME=?",new String[]{user.getUserName()});
            if (cursor.moveToFirst()){
                listener.onError("用户名已经被注册！");
            }else{
                String userid = getRandomUSER_ID();
                ContentValues values = new ContentValues();
                values.put("USER_ID",userid);
                values.put("USER_NAME",user.getUserName());
                values.put("USER_PASSWORD",user.getPassword());
                values.put("LIFT_PROCESSORPHONE",user.getTelephone());
                values.put("USER_MAIL",user.getMail());
                values.put("USER_CHARCTER",user.getRole());
                mUser = user;
                mUser.setUserId(userid);
                long code = db.insert(SQLiteDbHelper.TAB_USER,null,values);
                listener.onSuccess();
            }
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    };

    //注册用户
    public void insertUser(User user,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME=?",new String[]{user.getUserName()});
            if (cursor.moveToFirst()){
                listener.onError("用户名已经被注册！");
            }else{
                String userid = getRandomUSER_ID();
                ContentValues values = new ContentValues();
                values.put("USER_ID",userid);
                values.put("USER_NAME",user.getUserName());
                values.put("USER_PASSWORD",user.getPassword());
                values.put("LIFT_PROCESSORPHONE",user.getTelephone());
                values.put("USER_MAIL",user.getMail());
                values.put("USER_CHARCTER",user.getRole());
                long code = db.insert(SQLiteDbHelper.TAB_USER,null,values);
                listener.onSuccess();
            }
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    };

    //注册步数数据
    public void insertStep(Step step){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("STEP_ID",step.getSTEP_ID());
            values.put("DATE",step.getDATE());
            values.put("STEP_NUM",step.getSTEP_NUM());
            values.put("LOCATIONS",step.getLOCATIONS());
            long code = db.insert(SQLiteDbHelper.TAB_STEP,null,values);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //更新步数数据
    public void updateStep(Step step){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("STEP_ID",step.getSTEP_ID());
            values.put("DATE",step.getDATE());
            values.put("STEP_NUM",step.getSTEP_NUM());
            values.put("LOCATIONS",step.getLOCATIONS());
            long code = db.update(SQLiteDbHelper.TAB_STEP,values,"STEP_ID =?",new String[]{step.getSTEP_ID()+""});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //通过日期获取步数
    public Step getStepByDate(String date){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Step where DATE like ?",new String[]{date});
            if (cursor.moveToFirst()){
                String STEP_ID = cursor.getString(cursor.getColumnIndex("STEP_ID"));
                String DATE = cursor.getString(cursor.getColumnIndex("DATE"));
                String STEP_NUM = cursor.getString(cursor.getColumnIndex("STEP_NUM"));
                String LOCATIONS = cursor.getString(cursor.getColumnIndex("LOCATIONS"));

                Step step = new Step();
                step.setSTEP_ID(STEP_ID);
                step.setDATE(DATE);
                step.setSTEP_NUM(STEP_NUM);
                step.setLOCATIONS(LOCATIONS);
                return step;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //获取所有的步数记录
    public List<Step> getAllSteps(){
        List<Step> mSteps = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.query(SQLiteDbHelper.TAB_USER,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String STEP_ID = cursor.getString(cursor.getColumnIndex("STEP_ID"));
                String DATE = cursor.getString(cursor.getColumnIndex("DATE"));
                String STEP_NUM = cursor.getString(cursor.getColumnIndex("STEP_NUM"));
                String LOCATIONS = cursor.getString(cursor.getColumnIndex("LOCATIONS"));

                Step step = new Step();
                step.setSTEP_ID(STEP_ID);
                step.setDATE(DATE);
                step.setSTEP_NUM(STEP_NUM);
                step.setLOCATIONS(LOCATIONS);
                mSteps.add(step);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mSteps;
    }

    //生成随机userid
    public String getRandomUSER_ID(){
        String strRand="LF" ;
        for(int i=0;i<10;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }

    String pattern = "yyyy-MM-dd HH:mm:ss";
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public String getDateTime(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }


    public interface IListener{
        public void onSuccess();
        public void onError(String error);
    };


}
