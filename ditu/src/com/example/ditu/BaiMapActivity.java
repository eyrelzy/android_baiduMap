package com.example.ditu;

import android.app.Activity;  
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;  
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;  
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;  

import com.baidu.mapapi.BMapManager;  
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController; 
import com.baidu.mapapi.map.MapView;  
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;  


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import com.baidu.location.LocationClientOption.LocationMode;
import com.example.ditu.R;

public class BaiMapActivity extends Activity {
	BMapManager mBMapMan = null;  
	MapView mMapView = null; 
	MKSearch mMKSearch = null ;
	TextView tx;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan=new BMapManager(getApplication());  
		mBMapMan.init(null);  
		mMKSearch = new MKSearch();  
		mMKSearch.init(mBMapMan, new MySearchListener());
		//注意：请在试用setContentView前初始化BMapManager对象，否则会报错  
		setContentView(R.layout.dtmain);  
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
	    mLocationClient.registerLocationListener( myListener );    //注册监听函数
		
		mMapView=(MapView)findViewById(R.id.bmapsView);  
		mMapView.setBuiltInZoomControls(true);  
		//设置启用内置的缩放控件  
		MapController mMapController=mMapView.getController();  
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放  
		//GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));  
		//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)  
		//mMapController.setCenter(point);//设置地图中心点  
		mMapController.setZoom(12);//设置地图zoom级别 
		
		//mMKSearch.reverseGeocode(new GeoPoint(40057031, 116307852)); //逆地址解析 
		//mMKSearch.geocode("哈尔滨工业大学","哈尔滨");
		//setContentView(R.layout.activity_main);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
		//option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);//返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		Log.v("LocSDK3", "locClient "+mLocationClient.isStarted()+"  started");
		if (mLocationClient != null && mLocationClient.isStarted()){
			Log.v("LocSDK3", "locClient is  started");
			mLocationClient.requestLocation();
		}else {
			Log.v("LocSDK3", "locClient is null or not started");
		} 
			 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override  
	protected void onDestroy(){  
	        mMapView.destroy();  
	        if(mBMapMan!=null){  
	                mBMapMan.destroy();  
	                mBMapMan=null;  
	        }  
	        super.onDestroy();  
	}  
	@Override  
	protected void onPause(){  
	        mMapView.onPause();  
	        if(mBMapMan!=null){  
	               mBMapMan.stop();  
	        }  
	        super.onPause();  
	}  
	@Override  
	protected void onResume(){  
	        mMapView.onResume();  
	        if(mBMapMan!=null){  
	                mBMapMan.start();  
	        }  
	       super.onResume();  
	} 
	public class MySearchListener implements MKSearchListener {

		@Override
		//通过经纬度 实现
		public void onGetAddrResult(MKAddrInfo res, int error) {
			// TODO Auto-generated method stub
			if (error != 0) {  
		        String str = String.format("错误号：%d", error);  
		        //Toast.makeText(GeoCoderDemo.this, str, Toast.LENGTH_LONG).show();  
		        return;  
		    }  
		    //地图移动到该点  
		    mMapView.getController().animateTo(res.geoPt);  
		    if (res.type == MKAddrInfo.MK_GEOCODE) {  
		        //地理编码：通过地址检索坐标点  
		        String strInfo = String.format("纬度：%f 经度：%f", res.geoPt.getLatitudeE6()/1e6, res.geoPt.getLongitudeE6()/1e6);  
		        //Toast.makeText(GeoCoderDemo.this, strInfo, Toast.LENGTH_LONG).show();  
		    }  
		    if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {  
		        //反地理编码：通过坐标点检索详细地址及周边poi  
		        String strInfo = res.strAddr;  
		        //Toast.makeText(GeoCoderDemo.this, strInfo, Toast.LENGTH_LONG).show();  
		    }  
			
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		//根据关键字进行地图定位
		public void onGetPoiResult(MKPoiResult res, int type, int error) {
			// TODO Auto-generated method stub
			// 错误号可参考MKEvent中的定义  
			if ( error == MKEvent.ERROR_RESULT_NOT_FOUND){  
				Toast.makeText(BaiMapActivity.this, "抱歉，未找到结果",Toast.LENGTH_LONG).show();  
				return ;  
			} else if (error != 0 || res == null) {  
				Toast.makeText(BaiMapActivity.this, "搜索出错啦..", Toast.LENGTH_LONG).show();  
				return;  
			}  
			// 将poi结果显示到地图上  
			PoiOverlay poiOverlay = new PoiOverlay(BaiMapActivity.this, mMapView);  
			poiOverlay.setData(res.getAllPoi());  
			mMapView.getOverlays().clear();  
			mMapView.getOverlays().add(poiOverlay);  
			mMapView.refresh();  
			//当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空  
			for(MKPoiInfo info : res.getAllPoi() ){  
				if ( info.pt != null ){  
					mMapView.getController().animateTo(info.pt);  
					break;  
				}  
			}  
			
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}  
       
        
	}
	
	
	public class MyLocationListener implements BDLocationListener{

