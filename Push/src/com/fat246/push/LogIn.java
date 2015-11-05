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
	
	
	//�Ƿ񱣴�����
	private boolean isSavePassword;
	
	//�Ƿ��Զ���¼
	private boolean isAutoLogIn;
	
	//�����ļ�
	private SharedPreferences sp;
	
	//���ݿ����
	private DBConnector db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in);
		
		//���ܷ������� ������
		sp=PreferenceManager.getDefaultSharedPreferences(LogIn.this);
		db=new DBConnector();
		
		initView();
		
		checkPre();
		
		setListener();
	}


	//��������ļ�
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


	//��¼dialog
	private void ToastShow() {
		
		Toast.makeText(this, "���ڵ�¼......", Toast.LENGTH_SHORT).show();
		
		Toast.makeText(this, "��¼�ɹ�......", Toast.LENGTH_SHORT).show();
		
	}


	//���ü����¼�
	private void setListener() {
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (checkOk()){
					
					//���û����浽����
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

	//���û����浽����
	protected void saveLocal() {
		Editor edt=sp.edit();
		
		edt.putBoolean("Loged", true);
		
		edt.putString("user", user.getText().toString().trim());
		edt.putString("password", password.getText().toString().trim());
		
		//��������
		if (save_password.isChecked()){
			edt.putBoolean("save_password", true);
		}else{
			edt.putBoolean("save_password", false);
		}
		
		//�Զ���¼
		if (login_auto.isChecked()){
			edt.putBoolean("auto_login", true);
		}else{
			edt.putBoolean("auto_login", false);
		}
		
		edt.commit();
	}


	//����û������������ǲ��� �ϸ�
	protected boolean checkOk() {
		
		//����ʽ�Ƿ�Ϸ�
		if(!checkExpression()) return false;
		
		Log.e("here", "db");
		
		//�����ݿ���
		if (!db.selectUserInfo(user.getText().toString().trim(), password.getText().toString().trim())){
			
			makeHint("���ݿ����ʧ�ܣ�");
			
			return false;
		}
		
		else{
			makeHint("���ݿ���ʳɹ���");
		}
		
		return true;
	}


	private boolean checkExpression() {
		
		if (user.getText().toString().trim().equals("")){
			makeHint("�������û�����");
			return false;
		}
		else if (password.getText().toString().trim().equals("")){
			makeHint("���������룡");
			return false;
		}
		
		return true;
	}


	private void makeHint(String hint) {
		Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
	}


	//��ʼ���ؼ�
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
