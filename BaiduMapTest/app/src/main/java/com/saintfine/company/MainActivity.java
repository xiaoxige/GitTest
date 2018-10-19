package com.saintfine.company;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MapView bmapView;
    private Button btnTest;
    private BaiduMap map;

    /**
     * 搜索相关
     */
    private PoiSearch poiSearch;
    private SuggestionSearch suggestionSearch;

    /**
     * 定位相关
     */
    public LocationClient mLocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bmapView = findViewById(R.id.bmapView);
        btnTest = findViewById(R.id.btnTest);

        map =
                bmapView.getMap();
        // 设置了地图模式
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        map.setMyLocationEnabled(true);

        map.setMapStatus(MapStatusUpdateFactory.zoomTo(17.0f));

        draw();

        search();

        location();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadding(39.963175, 116.400244);
            }
        });
    }

    /**
     * 简易的基本操作
     */
    private void draw() {
        // 在地图上标记点
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
        LatLng point = new LatLng(39.963175, 116.400244);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmapDescriptor)
                // 是否可以拖拽
                .draggable(true);
        map.addOverlay(option);


        // 绘制折线(dottedLine: 是否虚线)
        LatLng p1 = new LatLng(39.97923, 116.357428);
        LatLng p2 = new LatLng(39.94923, 116.397428);
        LatLng p3 = new LatLng(39.97923, 116.437428);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        OverlayOptions ooPolyline = new PolylineOptions().dottedLine(true).width(10)
                .color(0xAAFF0000).points(points);
        map.addOverlay(ooPolyline);
    }

    /**
     * POI检索相关
     */
    private void search() {
        // 关键字搜索
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                // 获取POI检索结果
                List<PoiAddrInfo> allAddr = poiResult.getAllAddr();
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                List<CityInfo> suggestCityList = poiResult.getSuggestCityList();
                Log.e("TAG", "获取POI检索结果");

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                Log.e("TAG", "onGetPoiDetailResult");
            }


            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
                //获取Place详情页检索结果 
                Log.e("TAG", "获取Place详情页检索结果");
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                Log.e("TAG", "onGetPoiIndoorResult");
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                poiSearch.searchInCity(
                        new PoiCitySearchOption()
                                .city("北京")
                                .keyword("天通苑北")
                                .pageNum(1)
                );
            }
        }, 3000);


        // 地点搜索
        suggestionSearch = SuggestionSearch.newInstance();
        suggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                List<SuggestionResult.SuggestionInfo> allSuggestions = suggestionResult.getAllSuggestions();
                Log.e("TAG", "结果");
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                suggestionSearch.requestSuggestion(
                        new SuggestionSearchOption()
                                .city("北京")
                                .keyword("天安门")
                );
            }
        }, 3000);

    }

    /**
     * 定位相关
     */
    private void location() {
        mLocationClient = new LocationClient(getApplicationContext());

        // 设置一些定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；


        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        option.setIsNeedLocationDescribe(true);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true

        option.setIsNeedLocationPoiList(true);
        //可选，是否需要周边POI信息，默认为不需要，即参数为false
        //如果开发者需要获得周边POI信息，此处必须为true

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(0);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(true);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);


        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.e("TAG", bdLocation.getAddress().city + ", " + bdLocation.getLatitude() + ", " + bdLocation.getLongitude()
                        + ", " + bdLocation.describeContents() + ", " + bdLocation.getCity() + ", " + bdLocation.getAddrStr());

                List<Poi> poiList = bdLocation.getPoiList();
                Log.e("TAG", "");
                loadding(bdLocation.getLatitude(), bdLocation.getLongitude());

            }
        });


        mLocationClient.start();
    }


    private void loadding(final double latitude, double longitude) {
        //        // 定位的config
        MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true,
                BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        map.setMyLocationConfiguration(configuration);
        map.setMyLocationData(new MyLocationData.Builder().latitude(latitude).longitude(longitude).build());

        MapStatusUpdate mu = MapStatusUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        map.animateMapStatus(mu);
        map.setMapStatus(mu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bmapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.setMyLocationEnabled(false);
        suggestionSearch.destroy();
        poiSearch.destroy();
        bmapView.onDestroy();
    }

}
