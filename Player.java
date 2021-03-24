package game.server;

import game.shared.CRec;
import game.shared.PlayerInputs;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Player implements GameObject
{
	public CRec intRec;
	public Vec2<Float> pos;
	public Vec2<Float> bounds;
	public Vec2<Float> vel;
	
	float walkSpeed = 10f;
	float jumpPower = 80f;
	float gravity = 0.1f;
	
	public ClientThread playerClient;
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

		float minDistanceToPlatform = 9999f;

		List<GameObject> platforms = new PlatformFilter().filter(otherObjectsRef);

		if(platforms.size() != 0)
		{
			for(GameObject p : platforms)
			{
				if(checkHorizontalCollision(p))
				{
					Vec2<Float> otherPos = p.getPos();
					Vec2<Float> otherBounds = p.getBounds();
					if(pos.y < otherPos.y + otherBounds.y) {
						minDistanceToPlatform = Math.min(minDistanceToPlatform, pos.y - (otherPos.y + otherBounds.y));
					}
				}
			}
		}
		
		if(playerClient != null)
		{
			PlayerInputs in = playerClient.inputs;

			float walkMod = (in.right ? 1f : 0f) - (in.left ? 1f : 0f);

			vel.x = walkSpeed * walkMod;
				
			if(minDistanceToPlatform == 0f)
			{
				vel.y += jumpPower * (in.up ? 1f : 0f);
			}				
		}
			
		if(minDistanceToPlatform != 0f)
		{
			if(gravity != 0f)
			{
				vel.y -= gravity;
			}
			if(-vel.y * (float)dt >= minDistanceToPlatform)
			{
				vel.y = 0f;
				pos.y -= minDistanceToPlatform;
			}
		}
		
		pos.x += vel.x * (float)dt;
		pos.y += vel.y * (float)dt;
	}
}