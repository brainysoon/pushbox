package com.fat246.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import android.util.Log;

//���ݿ�������
public class DBConnector {

	//���ݿ�����
	private Connection conn;
	
	//ִ�����
	private Statement stm;
	
	public DBConnector(){
		
		try {
			//1.����������ʹ�÷����֪ʶ��
			Class.forName("com.mysql.jdbc.Driver");
			
			//2.ʹ��DriverManager ������ݿ�����
			this.conn=DriverManager.getConnection(
					"jdbc:mysql://115.28.79.151:3306/users",
					"root",
					"sxc19940115GT");
			
			//3.ʹ��Connector ������һ��Statement����
			this.stm=conn.createStatement();
			
			System.out.println(conn);
			
			
		} catch (ClassNotFoundException e) {
			Log.e("classnot", e.getMessage());
			
			e.printStackTrace();
		} catch (SQLException e) {
			Log.e("sqlex", e.getMessage());
			
			e.printStackTrace();
		}
	}
	
	//����һ���û�����Ϣ
	public boolean insertUserInfo(String user,String pass){
		
		//���û�гɹ��������ݿ��򷵻�false
		if (conn==null) return false;
		
		try {
			int result=stm.executeUpdate("" +
					"INSERT INTO user_info(user,pass) VALUES('"+user+"','"+pass+"')");
			
			//�ж�ִ�н��
			if (result>0) return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	//�ȶ�һ���û�����Ϣ
	public boolean selectUserInfo(String user,String pass){
		
		Log.e("here", "conn_before");
		
		//�ж����ݿ��Ƿ����ӳɹ�
		if (conn==null) return false;
		
		
		Log.e("here", "conn_after");
		
		try {
			
			String sql="SELECT * FROM user_info WHERE user='"+
					user+"' AND pass='"+pass+"'";
			
			Log.e("sql", sql);
			
			ResultSet rs=stm.executeQuery(sql);
			
			if (rs.next()){
				
				return true;
			}
			else {
				
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
