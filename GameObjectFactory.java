package game.server;

import game.shared.CRec;
import game.shared.PlayerInputs;

import java.util.ArrayList;

public class GameObjectFactory
{
	public GameObject createObject(String type)
	{
		if(type == null)
		{
			return null;
		}		
		if(type.equalsIgnoreCase("platform"))
		{
			Platform p = new Platform();
			
			p.pos.x = -10f;
			p.pos.y = -5f;
			p.bounds.x = 20f;
			p.bounds.y = 3f;
			
			return p;
		} 
		else if(type.equalsIgnoreCase("player"))
		{
			Player p = new Player();
			
			p.bounds.x = 5f;
			p.bounds.y = 7f;
			
			return p;
		}
		return null;
	}	
}