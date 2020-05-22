package com.step.counting;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

/***
 * 主页activity
 * 根据登录角色显示对应的不同底部tab 执行不同的操作
 *
 * */
public class MainActivity extends BaseActivtiy {

    private BottomNavigationView mBottomMenu;
    private Messenger mServiceMessage;

    public static int mCurrentSteps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


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

}
