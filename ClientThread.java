package game.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import game.shared.PlayerInputs;

public class ClientThread implements Runnable 
	{
		private Socket socket;
		private ObjectInputStream inputIn;
		private ObjectOutputStream gameDataOut;
		public PlayerInputs inputs;

		ClientThread(Socket socket) 
		{
			this.socket = socket;
			inputs = new PlayerInputs();
		}
		
		@Override
		public void run() 
		{
			System.out.println("Connected: " + socket);
			try 
			{
				setup();
				processIO();
			} 
			catch(Exception e){e.printStackTrace();} 
			finally 
			{
				try 
				{
					System.out.println("Closing: " + socket); 
					socket.close();
				}
				catch(Exception e){e.printStackTrace();} 
			}
		}
		
		private void setup() throws IOException
		{
			inputIn = new ObjectInputStream(socket.getInputStream());
			gameDataOut = new ObjectOutputStream(socket.getOutputStream());
		}
		
		private void processIO() throws IOException, ClassNotFoundException
		{
			while(true)
			{
				inputs = (PlayerInputs)inputIn.readObject();
			}
		}
		
		public ObjectOutputStream getOutStream()
		{
			return gameDataOut;
		}
	
	}