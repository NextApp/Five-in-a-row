package com.tuyasmart.five;

public class Nouser {
	Node a[] = new Node[1000];
	int len = 1;
	int vis[][]=new int[20][20];
	int add(Node x) {
		int i = 1;
		len++;
		if (len != 2) {
			for (i = len - 1; i > 1; i = i >> 1) {
				if (x.score > a[i >> 1].score)
					{
					     a[i] = a[i >> 1];
					     vis[a[i].x][a[i].y]=i;
					}
				else
					break;
			}
		}
		a[i] = x;
		vis[a[i].x][a[i].y]=i;
		return i;
	}
	int addNode(int x, int y, int score)
	{
		Node aa = new Node();
		aa.x = x;
		aa.y = y;
		aa.score = score;
    	return 	add(aa);
	}

	Node delete(int x) {
		int tmp, i=0;
		if(len>1)len--;
		else return null;
		Node as = new Node();
		as.score = a[x].score;
		as.x=a[x].x;
		as.y=a[x].y;
		vis[as.x][as.y]=0;
		for (i = x; (i << 1) < len; i = tmp) {
			tmp = ((i << 1) + 1 < len && a[(i << 1) + 1].score > a[i << 1].score) ? (i << 1) + 1
					: i << 1;
			if (a[tmp].score > a[len].score)
				{
				      a[i] = a[tmp];
				      vis[a[i].x][a[i].y]=i;
				}
			else
				break;
		}
		a[i] = a[len];
		vis[a[i].x][a[i].y]=i;
		
		return as;
	}
	Node deleteT(int x, int y)
	{
		if(vis[x][y]!=0)
		{
		 return  delete(vis[x][y]);	
		}
		return null;
	}
	public static void main(String arg[]) {
		Nouser my = new Nouser();
		Node a = new Node();
		a.x = 0;
		a.y = 2;
		a.score = 10;
		my.add(a);

		Node b = new Node();
		b.x = 0;
		b.y = 2;
		b.score = 155;
		my.add(b);
		Node c = new Node();
		c.score = 12;
		my.add(c);
		Node d = new Node();
		d.score = 11;
		my.add(d);
		// System.out.println(my.a[1].score);
		// System.out.println(my.a[2].score);
		// System.out.println(my.a[3].score);
		for (int i = 0; i < 4; i++) {
			System.out.println(my.len);
			System.out.println(my.delete(1).score);
		}

	}

}
