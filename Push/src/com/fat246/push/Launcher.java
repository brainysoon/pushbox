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

	//����view
	private ImageView bhu;
	
	//hander, �����ٷ�UI�̸߳���UI
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

    //��ʼ��Handler
    private void initHandler() {
		
    	hand=new Handler(){
    		@Override
    		public void handleMessage(Message msg) {
    			
    			//��ת����ҳ��
    			goToPass();
    		}
    	};
    	
	}

	//����͸����
    private void setAnim() {
		
    	ObjectAnimator.ofFloat(bhu, "Alpha", 0.0f,1.0f)
    	.setDuration(3000)
    	.start();
    	
	}

	//��ʱ
    private void setTimer() {
		new Timer().schedule(new TimerTask() {
			
			
			@Override
			public void run() {
				
				hand.sendEmptyMessage(0);
			}
			
			//�ӳ�ʱ��
		}, 4000);
	}

    //��ת��������
	protected void goToPass() {
		
		Intent intent=new Intent(this,Pass.class);
		
		startActivity(intent);
		
		//�����ֵ��뵭����Ч��
		overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
		
		finish();
	}

	//��ʼ������
	private void initView() {
		
		bhu=(ImageView)findViewById(R.id.bhu);
		
	}
}
