package game.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.ArrayList;

import game.shared.CRec;
import game.shared.PlayerInputs;

public class GameThread implements Runnable 
{ 	
	public GameObjectFactory factory;
	public GameState state;
	public BroadcasterThread broadcaster;
	
	public boolean running = true;
	final int TARGET_UPS = 30;
	final long UPS = 1000000000 / TARGET_UPS;
	long lastLoopTime = System.nanoTime();
	
	public void run()
	{
		System.out.println("Game thread running");
		setup();
		while(running)
		{
			long now = System.nanoTime();
			if(now < lastLoopTime + UPS){continue;}
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double dt = updateLength / ((double)UPS);
			
			update(dt);
			//broadcaster.setState(state);
		}
	}
	
	private void setup()
	{
		state = new GameState();

		broadcaster = new BroadcasterThread();
		broadcaster.state = state;

		Thread bct = new Thread(broadcaster);
		bct.start();

		state.registerObject(new Platform(new Vec2<Float>(-10f,-5f), new Vec2<Float>(20f,3f)));
		state.registerObject(new Platform(new Vec2<Float>(-65f,5f), new Vec2<Float>(20f,3f)));
		state.registerObject(new Platform(new Vec2<Float>(-30f,30f), new Vec2<Float>(20f,3f)));

		state.registerObject(new Platform(new Vec2<Float>(-100f,-50f), new Vec2<Float>(200f,5f)));
	}
	
	private void update(double dt)
	{
		for(GameObject o : state.objects)
		{
			if(o == null){continue;}
			o.update(dt);
		}
	}
	
	public ClientThread connectClient(Socket socket)
	{
		ClientThread pc = new ClientThread(socket);
		Player p = new Player(new Vec2<Float>(0f,0f), new Vec2<Float>(5f,15f));

		p.playerClient = pc;
		p.intRec.c = pc.getColor();
			
		state.registerObject(p);
		state.registerClient(pc);
		
		return pc;
	}
}