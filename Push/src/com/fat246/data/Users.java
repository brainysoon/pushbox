package com.fat246.data;

public class Users {
	
	//�˻���
	private String user;
	
	//����
	private String pass;
	
	public Users(String user,String pass){
		this.user=user;
		this.pass=pass;
	}
	
	public String getUser(){
		return this.user;
	}
	
	public String getPass(){
		return this.pass;
	}
}
