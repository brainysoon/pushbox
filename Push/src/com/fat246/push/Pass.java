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
	
	//��¼
	private Button login;
	
	//�Ƿ��е�¼����
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

	//��챾�������ļ�
	private void checkPre() {
		
		//�õ�Ĭ�ϵ������ļ�
		SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
		
		isLoged=sp.getBoolean("Loged", false);
		
		if (isLoged){
			login.setText("�л��û�");
		}else {
			login.setText("�û���¼");
		}
	}

	//���ü����¼�
	private void setListener() {
		
		//��ʼ��Ϸ
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				startGame();
			}
		});
		
		//ת������
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

	//��ʼ��Ϸ
	protected void startGame() {
		
		Intent intent=new Intent(this,Main.class);
		
		startActivity(intent);
		
		overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
	}

	//��ʼ����ť
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
		
		builder.setTitle("��ʾ");
		
		builder.setMessage("�Ƿ��˳���Ϸ��");
		
		builder.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				arg0.dismiss();
				
				finish();
			}
		});
		
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		});
		
		builder.create().show();
	}
}
