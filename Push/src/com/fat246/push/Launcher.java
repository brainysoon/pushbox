package com.fat246.push;

import java.util.Timer;
import java.util.TimerTask;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;


public class Launcher extends Activity {

	//变量view
	private ImageView bhu;
	
	//hander, 不能再非UI线程更新UI
	private Handler hand;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
        
        initView();
        
        initHandler();
        
        setTimer();
        
        setAnim();
        
    }

    //初始化Handler
    private void initHandler() {
		
    	hand=new Handler(){
    		@Override
    		public void handleMessage(Message msg) {
    			
    			//跳转的主页面
    			goToPass();
    		}
    	};
    	
	}

	//设置透明度
    private void setAnim() {
		
    	ObjectAnimator.ofFloat(bhu, "Alpha", 0.0f,1.0f)
    	.setDuration(3000)
    	.start();
    	
	}

	//定时
    private void setTimer() {
		new Timer().schedule(new TimerTask() {
			
			
			@Override
			public void run() {
				
				hand.sendEmptyMessage(0);
			}
			
			//延迟时间
		}, 4000);
	}

    //跳转到主函数
	protected void goToPass() {
		
		Intent intent=new Intent(this,Pass.class);
		
		startActivity(intent);
		
		//给你种淡入淡出的效果
		overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
		
		finish();
	}

	//初始化变量
	private void initView() {
		
		bhu=(ImageView)findViewById(R.id.bhu);
		
	}
}
