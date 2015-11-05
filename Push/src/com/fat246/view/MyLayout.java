package com.fat246.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MyLayout extends LinearLayout{

	public MyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(heightMeasureSpec, heightMeasureSpec);
	}
}
