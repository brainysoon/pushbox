package com.fat246.push;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.Visibility;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Pass extends Activity {

	//
	private Button start;
	
	private Button offier;
	
	//登录
	private Button login;
	
	//是否有登录缓存
	private boolean isLoged=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pass);
		
		initView();
		
		checkPre();
		
		setListener();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		checkPre();
	}

	//检察本地配置文件
	private void checkPre() {
		
		//得到默认的配置文件
		SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
		
		isLoged=sp.getBoolean("Loged", false);
		
		if (isLoged){
			login.setText("切换用户");
		}else {
			login.setText("用户登录");
		}
	}

	//设置监听事件
	private void setListener() {
		
		//开始游戏
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				startGame();
			}
		});
		
		//转到官网
		offier.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent();
				
				//
				intent.setAction("android.intent.action.VIEW");
				
				Uri offier_uri=Uri.parse("http://www.fat246.com");
				
				intent.setData(offier_uri);
				
				//
				startActivity(intent);
			}
		});
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent(Pass.this,LogIn.class);
				
				startActivity(intent);
				
				overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
			}
		});
	}

	//开始游戏
	protected void startGame() {
		
		Intent intent=new Intent(this,Main.class);
		
		startActivity(intent);
		
		overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
	}

	//初始化按钮
	private void initView() {
		
		start=(Button)findViewById(R.id.start_button);
		offier=(Button)findViewById(R.id.offier);
		login=(Button)findViewById(R.id.login);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
			displayDialog();
		}
		return false;
	}

	
	private void displayDialog() {
		
		AlertDialog.Builder builder=new Builder(this);
		
		builder.setTitle("提示");
		
		builder.setMessage("是否退出游戏？");
		
		builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				arg0.dismiss();
				
				finish();
			}
		});
		
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		});
		
		builder.create().show();
	}
}
