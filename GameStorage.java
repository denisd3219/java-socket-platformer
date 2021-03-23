package game.server;

import game.shared.CRec;
import game.shared.PlayerInputs;

import java.util.ArrayList;

public class GameStorage
{
	public ArrayList<GameObject> objects;
	public ArrayList<ClientThread> clients;
	
	GameStorage()
	{
		objects = new ArrayList();
		clients = new ArrayList();
	}

	public void registerObject(GameObject o)
	{
		if(o == null){return;}
		objects.add(o);
		if(o instanceof Player)
		{
			Player p = (Player)o;
			p.otherObjectsRef = objects;
		}
	}
	
	public void registerClient(ClientThread ct)
	{
		clients.add(ct);		
	}
}