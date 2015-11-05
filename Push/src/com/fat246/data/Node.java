package com.fat246.data;

public class Node {
	// 箱子的位置
	public Dot box;

	// 人的位置
	public Dot pen;

	// 记录步数
	public int step = 0;
	
	//记录箱子的步数
	public int boxStep=0;

	// 记录方向
	public int dir;

	public Node() {
		box = new Dot();
		pen = new Dot();
	};

	public Node(Dot box, Dot pen) {
		this.box = box;
		this.pen = pen;
	}
}
