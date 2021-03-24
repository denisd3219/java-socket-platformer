package game.server;

import java.util.List;

public interface GameObjectFilter 
{
    public List<GameObject> filter(List<GameObject> objects);
}