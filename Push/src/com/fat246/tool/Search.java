package com.fat246.tool;

import java.util.LinkedList;
import java.util.Queue;

import com.fat246.data.*;

public class Search {

	// ��ͼ
	private int[][] map;

	// ��������
	private int[][] dir = new int[4][2];

	// ��ͼ�Ŀ��
	private int wd = 7;

	// �������,���������
	private int[][] sign = new int[wd][wd];

	// �������,map������˫����
	private int[][][] signMap = new int[wd][wd][4];

	// �յ�
	private Dot end;

	// ����
	private Queue<Node> queue = new LinkedList<Node>();

	// ����������
	private Queue<nodeDot> que = new LinkedList<Search.nodeDot>();

	public Search() {

		initDir();
	}

	// ��ʼ����������
	private void initDir() {

		// up
		dir[0][0] = -1;
		dir[0][1] = 0;

		// left
		dir[1][0] = 0;
		dir[1][1] = -1;

		// down
		dir[2][0] = 1;
		dir[2][1] = 0;

		// right
		dir[3][0] = 0;
		dir[3][1] = 1;

	}

	// �������·����������ܵ��� �򷵻�-1
	public Node search(int[][] map) {

		// ���õ�ͼ
		this.map = map;

		// ��map�����������
		clearSignMap();

		// �������
		queue.clear();

		// �ҵ��˵�λ��
		Dot pen = findDot(2);

		// �ҵ����ӵ���ʼλ��
		Dot start = findDot(3);

		// �ҵ��յ�
		end = findDot(4);

		// ���нڵ�
		Node fir = new Node(start, pen);
		fir.dir = 2;
		fir.step = 0;
		fir.boxStep=0;

		// ��ʱ�Ľڵ�
		Node tmp;

		// �����ӵ���ʼλ�ü��뵽����
		queue.offer(fir);

		// ������ʼ
		while (!queue.isEmpty()) {

			// ȡ��ͷ
			fir = queue.poll();

			// �������ұ����ĸ�����
			for (int i = 0; i < 4; i++) {

				tmp = new Node();
				tmp.box.y = fir.box.y + dir[i][0];
				tmp.box.x = fir.box.x + dir[i][1];
				tmp.dir = i;

				//�̳в���
				tmp.step=fir.step;
				tmp.boxStep=fir.boxStep+1;
				
				// ����Ƿ�������
				if (!checkNode(fir, tmp))
					continue;
				
				// �ж��ǲ����յ�
				if (tmp.box.y == end.y && tmp.box.x == end.x) 
					return tmp;
					
				queue.offer(tmp);

				// ����Ѿ��߹�
				signMap[tmp.box.y][tmp.box.x][tmp.dir] = 1;

			}
		}

		return null;
	}

	// ��map�����������
	private void clearSignMap() {

		for (int i = 0; i < wd; i++)
			for (int j = 0; j < wd; j++)
				for (int k = 0; k < 4; k++)
					signMap[i][j][k] = 0;
	}

	private boolean checkNode(Node fir, Node tmp) {

		// ���߽�
		if (!checkBor(tmp.box))
			return false;

		// ����Ƿ���ǽ
		if (!checkWall(tmp.box))
			return false;

		// ����Ƿ��߹�
		if (signMap[tmp.box.y][tmp.box.x][tmp.dir] == 1)
			return false;

		// ����Ƿ�ɴ�
		if (!checkCanGo(fir, tmp))
			return false;

		// �ɴ������½��˵�����λ�ü�¼
		tmp.pen.y = fir.box.y;
		tmp.pen.x = fir.box.x;
		tmp.step++;

		return true;
	}

	// ����Ƿ���ǽ
	private boolean checkWall(Dot tmp) {

		if (map[tmp.y][tmp.x] == 1)
			return false;

		return true;
	}

	// ���߽�
	private boolean checkBor(Dot tmp) {

		if (tmp.y < 0 || tmp.y >= wd)
			return false;
		if (tmp.x < 0 || tmp.x >= wd)
			return false;

		return true;
	}

	// ����������Ƿ�ɴ�
	private boolean checkCanGo(Node fir, Node tmp) {

		// �˵��յ�
		Dot sto = new Dot();

		// ��������˵�λ��
		int key = (tmp.dir + 2) % 4;

		// �����Ӧ�õ����λ��
		sto.y = fir.box.y + dir[key][0];
		sto.x = fir.box.x + dir[key][1];

		// ����˵�λ���Ƿ��Ѿ����˱߽�
		if (!checkBor(sto))
			return false;

		// ����˵�λ���Ƿ�ɴ�
		if (!checkWall(sto))
			return false;

		// ���������������
		que.clear();

		// �����������
		clearSign();

		// �˵Ŀ�ʼ��λ��
		Dot sta = new Dot(fir.pen.x, fir.pen.y);

		// ��ʱ��
		Dot tp;
		nodeDot notp;

		// �����ǲ����Ѿ���λ����
		if (sta.y == sto.y && sta.x == sto.x)
			return true;

		nodeDot node = new nodeDot(sta);

		// ���ͷ���
		que.offer(node);

		// ���ͷ����Ѿ��߹�
		sign[sta.y][sta.x] = 1;

		// ������ʼ
		while (!que.isEmpty()) {

			// ȡ��ͷ���
			node = que.poll();

			for (int i = 0; i < 4; i++) {

				tp = new Dot();
				tp.y = node.dot.y + dir[i][0];
				tp.x = node.dot.x + dir[i][1];

				// ����Ƿ��Ǳ߽������ǽ
				if (!checkBor(tp))
					continue;
				if (!checkWall(tp))
					continue;

				// ����Ƿ��߹�
				if (sign[tp.y][tp.x] == 1)
					continue;

				// ��װ
				notp = new nodeDot(tp);
				notp.step = node.step + 1;

				// ����ǲ����յ�
				if (tp.y == sto.y && tp.x == sto.x) {
					
					tmp.step += notp.step;

					return true;
				}

				// ����Ƿ�������
				if (tp.y == fir.box.y && tp.x == fir.box.x)
					continue;

				// �������
				que.offer(notp);

				// ���
				sign[tp.y][tp.x] = 1;
			}
		}
		return false;
	}

	// ����ͼ����ı�����
	private void clearSign() {
		for (int i = 0; i < wd; i++)
			for (int j = 0; j < wd; j++)
				sign[i][j] = 0;
	}

	// �ҵ� ��Ӧ��λ��
	public Dot findDot(int what) {
		Dot pen = new Dot();

		for (int i = 0; i < wd; i++)
			for (int j = 0; j < wd; j++)
				if (map[i][j] == what) {
					pen.x = j;
					pen.y = i;

					return pen;
				}
		return findDot(2);
	}

	// �������ڵ���
	class nodeDot {

		// ����Ͳ���
		public Dot dot;
		public int step = 0;

		public nodeDot() {
			dot = new Dot();
		}

		public nodeDot(Dot dot) {
			this.dot = dot;
		}
	}
}