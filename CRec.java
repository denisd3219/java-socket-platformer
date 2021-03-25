package game.shared;

import java.io.Serializable;
	
public class CRec implements Serializable
{
	public int x, y, w, h, c;
	
	public CRec()
	{
		this.x = 0;
		this.y = 0;
		this.w = 1;
		this.h = 1;
		this.c = 0;
	}
	
	public CRec(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.c = 0;
	}
}