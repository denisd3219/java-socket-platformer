package game.shared;

import java.io.Serializable;
	
public class CRec implements Serializable
{
	public int x, y, w, h, r, g, b;
	
	public CRec()
	{
		this.x = 0;
		this.y = 0;
		this.w = 1;
		this.h = 1;
		this.r = 0;
		this.g = 0;
		this.b = 0;
	}
	
	public CRec(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.r = 0;
		this.g = 0;
		this.b = 0;
	}
}