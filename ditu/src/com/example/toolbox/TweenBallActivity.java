package com.example.toolbox;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.ditu.R;

public class TweenBallActivity extends Activity implements AnimationListener {
	private ImageView image;
	private Animation down;
	private Animation up;
	private Animation totaldown;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.ball);
			image=(ImageView)findViewById(R.id.imageView1);
			up=AnimationUtils.loadAnimation(this, R.anim.up);
			down=AnimationUtils.loadAnimation(this, R.anim.down);
			totaldown=AnimationUtils.loadAnimation(this, R.anim.totaldown);
			//down.setRepeatCount(Animation.INFINITE);
			Log.e("LZY", "here");
			down.setAnimationListener(this);
			up.setAnimationListener(this);
			totaldown.setAnimationListener(this);
			image.startAnimation(down);
		}
		@Override
		public void onAnimationEnd(Animation arg0) {
			// TODO Auto-generated method stub
			if(arg0.hashCode()==down.hashCode()){
				image.startAnimation(up);
				
			}else if(arg0.hashCode()==up.hashCode()){
				image.startAnimation(totaldown);			
			}else{
				image.startAnimation(up);
			}
		}
		@Override
		public void onAnimationRepeat(Animation arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onAnimationStart(Animation arg0) {
			// TODO Auto-generated method stub
			
		}
}