package com.step.counting.util;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2018/4/29.
 */

public class FragmentUtils {
    public static void addFragmentToActivity(FragmentManager fragmentManager ,
                                             Fragment fragment ,
                                             int frmeId){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frmeId,fragment);
        fragmentTransaction.commit();
    }
    public static void replaceFragmentToActivity(FragmentManager fragmentManager ,
                                                 Fragment fragment ,
                                                 int frmeId){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frmeId,fragment);
        fragmentTransaction.commit();
    }
}
