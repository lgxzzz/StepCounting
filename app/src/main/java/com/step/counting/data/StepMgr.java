package com.step.counting.data;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.step.counting.bean.Step;
import com.step.counting.navi.LocationMgr;
import com.step.counting.navi.PoiSearchMgr;

public class StepMgr {
    private Context mContext;
    public static StepMgr instance;

    //定位模块
    private LocationMgr mLocationMgr;

    public LatLng mCurrentPosition; //当前地点

    public Double mLongitude;
    public Double mLatitude;

    private IStempMgrLisetner lisetner;

    public static StepMgr getInstance(Context mContext){
        if (instance == null){
            instance = new StepMgr(mContext);
        }
        return instance;
    };

    public StepMgr(Context mContext){
        this.mContext = mContext;
        initData();
    }

    public void initData(){
        mLocationMgr  = new LocationMgr(mContext);
        getPosition();
    };
    //获取定位信息并且查询当前的POI点周边
    public void getPosition(){
        mLocationMgr.getLonLat(mContext, new LocationMgr.LonLatListener() {
            @Override
            public void getLonLat(AMapLocation aMapLocation) {
                mLongitude = aMapLocation.getLongitude();
                mLatitude = aMapLocation.getLatitude();
                mCurrentPosition = new LatLng(mLatitude,mLongitude);
                if (lisetner!=null){
                    lisetner.getLonLat(aMapLocation);
                }
            }
        });
    }

    public interface IStempMgrLisetner{
        public void getLonLat(AMapLocation aMapLocation);
    }

    public void setLisetner(IStempMgrLisetner lisetner) {
        this.lisetner = lisetner;
    }
}
