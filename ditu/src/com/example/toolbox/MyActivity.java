package com.example.toolbox;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.bouncingballs.BouncingBalls;
import com.example.ditu.BaiMapActivity;
import com.example.ditu.R;
import com.example.toolbox.GridAdapter.GridItemInfo;


public class MyActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initGrid();
		//String s=getMoon();
	}
	 public void initGrid() {
		 GridView gridView = (GridView) findViewById(R.id.gridView);
	    	GridAdapter adapter = new GridAdapter(this);
	    	List<GridItemInfo> list = new ArrayList<GridItemInfo>();
	    	
	    	list.add(new GridItemInfo(R.drawable.cal_icon, "计算器"));
	    	list.add(new GridItemInfo(R.drawable.ic_launcher, "弹球"));
	    	list.add(new GridItemInfo(R.drawable.chat_icon, "聊天室"));
	    	list.add(new GridItemInfo(R.drawable.map_icon, "地图"));
	    	list.add(new GridItemInfo(R.drawable.ball_icon, "立体弹球"));
	    	
	    	adapter.setList(list);
	    	
	    	gridView.setAdapter(adapter);
	    	
	    	gridView.setOnItemClickListener(
	    			new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Intent intent = new Intent();
							switch (arg2) {
							case 0:
								intent.setClass(MyActivity.this, CalActivity.class);
								break;
							case 3:
								intent.setClass(MyActivity.this, BaiMapActivity.class);
								break;
							case 1:
								intent.setClass(MyActivity.this, TweenBallActivity.class);
								break;
							case 2:
								intent.setClass(MyActivity.this, ChatActivity.class);
								break;
							case 4:
								intent.setClass(MyActivity.this, BouncingBalls.class);
								break;
							}
							startActivity(intent);
						}
	    			});
	 
	 }
	//load class when so libary
	public native String getMoon();
	static {
		System.loadLibrary("Calculator-jni");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.my, menu);
		return true;
	}

}
