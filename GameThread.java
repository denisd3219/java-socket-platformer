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
	public GameStorage storage;
	
	public boolean running = true;
	final int TARGET_UPS = 20;
	final long UPS = 1000000000 / TARGET_UPS;
	long lastLoopTime = System.nanoTime();
	
	public void run()
	{
		System.out.println("Game thread running");
		setup();
		while(running)
		{
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double dt = updateLength / ((double)UPS);
			
			update(dt);
			try
			{
				broadcast();
			}
			catch(Exception e){e.printStackTrace();} 
		}
	}
	
	private void setup()
	{
		factory = new GameObjectFactory();
		storage = new GameStorage();
		
		storage.registerObject(factory.createObject("platform"));
		
	}
	
	private void update(double dt)
	{
		for(GameObject o : storage.objects)
		{
			if(o == null){continue;}
			o.update(dt);
		}
	}
	
	private void broadcast() throws IOException
	{
		ArrayList<CRec> recsToSend = new ArrayList();
		
		for(GameObject o : storage.objects)
		{
			recsToSend.add(o.getDataToSend());
		}
		
		for(ClientThread ct : storage.clients)
		{
			ObjectOutputStream out = ct.getOutStream();
			if(out == null){continue;}
			out.reset();
			out.writeObject(recsToSend);
		}
	}
	
	public ClientThread connectClient(Socket socket)
	{
		ClientThread pc = new ClientThread(socket);
		
		Player p = (Player)factory.createObject("player");
		p.inputsRef = pc.inputs;
		
		
		storage.registerObject(p);
		storage.registerClient(pc);
		
		System.out.println(storage.clients);
		
		return pc;
	}
}