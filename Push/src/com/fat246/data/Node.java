package com.fat246.data;

public class Node {
	// ���ӵ�λ��
	public Dot box;

	// �˵�λ��
	public Dot pen;

	// ��¼����
	public int step = 0;
	
	//��¼���ӵĲ���
	public int boxStep=0;

	// ��¼����
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
