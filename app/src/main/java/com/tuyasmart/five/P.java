package com.tuyasmart.five;


public class P {
	T a[]=new T[230];
	int len=0;
public void add(int x,int y,int score,int s[])
	{
	    T aa=new T();
		aa.x=x;
		aa.y=y;
		aa.score=score;
		for(int i=0;i<11;i++)
		aa.id[i]=s[i];
		a[len]=aa;
		len++;
	}
}
