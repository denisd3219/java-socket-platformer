package game.server;

import java.util.List;

public class PlatformFilter implements GameObjectFilter
{
    public List<GameObject> filter(List<GameObject> objects)
    {
        List<GameObject> platforms = new ArrayList<GameObject>();
        for(GameObject curObj : objects)
        {
			if(curObj instanceof Platform)
			{
				platforms.add(curObj);
			}
        }
		return platforms;
    }
}