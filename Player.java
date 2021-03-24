package game.server;

import game.shared.CRec;
import game.shared.PlayerInputs;

import java.util.ArrayList;

public class Player implements GameObject
{
	public CRec intRec;
	public Vec2<Float> pos;
	public Vec2<Float> bounds;
	public Vec2<Float> vel;
	
	float walkSpeed = 1f;
	float gravity = 0f;
	
	public PlayerInputs inputsRef;
	public ArrayList<GameObject> otherObjectsRef;
	
	public Player()
	{
		intRec = new CRec();
		pos = new Vec2(0f, 0f);
		bounds = new Vec2(1f, 1f);
		vel = new Vec2(0f, 0f);
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
	
	public boolean checkHorizontalCollision(GameObject other)
	{
		Vec2<Float> otherPos = other.getPos();
		Vec2<Float> otherBounds = other.getBounds();
		return pos.x + bounds.x  > otherPos.x && pos.x < otherPos.x + otherBounds.x;
	}
	
	public void update(double dt)
	{
		if(otherObjectsRef == null){return;}

		float minDistanceToDense = 9999f;

		GameObjectFilter platform = new PlatformFilter();

		for(GameObject p : platform.filter(otherObjectsRef))
		{
			if(checkHorizontalCollision(p))
			{
				Vec2<Float> otherPos = p.getPos();
				Vec2<Float> otherBounds = p.getBounds();
				if(pos.y >= otherPos.y + otherBounds.y)
				{
					minDistanceToDense = pos.y - otherPos.y + otherBounds.y;
				}
			}
		}
		/*
		if(inputsRef != null && false)
		{
			float walkMod = ((inputsRef.right ? 1f : 0f) - (inputsRef.left ? 1f : 0f));
			velX = walkSpeed * walkMod * (float)dt;
				
			if(minDistanceToDense == 0)
			{
				velY += 10*(inputsRef.up ? 1 : 0);
			}				
		}
		
		
			
		if(minDistanceToDense != 0)
		{
			if(gravity != 0)
			{
				velY -= gravity;
			}
			if(velY > minDistanceToDense)
			{
				velY = minDistanceToDense;
			}
		}
		else
		{
			velY = 0;
		}
		*/
		vel.x = walkSpeed * (float)dt;
		pos.x += vel.x;
		pos.y += vel.y;
	}
}