package com.step.counting;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.step.counting.bean.User;
import com.step.counting.constant.Constant;
import com.step.counting.data.DBManger;
import com.step.counting.fragement.AboutFragment;
import com.step.counting.fragement.HistoryFragment;
import com.step.counting.fragement.TodayFragment;
import com.step.counting.util.FragmentUtils;

import java.io.FileNotFoundException;

/***
 * */
public class MainActivity extends BaseActivtiy {

    private BottomNavigationView mBottomMenu;
    private Messenger mServiceMessage;

    public static int mCurrentSteps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        init();

        bind();
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            bind();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            mServiceMessage = new Messenger(service);
            Messenger messenger = new Messenger(mhandler);
            Message message = new Message();
            message.what = Constant.MSG_BIND;
            message.replyTo = messenger;
            try {
                mServiceMessage.send(message);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    private void bind(){
        Log.e("StepService", "service bind");
        Intent intent = new Intent(MainActivity.this, StepService.class);
        startService(intent);
        boolean mFlag = bindService(intent, connection, this.BIND_AUTO_CREATE);
        if(!mFlag){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    bind();
                }
            }).start();
        }
    }

    public Handler mhandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case Constant.MSG_FROM_SERVER:
                    Bundle bundle =  msg.getData();
                    mCurrentSteps = bundle.getInt("steps");
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    public void init(){
        User mUser = DBManger.getInstance(this).mUser;
        mBottomMenu = findViewById(R.id.report_person_bottom_menu);


        mBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                return true;
            }
        });
        showFragment(R.id.bottom_menu_today);
    }


    /**
     * 根据id显示相应的页面
     * @param menu_id
     */
    private void showFragment(int menu_id) {
        switch (menu_id){
            case R.id.bottom_menu_today:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, TodayFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_his:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, HistoryFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_about:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, AboutFragment.getInstance(),R.id.main_frame);
                break;
        }
    }

    private void requestPermissions(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACTIVITY_RECOGNITION);
                if(permission!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]
                            {Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION},0x0010);
                }

                if(permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[] {
                            Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
