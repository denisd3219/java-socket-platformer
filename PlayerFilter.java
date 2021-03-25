package game.server;

import java.util.List;
import java.util.ArrayList;

public class PlayerFilter implements GameObjectFilter
{
    public List<GameObject> filter(List<GameObject> objects)
    {
        List<GameObject> players = new ArrayList<GameObject>();
        for(GameObject curObj : objects)
        {
			if(curObj instanceof Player)
			{
				players.add(curObj);
			}
        }
		return players;
    }
}