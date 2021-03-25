package game.server;

import game.shared.CRec;

public class Platform implements GameObject
{
	public CRec intRec;
	public Vec2<Float> pos;
	public Vec2<Float> bounds;
	
	public Platform()
	{
		intRec = new CRec();
		pos = new Vec2(0f, 0f);
		bounds = new Vec2(1f, 1f);
	}

	public Platform(Vec2<Float> pos)
	{
		intRec = new CRec();
		this.pos = pos;
		bounds = new Vec2(1f, 1f);
	}

	public Platform(Vec2<Float> pos, Vec2<Float> bounds)
	{
		intRec = new CRec();
		this.pos = pos;
		this.bounds = bounds;
	}
	
	public Vec2<Float> getPos(){return pos;}
	public Vec2<Float> getBounds(){return bounds;}
	
	public CRec getDataToSend()
	{
		intRec.x = pos.x.intValue();
		intRec.y = pos.y.intValue();
		intRec.w = bounds.x.intValue();
		intRec.h = bounds.y.intValue();
		return intRec;
	}
	
	public void update(double dt){}	
}