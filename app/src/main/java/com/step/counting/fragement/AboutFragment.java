package com.step.counting.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.step.counting.R;
import com.step.counting.bean.User;
import com.step.counting.data.DBManger;

/***
 * 维保人员个人信息界面
 *
 * */
public class AboutFragment extends Fragment {

    //个人信息
    TextView mUserID;
    TextView mUserName;
    TextView mUserTel;
    TextView mUserMail;
    Button mUpdateBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_about, container, false);
        initView(view);

        return view;
    }

    public static AboutFragment getInstance() {
        return new AboutFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initView(View view){
        mUserID = view.findViewById(R.id.user_id);
        mUserName = view.findViewById(R.id.user_name);
        mUserTel = view.findViewById(R.id.user_tel);
        mUserMail = view.findViewById(R.id.user_mail);
        mUpdateBtn = view.findViewById(R.id.user_update_btn);
    };

    public void initData() {
        User user = DBManger.getInstance(getContext()).mUser;
        mUserID.setText(user.getUserId());
        mUserName.setText(user.getUserName());
        mUserMail.setText(user.getMail());
        mUserTel.setText(user.getTelephone());
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }




}
