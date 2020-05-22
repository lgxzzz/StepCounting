package com.step.counting.navi;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;

public class LocationMgr implements AMapLocationListener {

    private AMapLocationClient locationClient = null;  // 定位
    private AMapLocationClientOption locationOption = null;  // 定位设置

    private Context mContext;

    public static LocationMgr instance;

    public LocationMgr(Context context){
        this.mContext = context;
    }

    public LocationMgr getInstance(){
        return instance;
    }

    public LatLng mCurrentPosition; //当前地点

    public Double mLongitude;
    public Double mLatitude;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
//        mLonLatListener.getLonLat(aMapLocation);
//        locationClient.stopLocation();
//        locationClient.onDestroy();
//        locationClient = null;
//        locationOption = null;
        mLongitude = aMapLocation.getLongitude();
        mLatitude = aMapLocation.getLatitude();
        mCurrentPosition = new LatLng(mLatitude,mLongitude);
    }

    public LatLng getmCurrentPosition() {
        return mCurrentPosition;
    }

    public void setmCurrentPosition(LatLng mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }

    private LonLatListener mLonLatListener;

    public void  startLocation(){
        locationClient = new AMapLocationClient(mContext);
        locationOption = new AMapLocationClientOption();
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);// 设置定位模式为高精度模式
        locationClient.setLocationListener(this);// 设置定位监听
        locationOption.setOnceLocation(false); // 单次定位
        locationOption.setInterval(3000);
        locationOption.setNeedAddress(true);//逆地理编码
        locationClient.setLocationOption(locationOption);// 设置定位参数
        locationClient.startLocation(); // 启动定位
    }

    public interface  LonLatListener{
        void getLonLat(AMapLocation aMapLocation);
    }
}
