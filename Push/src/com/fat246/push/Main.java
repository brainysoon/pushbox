package com.fat246.push;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.fat246.data.Dot;
import com.fat246.data.Node;
import com.fat246.tool.CreateMap;
import com.fat246.tool.Search;

public class Main extends Activity implements OnClickListener{

	//重新再来按钮
	private Button restart;
	
	//新游戏
	private Button new_game;
	
	//整个的宽度
	private int wd=7;
	
	//找到八个按钮
	private Button [][] con=new Button[2][4];
	
	//所有的位置
	private ImageView [][] box=new ImageView[wd][wd];
	
	//格局
	private int [][] setup= new int[wd][wd]; 
	
	//记录格局，方便重新再来
	private int [][] back_setup=new int [wd][wd];
	
	//控制墙的个数
	private int wall_num=12;
	
	//人的位置
	private Dot pen;
	
	//方向数组
	private int [][] dir=new int [2][4];
	
	//标记通过终点
	private boolean flag_end=false;
	
	//计步器
	private int step_num=0;
	
	//人的十种姿态
	private int [] penImage=new int [10];
	
	//保存现在的姿态
	private int po=1;
	
	//生成地图
	CreateMap cm=new CreateMap();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initView();
		
		initDir();
		
		initPenImage();
		
		setListener();
		
		findMap();
		
