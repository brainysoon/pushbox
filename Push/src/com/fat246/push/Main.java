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

	//����������ť
	private Button restart;
	
	//����Ϸ
	private Button new_game;
	
	//�����Ŀ��
	private int wd=7;
	
	//�ҵ��˸���ť
	private Button [][] con=new Button[2][4];
	
	//���е�λ��
	private ImageView [][] box=new ImageView[wd][wd];
	
	//���
	private int [][] setup= new int[wd][wd]; 
	
	//��¼��֣�������������
	private int [][] back_setup=new int [wd][wd];
	
	//����ǽ�ĸ���
	private int wall_num=12;
	
	//�˵�λ��
	private Dot pen;
	
	//��������
	private int [][] dir=new int [2][4];
	
	//���ͨ���յ�
	private boolean flag_end=false;
	
	//�Ʋ���
	private int step_num=0;
	
	//�˵�ʮ����̬
	private int [] penImage=new int [10];
	
	//�������ڵ���̬
	private int po=1;
	
	//���ɵ�ͼ
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

	//��ʼ���˵�ʼ����̬
	private void initPenImage() {
		
		//��
		penImage[0]=R.drawable.pen_back;
		
		//��
		penImage[1]=R.drawable.pen_foword;
		
		//��
		penImage[2]=R.drawable.pen_left;
		
		//��
		penImage[3]=R.drawable.pen_right;
	}

	//��ʼ����������
	private void initDir() {
		
		//��
		dir[0][0]=-1;
		dir[1][0]=0;
		
		//��
		dir[0][1]=1;
		dir[1][1]=0;
		
		//��
		dir[0][2]=0;
		dir[1][2]=-1;
		
		//��
		dir[0][3]=0;
		dir[1][3]=1;
	}

	//���ü����¼�
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

	//��������
	protected void testSearch() {
		
		Search srh=new Search();
		
		Node node=srh.search(setup);
	}

	//���ø��ͼƬ
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

	//����ͼƬ
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
		
		//����setup����������
		copySetup();
		
		//�˵�λ��
		pen=cm.pen;
		
		//�˵���̬
		po=1;
		
		//intoMap();
	}

	//����setup
	private void copySetup() {
		
		for (int i=0; i<wd; i++)
			for (int j=0; j<wd; j++)
				back_setup[i][j]=setup[i][j];
	}

	//����һ�����
	private void intoMap() {
			
		for (int i=0; i<wd; i++)
			for (int j=0; j<wd; j++)
				setup[i][j]=0;
		
		//12��ǽ��λ��
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
	
	//��ʼ���ؼ�
	private void initView() {
		
		//���Ȱ˸���ť
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
		//49��λ��
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

	//�����¼�
	@Override
	public void onClick(View arg0) {
		
		//ȷ���û���������һ��
		int id=arg0.getId();
		
		//���Ķ��ƶ�
		int where=-1;
		
		//��
		if (id==R.id.Lup){
			where=0;
		}
		//��
		else if (id==R.id.Ldown){
			where=1;
		}
		//��
		else if (id==R.id.Lleft){
			where=2;
		}
		//��
		else if (id==R.id.Lright){
			where=3;
		}
		
		if (where>=0){
			po=where;
			moveTo(where);
		}
		
		Log.e("where", ""+id+"||"+R.id.Lup+"  "+R.id.Ldown+"  "+R.id.Lleft+"  "+R.id.Lright);
	}
	
	//���Ķ��ƶ�
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

		//���ı��˵�λ��
		pen=next;
		stepAdd();
	}
	
	//�ж��Ƿ�Խ��
	private boolean checkIn(Dot next){
		
		if (next.y<0||next.y>=7||next.x<0||next.x>=7) return false;
		
		return true;
	}

	//��������λ��
	private void changeBox(Dot pen, Dot next) {
			
		//���Ƚ���ͼ
		int tmp=setup[pen.y][pen.x];
		setup[pen.y][pen.x]=setup[next.y][next.x];
		setup[next.y][next.x]=tmp;
		
		//����ͼƬ
		setImage(pen);
		setImage(next);
	}
	
	//�Ʋ�����1
	private void stepAdd(){
		step_num++;
	}

	private void success(){
		AlertDialog.Builder builder=new Builder(this);
		
		builder.setIcon(R.drawable.people);
		
		builder.setTitle("ͨ�سɹ�!");
		
		builder.setMessage("��ϲ����"+step_num+"����˳��ͨ�أ�����");
		
		builder.setPositiveButton("��������", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				reSet();
				
				arg0.dismiss();
			}
		});
		
		builder.setNeutralButton("����Ϸ", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				startNewGame();
				
				arg0.dismiss();
			}
		});
		
		builder.setNegativeButton("�˳���Ϸ", new DialogInterface.OnClickListener() {
			
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
	
	//������Ϸ
	protected void reSet() {
		
		//��ԭsetup
		reSetup();
		
		setView();
		
		step_num=0;
	}
	
	//��ԭ
	private void reSetup() {
		
		//��λsetup
		for (int i=0; i<wd; i++)
			for (int j=0; j<wd; j++)
				setup[i][j]=back_setup[i][j];
		
		//��λ�˵�λ��
		pen=cm.pen;
		
		//��λ�˵���̬
		po=1;
	}

	//��ʼ����Ϸ
	private void startNewGame(){
		
		findMap();
		
		setView();
		
		step_num=0;
	}

	//���������¼�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//�ж��ǲ��� �����¼�
		if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
			hintUser();
		}
		
		return false;
	}

	//��ʾ�û�
	private void hintUser() {
		
		AlertDialog.Builder builder=new Builder(this);
		
		builder.setMessage("ȷ��Ҫֹͣ��Ϸ���˳���");
		
		builder.setTitle("��ʾ");
		
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
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
