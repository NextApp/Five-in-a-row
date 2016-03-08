package com.tuyasmart.five;


public class Chessman {
    int len=0;
    int x[]=new int[420];
	int y[]=new int[420];
	public void add(int xx,int yy) {
    	x[len] = xx;
		y[len++] = yy;
	}
	public void delete() {
		len--;
	}
}
