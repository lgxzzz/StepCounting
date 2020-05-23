package com.step.counting.fragement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.PolylineOptions;
import com.step.counting.MainActivity;
import com.step.counting.R;
import com.step.counting.bean.Step;
import com.step.counting.bean.User;
import com.step.counting.data.DBManger;
import com.step.counting.navi.LocationMgr;
import com.step.counting.util.DateUtil;

import java.util.ArrayList;

public class TodayFragment extends Fragment {

    TextView mTodayTv;
    Step mTodayStep;

    private MapView mMapView = null;
    private AMap mAMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_today, container, false);
        initView(view,savedInstanceState);
        return view;
    }

    public static TodayFragment getInstance() {
        return new TodayFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    public void initView(View view, Bundle savedInstanceState){
        mTodayTv = view.findViewById(R.id.today_step_tv);

        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();

        mHandler.sendEmptyMessageDelayed(HSG_UI_REFRESH,1000*10);
    };

    /**
     * 绘制运动路线
     */
    public void drawLines(Step mTodayStep) {
        mAMap.clear();
        String locations = mTodayStep.getLOCATIONS();
        String[] gpsData = locations.split("-");
        ArrayList<LatLng> latlngList_path = new ArrayList<LatLng>();
        for (int i = 0;i<gpsData.length;i++){
            String[] gps = gpsData[i].split(",");
            double lat = Double.parseDouble(gps[0]);
            double lon = Double.parseDouble(gps[1]);
            LatLng latLng = new LatLng(lat,lon);
            latlngList_path.add(latLng);
        }
        if (latlngList_path.size()>0){
            PolylineOptions options = new PolylineOptions();
            options.addAll(latlngList_path);
            options.width(10).geodesic(true).color(Color.GREEN);
            mAMap.addPolyline(options);

            LatLng latLng = latlngList_path.get(0);
            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 15));
        }

    }

    public static final int HSG_UI_REFRESH = 1;
    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case HSG_UI_REFRESH:
                    mTodayStep = DBManger.getInstance(getContext()).getStepByDate(DateUtil.getCurrentDayStr());
                    drawLines(mTodayStep);
                    mTodayTv.setText("今日步数："+ MainActivity.mCurrentSteps);
                    mHandler.sendEmptyMessageDelayed(HSG_UI_REFRESH,1000*10);
                    break;
            }
            return false;
        }
    });
}