		setView();
	}

	//初始化人的始终姿态
	private void initPenImage() {
		
		//上
		penImage[0]=R.drawable.pen_back;
		
		//下
		penImage[1]=R.drawable.pen_foword;
		
		//左
		penImage[2]=R.drawable.pen_left;
		
		//右
		penImage[3]=R.drawable.pen_right;
	}

	//初始化方向数组
	private void initDir() {
		
		//上
		dir[0][0]=-1;
		dir[1][0]=0;
		
		//下
		dir[0][1]=1;
		dir[1][1]=0;
		
		//左
		dir[0][2]=0;
		dir[1][2]=-1;
		
		//右
		dir[0][3]=0;
		dir[1][3]=1;
	}

	//设置监听事件
	private void setListener() {
		
			for (int j=0; j<4; j++)
				con[0][j].setOnClickListener(this);
		
		restart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				reSet();
			}
		});
		
		new_game.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startNewGame();
			}
		});
	}

	//测试搜索
	protected void testSearch() {
		
		Search srh=new Search();
		
		Node node=srh.search(setup);
	}

	//设置格局图片
	private void setView() {
		
		Dot key=new Dot();
		
		for (int i=0; i<wd; i++){
			for (int j=0; j<wd; j++){
				key.y=i;
				key.x=j;
				setImage(key);
			}
		}
		
	}

	//设置图片
	private void setImage(Dot key) {                                          
		if (setup[key.y][key.x]==0){
			box[key.y][key.x].setImageResource(R.drawable.floor);
		}
		else if(setup[key.y][key.x]==1){
			box[key.y][key.x].setImageResource(R.drawable.wall);
		}
		else if (setup[key.y][key.x]==2){
			box[key.y][key.x].setImageResource(penImage[po]);
		}
		else if (setup[key.y][key.x]==3){
			box[key.y][key.x].setImageResource(R.drawable.start);
		}
		else if (setup[key.y][key.x]==4){
			box[key.y][key.x].setImageResource(R.drawable.end);
		}
	}

	private void findMap() {
		
		
		cm.createUsedMap(setup, 12);
		
		//拷贝setup，方便重来
		copySetup();
		
		//人的位置
		pen=cm.pen;
		
		//人的姿态
		po=1;
		
		//intoMap();
	}

	//拷贝setup
	private void copySetup() {
		
		for (int i=0; i<wd; i++)
			for (int j=0; j<wd; j++)
				back_setup[i][j]=setup[i][j];
	}

	//引入一个格局
	private void intoMap() {
			
		for (int i=0; i<wd; i++)
			for (int j=0; j<wd; j++)
				setup[i][j]=0;
		
		//12个墙的位置
		setup[0][0]=1;
		setup[1][0]=1;
		setup[2][0]=1;
		setup[2][1]=1;
		setup[2][2]=1;
		setup[4][1]=1;
		setup[4][2]=1;
		setup[4][3]=1;
		setup[4][4]=1;
		setup[0][4]=1;
		setup[3][5]=1;
		setup[1][3]=3;
		setup[3][1]=4;
		setup[1][6]=2;
		
		pen=new Dot();
		pen.y=1;
		pen.x=6;
	}
	
	//初始化控件
	private void initView() {
		
		//首先八个按钮
		con[0][0]=(Button)findViewById(R.id.Lup);
		con[0][1]=(Button)findViewById(R.id.Ldown);
		con[0][2]=(Button)findViewById(R.id.Lleft);
		con[0][3]=(Button)findViewById(R.id.Lright);
		
		/*
		con[1][0]=(Button)findViewById(R.id.Rup);
		con[1][1]=(Button)findViewById(R.id.Rdown);
		con[1][2]=(Button)findViewById(R.id.Rleft);
		con[1][3]=(Button)findViewById(R.id.Rright);
		*/
		//49个位置
		box[0][0]=(ImageView)findViewById(R.id.box_0_0);
		box[0][1]=(ImageView)findViewById(R.id.box_0_1);
		box[0][2]=(ImageView)findViewById(R.id.box_0_2);
		box[0][3]=(ImageView)findViewById(R.id.box_0_3);
		box[0][4]=(ImageView)findViewById(R.id.box_0_4);
		box[0][5]=(ImageView)findViewById(R.id.box_0_5);
		box[0][6]=(ImageView)findViewById(R.id.box_0_6);
		
		box[1][0]=(ImageView)findViewById(R.id.box_1_0);
		box[1][1]=(ImageView)findViewById(R.id.box_1_1);
		box[1][2]=(ImageView)findViewById(R.id.box_1_2);
		box[1][3]=(ImageView)findViewById(R.id.box_1_3);
		box[1][4]=(ImageView)findViewById(R.id.box_1_4);
		box[1][5]=(ImageView)findViewById(R.id.box_1_5);
		box[1][6]=(ImageView)findViewById(R.id.box_1_6);
		
		box[2][0]=(ImageView)findViewById(R.id.box_2_0);
		box[2][1]=(ImageView)findViewById(R.id.box_2_1);
		box[2][2]=(ImageView)findViewById(R.id.box_2_2);
		box[2][3]=(ImageView)findViewById(R.id.box_2_3);
		box[2][4]=(ImageView)findViewById(R.id.box_2_4);
		box[2][5]=(ImageView)findViewById(R.id.box_2_5);
		box[2][6]=(ImageView)findViewById(R.id.box_2_6);
		
		box[3][0]=(ImageView)findViewById(R.id.box_3_0);
		box[3][1]=(ImageView)findViewById(R.id.box_3_1);
		box[3][2]=(ImageView)findViewById(R.id.box_3_2);
		box[3][3]=(ImageView)findViewById(R.id.box_3_3);
		box[3][4]=(ImageView)findViewById(R.id.box_3_4);
		box[3][5]=(ImageView)findViewById(R.id.box_3_5);
		box[3][6]=(ImageView)findViewById(R.id.box_3_6);
		
		box[4][0]=(ImageView)findViewById(R.id.box_4_0);
		box[4][1]=(ImageView)findViewById(R.id.box_4_1);
		box[4][2]=(ImageView)findViewById(R.id.box_4_2);
		box[4][3]=(ImageView)findViewById(R.id.box_4_3);
		box[4][4]=(ImageView)findViewById(R.id.box_4_4);
		box[4][5]=(ImageView)findViewById(R.id.box_4_5);
		box[4][6]=(ImageView)findViewById(R.id.box_4_6);
		
		box[5][0]=(ImageView)findViewById(R.id.box_5_0);
		box[5][1]=(ImageView)findViewById(R.id.box_5_1);
		box[5][2]=(ImageView)findViewById(R.id.box_5_2);
		box[5][3]=(ImageView)findViewById(R.id.box_5_3);
		box[5][4]=(ImageView)findViewById(R.id.box_5_4);
		box[5][5]=(ImageView)findViewById(R.id.box_5_5);
		box[5][6]=(ImageView)findViewById(R.id.box_5_6);
		
		box[6][0]=(ImageView)findViewById(R.id.box_6_0);
		box[6][1]=(ImageView)findViewById(R.id.box_6_1);
		box[6][2]=(ImageView)findViewById(R.id.box_6_2);
		box[6][3]=(ImageView)findViewById(R.id.box_6_3);
		box[6][4]=(ImageView)findViewById(R.id.box_6_4);
		box[6][5]=(ImageView)findViewById(R.id.box_6_5);
		box[6][6]=(ImageView)findViewById(R.id.box_6_6);
		
		
		restart=(Button)findViewById(R.id.restart);
		
		new_game=(Button)findViewById(R.id.new_game);
	}

	//监听事件
	@Override
	public void onClick(View arg0) {
		
		//确定用户摁的是哪一个
		int id=arg0.getId();
		
		//往哪儿移动
		int where=-1;
		
		//上
		if (id==R.id.Lup){
			where=0;
		}
		//下
		else if (id==R.id.Ldown){
			where=1;
		}
		//左
		else if (id==R.id.Lleft){
			where=2;
		}
		//右
		else if (id==R.id.Lright){
			where=3;
		}
		
		if (where>=0){
			po=where;
			moveTo(where);
		}
		
		Log.e("where", ""+id+"||"+R.id.Lup+"  "+R.id.Ldown+"  "+R.id.Lleft+"  "+R.id.Lright);
	}
	
	//向哪儿移动
	private void moveTo(int k) {
		
		Dot next=new Dot();
		Dot next_next=new  Dot();
		
		next.x=pen.x+dir[1][k];
		next.y=pen.y+dir[0][k];
		
		if (!checkIn(next)) return ;
		if (setup[next.y][next.x]==1)
			return ;
		
		if (setup[next.y][next.x]==4){
			flag_end=true;
			setup[next.y][next.x]=0;
			changeBox(pen, next);
			pen=next;
			stepAdd();
			return ;
		}
		
		if(setup[next.y][next.x]==3){
			next_next.y=next.y+dir[0][k];
			next_next.x=next.x+dir[1][k];
			if (!checkIn(next_next)) return ;
			if (setup[next_next.y][next_next.x]==4){
				setup[next_next.y][next_next.x]=0;
				changeBox(next, next_next);
				changeBox(next, pen);
				pen=next;
				stepAdd();
				success();
				return ;
			}
			if (setup[next_next.y][next_next.x]==1)
				return ;
			changeBox(next, next_next);
		}
		
		changeBox(pen,next);
		
		if (flag_end){
			setup[pen.y][pen.x]=4;
			setImage(pen);
			flag_end=false;
		}

		//最后改变人的位置
		pen=next;
		stepAdd();
	}
	
	//判断是否越界
	private boolean checkIn(Dot next){
		
		if (next.y<0||next.y>=7||next.x<0||next.x>=7) return false;
		
		return true;
	}

	//交换两个位置
	private void changeBox(Dot pen, Dot next) {
			
		//首先交换图
		int tmp=setup[pen.y][pen.x];
		setup[pen.y][pen.x]=setup[next.y][next.x];
		setup[next.y][next.x]=tmp;
		
		//设置图片
		setImage(pen);
		setImage(next);
	}
	
	//计步器加1
	private void stepAdd(){
		step_num++;
	}

	private void success(){
		AlertDialog.Builder builder=new Builder(this);
		
		builder.setIcon(R.drawable.people);
		
		builder.setTitle("通关成功!");
		
		builder.setMessage("恭喜你以"+step_num+"步，顺利通关！！！");
		
		builder.setPositiveButton("重新再来", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				reSet();
				
				arg0.dismiss();
			}
		});
		
		builder.setNeutralButton("新游戏", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				startNewGame();
				
				arg0.dismiss();
			}
		});
		
		builder.setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				arg0.dismiss();
				
				finish();
			}
		});
		
		Dialog dialog=builder.create();
		
		dialog.setCanceledOnTouchOutside(false);
		
		dialog.show();
		
	}
	
	//重置游戏
	protected void reSet() {
		
		//复原setup
		reSetup();
		
		setView();
		
		step_num=0;
	}
	
	//复原
	private void reSetup() {
		
		//复位setup
		for (int i=0; i<wd; i++)
			for (int j=0; j<wd; j++)
				setup[i][j]=back_setup[i][j];
		
		//复位人的位置
		pen=cm.pen;
		
		//复位人的姿态
		po=1;
	}

	//开始新游戏
	private void startNewGame(){
		
		findMap();
		
		setView();
		
		step_num=0;
	}

	//监听按键事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//判断是不是 返回事件
		if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
			hintUser();
		}
		
		return false;
	}

	//提示用户
	private void hintUser() {
		
		AlertDialog.Builder builder=new Builder(this);
		
		builder.setMessage("确定要停止游戏并退出？");
		
		builder.setTitle("提示");
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
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
