package com.fat246.tool;

import java.util.LinkedList;
import java.util.Queue;

import com.fat246.data.*;

public class Search {

	// 地图
	private int[][] map;

	// 方向数组
	private int[][] dir = new int[4][2];

	// 地图的宽度
	private int wd = 7;

	// 标记数组,子搜索标记
	private int[][] sign = new int[wd][wd];

	// 标记数组,map数组标记双向标记
	private int[][][] signMap = new int[wd][wd][4];

	// 终点
	private Dot end;

	// 队列
	private Queue<Node> queue = new LinkedList<Node>();

	// 子搜索队列
	private Queue<nodeDot> que = new LinkedList<Search.nodeDot>();

	public Search() {

		initDir();
	}

	// 初始化方向数组
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

	// 返回最短路径，如果不能到达 则返回-1
	public Node search(int[][] map) {

		// 设置地图
		this.map = map;

		// 将map标记数组清零
		clearSignMap();

		// 清除队列
		queue.clear();

		// 找到人的位置
		Dot pen = findDot(2);

		// 找到箱子的起始位置
		Dot start = findDot(3);

		// 找到终点
		end = findDot(4);

		// 队列节点
		Node fir = new Node(start, pen);
		fir.dir = 2;
		fir.step = 0;
		fir.boxStep=0;

		// 临时的节点
		Node tmp;

		// 将箱子的起始位置加入到队列
		queue.offer(fir);

		// 搜索开始
		while (!queue.isEmpty()) {

			// 取出头
			fir = queue.poll();

			// 上下左右遍历四个方向
			for (int i = 0; i < 4; i++) {

				tmp = new Node();
				tmp.box.y = fir.box.y + dir[i][0];
				tmp.box.x = fir.box.x + dir[i][1];
				tmp.dir = i;

				//继承步数
				tmp.step=fir.step;
				tmp.boxStep=fir.boxStep+1;
				
				// 检察是否可以入队
				if (!checkNode(fir, tmp))
					continue;
				
				// 判断是不是终点
				if (tmp.box.y == end.y && tmp.box.x == end.x) 
					return tmp;
					
				queue.offer(tmp);

				// 标记已经走过
				signMap[tmp.box.y][tmp.box.x][tmp.dir] = 1;

			}
		}

		return null;
	}

	// 将map标记数组清零
	private void clearSignMap() {

		for (int i = 0; i < wd; i++)
			for (int j = 0; j < wd; j++)
				for (int k = 0; k < 4; k++)
					signMap[i][j][k] = 0;
	}

	private boolean checkNode(Node fir, Node tmp) {

		// 检察边界
		if (!checkBor(tmp.box))
			return false;

		// 检察是否是墙
		if (!checkWall(tmp.box))
			return false;

		// 检查是否走过
		if (signMap[tmp.box.y][tmp.box.x][tmp.dir] == 1)
			return false;

		// 检察是否可达
		if (!checkCanGo(fir, tmp))
			return false;

		// 可达的情况下将人的最终位置记录
		tmp.pen.y = fir.box.y;
		tmp.pen.x = fir.box.x;
		tmp.step++;

		return true;
	}

	// 检查是否是墙
	private boolean checkWall(Dot tmp) {

		if (map[tmp.y][tmp.x] == 1)
			return false;

		return true;
	}

	// 检察边界
	private boolean checkBor(Dot tmp) {

		if (tmp.y < 0 || tmp.y >= wd)
			return false;
		if (tmp.x < 0 || tmp.x >= wd)
			return false;

		return true;
	}

	// 用搜索检察是否可达
	private boolean checkCanGo(Node fir, Node tmp) {

		// 人的终点
		Dot sto = new Dot();

		// 用于算出人的位置
		int key = (tmp.dir + 2) % 4;

		// 算出人应该到达的位置
		sto.y = fir.box.y + dir[key][0];
		sto.x = fir.box.x + dir[key][1];

		// 检察人的位置是否已经出了边界
		if (!checkBor(sto))
			return false;

		// 检察人的位置是否可达
		if (!checkWall(sto))
			return false;

		// 将子搜索队列清空
		que.clear();

		// 标记数组清零
		clearSign();

		// 人的开始的位置
		Dot sta = new Dot(fir.pen.x, fir.pen.y);

		// 临时点
		Dot tp;
		nodeDot notp;

		// 看看是不是已经在位置了
		if (sta.y == sto.y && sta.x == sto.x)
			return true;

		nodeDot node = new nodeDot(sta);

		// 入队头结点
		que.offer(node);

		// 标记头结点已经走过
		sign[sta.y][sta.x] = 1;

		// 搜索开始
		while (!que.isEmpty()) {

			// 取出头结点
			node = que.poll();

			for (int i = 0; i < 4; i++) {

				tp = new Dot();
				tp.y = node.dot.y + dir[i][0];
				tp.x = node.dot.x + dir[i][1];

				// 检察是否是边界或者是墙
				if (!checkBor(tp))
					continue;
				if (!checkWall(tp))
					continue;

				// 检察是否走过
				if (sign[tp.y][tp.x] == 1)
					continue;

				// 包装
				notp = new nodeDot(tp);
				notp.step = node.step + 1;

				// 检察是不是终点
				if (tp.y == sto.y && tp.x == sto.x) {
					
					tmp.step += notp.step;

					return true;
				}

				// 检察是否是箱子
				if (tp.y == fir.box.y && tp.x == fir.box.x)
					continue;

				// 推入队列
				que.offer(notp);

				// 标记
				sign[tp.y][tp.x] = 1;
			}
		}
		return false;
	}

	// 将地图里面的标记清除
	private void clearSign() {
		for (int i = 0; i < wd; i++)
			for (int j = 0; j < wd; j++)
				sign[i][j] = 0;
	}

	// 找到 相应的位置
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

	// 子搜索节点类
	class nodeDot {

		// 坐标和步数
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