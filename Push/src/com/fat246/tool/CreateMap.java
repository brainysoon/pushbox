package com.fat246.tool;

import java.util.Random;

import com.fat246.data.Dot;

public class CreateMap {
	
	//��Ҫ���ɵ�����
	private int [][] map;
	
	//����ĳ���
	private int wd=7;
	
	//ǽ�ĸ���
	private int wall_num=12;
	
	//�����
	Random rad=new Random();
	
	//������
	private Search srh=new Search();
	
	//�˵�λ��
	public Dot pen;
	
	//����һ�����õĵ�ͼ
	public boolean createUsedMap(int [][] map,int wall_num){
		
		//��ͼ
		this.map=map;
		
		//ǽ����Ŀ
		this.wall_num=wall_num;
		
		while (!initMap()) ;
		
		pen=srh.findDot(2);
		
		return true;
	}
	private boolean initMap() {
		//�������
		for (int i=0; i<wd; i++)
			for (int j=0; j<wd; j++)
				map[i][j]=0;
		
		//����ǽ��λ��
		for (int i=0; i<wall_num; i++){
			setUp(1);
		}
		
		//�����˵�λ��
		setUp(2);
		
		setUp(3);
		//������ʼ��
		
		//�����յ�
		setUp(4);
		
		if (checkMap()) return true;
		
		return false;
	}
	
	//���� λ���Ƿ����
	private boolean checkMap() {
		
		if (srh.search(map)==null) return false;
		
		return true;
	}
	//����λ��
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