package game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Server 
{ 
	public static void main(String[] args)
	{
		if(args.length != 1) 
		{
			System.err.println("Wrong args, specify a port");
			return;
		}
		
		GameThread game = new GameThread();
		Thread gt = new Thread(game);
		gt.start();
		
		try(ServerSocket listener = new ServerSocket(Integer.parseInt(args[0]))) 
		{
			System.out.println("Listening on " + args[0]);
			ExecutorService pool = Executors.newFixedThreadPool(10);
			while(true) 
			{
				pool.execute(game.connectClient(listener.accept()));
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
} 