		@Override
		//本地定位的实现
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null){
				Log.v("myerror","weihuoqu");
				return ;
			} 
			MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mMapView);  
			LocationData locData = new LocationData();  
		    SharedPreferences mySharedPreferences = getSharedPreferences("dingwei",Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putString("latitude", ""+location.getLatitude());
			editor.putString("longitude",""+location.getLongitude());
			editor.commit();
			locData.latitude = location.getLatitude();  
			locData.longitude = location.getLongitude();  
			locData.direction = 2.0f;  
			myLocationOverlay.setData(locData);  
			mMapView.getOverlays().add(myLocationOverlay);  
			mMapView.refresh();  
			mMapView.getController().animateTo(new GeoPoint((int)(locData.latitude*1e6),  
			(int)(locData.longitude* 1e6)));
			
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

	
	 @Override  
	public boolean onOptionsItemSelected(MenuItem item) {  
	        // TODO Auto-generated method stub  
	        switch(item.getItemId()){  
	        case R.id.dingwei:  
	        	LayoutInflater inflater = LayoutInflater.from(this);
	    		final View layout = inflater.inflate(R.layout.dingwei,null);
	    		final EditText latitude=(EditText)layout.findViewById(R.id.Latitude);
	    		final EditText Longitude=(EditText)layout.findViewById(R.id.Longitude);
	    		Dialog dialog=new AlertDialog.Builder(this).setTitle("设置").setView(layout)
	    				.setPositiveButton("确定",new DialogInterface.OnClickListener(){
	    					@Override
	    					public void onClick(DialogInterface dialog, int which) {
	    						// TODO Auto-generated method stub
	    						
	    						double myLatitude=Double.valueOf(latitude.getText().toString());
	    						double myLongitude=Double.valueOf(Longitude.getText().toString());
	    						//SharedPreferences mySharedPreferences = getSharedPreferences("dingwei",Activity.MODE_PRIVATE);
	    						//SharedPreferences.Editor editor = mySharedPreferences.edit();
	    						
	    						//editor.putString("latitude", myLatitude);
	    						//editor.putString("longitude",myLongitude);
	    						//editor.commit();
	    						
	    						MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mMapView);  
	    						LocationData locData = new LocationData();  
	    						locData.latitude = myLatitude;
	    						locData.longitude = myLongitude;
	    						locData.direction = 2.0f;  
	    						myLocationOverlay.setData(locData);  
	    						mMapView.getOverlays().add(myLocationOverlay);  
	    						mMapView.refresh();  
	    						mMapView.getController().animateTo(new GeoPoint((int)(locData.latitude*1e6),  
	    						(int)(locData.longitude* 1e6)));
	    						//dialog.dismiss();
	    						
	    						
	    					}
	    				})
	    				.setNegativeButton("取消",new DialogInterface.OnClickListener() {
	    					
	    					@Override
	    					public void onClick(DialogInterface dialog, int which) {
	    						// TODO Auto-generated method stub
	    						dialog.dismiss();
	    						
	    						
	    					}
	    				}).show();
	    		
	        	break;
	        case R.id.miaoshu:  
	        	LayoutInflater inflater1 = LayoutInflater.from(this);
	    		final View layout1 = inflater1.inflate(R.layout.miaoshu,null);
	    		final EditText miaoshu=(EditText)layout1.findViewById(R.id.locMiaoshu);
	    		Dialog dialog1=new AlertDialog.Builder(this).setTitle("设置").setView(layout1)
	    				.setPositiveButton("确定",new DialogInterface.OnClickListener(){
	    					@Override
	    					public void onClick(DialogInterface dialog, int which) {
	    						// TODO Auto-generated method stub
	    						String myMiaoshu=miaoshu.getText().toString();
	    						SharedPreferences mySharedPreferences = getSharedPreferences("dingwei",Activity.MODE_PRIVATE);
	    						double latitude=Double.valueOf(mySharedPreferences.getString("latitude",""));
	    						double longitude=Double.valueOf(mySharedPreferences.getString("longitude",""));
	    						mMKSearch.poiSearchNearBy(myMiaoshu, new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6)), 5000);  
	    						dialog.dismiss();
	    						
	    						
	    					}
	    				})
	    				.setNegativeButton("取消",new DialogInterface.OnClickListener() {
	    					
	    					@Override
	    					public void onClick(DialogInterface dialog, int which) {
	    						// TODO Auto-generated method stub
	    						dialog.dismiss();
	    						
	    						
	    					}
	    				}).show();
	    		
	    		
	            break;  
	        case R.id.dangqian:  
	        	if (mLocationClient != null && mLocationClient.isStarted()){
	    			Log.v("dangqian", "locClient is  started");
	    			mLocationClient.requestLocation();
	    		}else {
	    			Log.v("dangqian", "locClient is null or not started");
	    		} 
	            break;  
	        default:  
	            break;  
	        }  
	        return super.onOptionsItemSelected(item);  
	    }  
}
