package com.fat246.tool;

import java.util.Random;

import com.fat246.data.Dot;

public class CreateMap {
	
	//需要生成的数组
	private int [][] map;
	
	//数组的长度
	private int wd=7;
	
	//墙的个数
	private int wall_num=12;
	
	//随机数
	Random rad=new Random();
	
	//搜索类
	private Search srh=new Search();
	
	//人的位置
	public Dot pen;
	
	//构建一个可用的地图
	public boolean createUsedMap(int [][] map,int wall_num){
		
		//地图
		this.map=map;
		
		//墙的数目
		this.wall_num=wall_num;
		
		while (!initMap()) ;
		
		pen=srh.findDot(2);
		
		return true;
	}
	private boolean initMap() {
		//格局清零
		for (int i=0; i<wd; i++)
			for (int j=0; j<wd; j++)
				map[i][j]=0;
		
		//生成墙的位置
		for (int i=0; i<wall_num; i++){
			setUp(1);
		}
		
		//生成人的位置
		setUp(2);
		
		setUp(3);
		//生成起始点
		
		//生成终点
		setUp(4);
		
		if (checkMap()) return true;
		
		return false;
	}
	
	//检察此 位置是否可用
	private boolean checkMap() {
		
		if (srh.search(map)==null) return false;
		
		return true;
	}
	//生成位置
	private Dot setUp(int what){
			
		Dot dot=new Dot();
		dot.y=Math.abs(rad.nextInt()%wd);
		dot.x=Math.abs(rad.nextInt()%wd);
			
		while (map[dot.y][dot.x]!=0){
			dot.y=Math.abs(rad.nextInt()%wd);
			dot.x=Math.abs(rad.nextInt()%wd);
		}
			
		map[dot.y][dot.x]=what;
		return dot;
	}
}