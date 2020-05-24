package com.step.counting.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.step.counting.R;


public class TitleView extends RelativeLayout{
    private Button mBackBtn;
    private TextView mTitleView;

    public TitleView(Context context) {
        super(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.title_view,this,true);
        mBackBtn = findViewById(R.id.title_back_btn);
        mTitleView = findViewById(R.id.title_content);
    }

    public void setBackBtnVisible(boolean visible){
        mBackBtn.setVisibility(visible? View.VISIBLE:View.GONE);
    }

    public void setTitle(String title){
        mTitleView.setText(title);
    }

    public void setOnBackListener(OnClickListener listener){
        mBackBtn.setOnClickListener(listener);
    }
}
