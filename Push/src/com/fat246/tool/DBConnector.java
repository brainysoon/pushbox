package com.fat246.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import android.util.Log;

//数据库连接类
public class DBConnector {

	//数据库连接
	private Connection conn;
	
	//执行语句
	private Statement stm;
	
	public DBConnector(){
		
		try {
			//1.加载驱动，使用反射的知识。
			Class.forName("com.mysql.jdbc.Driver");
			
			//2.使用DriverManager 获得数据库连接
			this.conn=DriverManager.getConnection(
					"jdbc:mysql://115.28.79.151:3306/users",
					"root",
					"sxc19940115GT");
			
			//3.使用Connector 来生成一个Statement对象
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
	
	//插入一个用户的信息
	public boolean insertUserInfo(String user,String pass){
		
		//如果没有成功连接数据库则返回false
		if (conn==null) return false;
		
		try {
			int result=stm.executeUpdate("" +
					"INSERT INTO user_info(user,pass) VALUES('"+user+"','"+pass+"')");
			
			//判断执行结果
			if (result>0) return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	//比对一个用户的信息
	public boolean selectUserInfo(String user,String pass){
		
		Log.e("here", "conn_before");
		
		//判断数据库是否连接成功
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
