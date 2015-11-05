package com.fat246.push;

import com.fat246.tool.DBConnector;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends Activity {

	//
	private EditText user;
	private EditText password;
	private Button login;
	private Button signup;
	private Button forget_password;
	private CheckBox save_password;
	private CheckBox login_auto;
	
	
	//是否保存密码
	private boolean isSavePassword;
	
	//是否自动登录
	private boolean isAutoLogIn;
	
	//配置文件
	private SharedPreferences sp;
	
	//数据库访问
	private DBConnector db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in);
		
		//不能放在外面 会出错的
		sp=PreferenceManager.getDefaultSharedPreferences(LogIn.this);
		db=new DBConnector();
		
		initView();
		
		checkPre();
		
		setListener();
	}


	//检察配置文件
	private void checkPre() {
		
		user.setText(sp.getString("user", ""));
		
		if (sp.getBoolean("save_password", false)){
			password.setText(sp.getString("password",""));
			save_password.setChecked(true);
		}
		
		if (sp.getBoolean("auto_login", false)){
			
			login_auto.setChecked(true);
			
			ToastShow();
			
		//	finish();
		}
	}


	//登录dialog
	private void ToastShow() {
		
		Toast.makeText(this, "正在登录......", Toast.LENGTH_SHORT).show();
		
		Toast.makeText(this, "登录成功......", Toast.LENGTH_SHORT).show();
		
	}


	//设置监听事件
	private void setListener() {
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (checkOk()){
					
					//将用户保存到本地
					saveLocal();
					
					finish();
				}
			}
		});
		
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
		
		forget_password.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
	}

	//将用户保存到本地
	protected void saveLocal() {
		Editor edt=sp.edit();
		
		edt.putBoolean("Loged", true);
		
		edt.putString("user", user.getText().toString().trim());
		edt.putString("password", password.getText().toString().trim());
		
		//保存密码
		if (save_password.isChecked()){
			edt.putBoolean("save_password", true);
		}else{
			edt.putBoolean("save_password", false);
		}
		
		//自动登录
		if (login_auto.isChecked()){
			edt.putBoolean("auto_login", true);
		}else{
			edt.putBoolean("auto_login", false);
		}
		
		edt.commit();
	}


	//检察用户名或者密码是不是 合格
	protected boolean checkOk() {
		
		//检查格式是否合法
		if(!checkExpression()) return false;
		
		Log.e("here", "db");
		
		//上数据库检查
		if (!db.selectUserInfo(user.getText().toString().trim(), password.getText().toString().trim())){
			
			makeHint("数据库访问失败！");
			
			return false;
		}
		
		else{
			makeHint("数据库访问成功！");
		}
		
		return true;
	}


	private boolean checkExpression() {
		
		if (user.getText().toString().trim().equals("")){
			makeHint("请输入用户名！");
			return false;
		}
		else if (password.getText().toString().trim().equals("")){
			makeHint("请输入密码！");
			return false;
		}
		
		return true;
	}


	private void makeHint(String hint) {
		Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
	}


	//初始化控件
	private void initView() {
		
		user=(EditText)findViewById(R.id.user);
		password=(EditText)findViewById(R.id.password);
		login=(Button)findViewById(R.id.login);
		signup=(Button)findViewById(R.id.signup);
		forget_password=(Button)findViewById(R.id.forget_password);
		save_password=(CheckBox)findViewById(R.id.save_password);
		login_auto=(CheckBox)findViewById(R.id.login_auto);
	}
}
