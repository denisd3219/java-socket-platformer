package game.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.ArrayList;

import game.shared.CRec;
import game.shared.PlayerInputs;

public class BroadcasterThread implements Runnable 
{ 	
	public GameState state;
	public boolean running = true;
	
	public void run()
	{
		System.out.println("Broadcaster thread running");
		setup();
		while(running)
		{
			try{broadcast();}
			catch(Exception e){e.printStackTrace();}
		}
	}
	
	private void setup()
	{
	}
	
	private void broadcast() throws IOException
	{
		ArrayList<CRec> recsToSend = new ArrayList();
		
		for(GameObject o : state.objects)
		{
			recsToSend.add(o.getDataToSend());
		}
		
		for(ClientThread ct : state.clients)
		{
			ObjectOutputStream out = ct.getOutStream();
			if(out == null){continue;}
			out.reset();
			out.writeObject(recsToSend);
		}
	}
}