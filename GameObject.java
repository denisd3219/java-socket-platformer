package game.server;

import game.shared.CRec;
import game.shared.PlayerInputs;

public interface GameObject
{
	public CRec getDataToSend();
	public Vec2<Float> getPos();
	public Vec2<Float> getBounds();
	public void update(double dt);
}