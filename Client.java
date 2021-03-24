package game.client;

import game.shared.CRec;
import game.shared.PlayerInputs;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.IOException;
import java.net.Socket;

import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*; 

import java.util.ArrayList;

public class Client{

	Socket socket;
	ObjectOutputStream inputOut;
	ObjectInputStream gameDataIn;
		
	String serverAddress;
	int serverPort;
	
	ArrayList<CRec> recsToRender;
	PlayerInputs inputs;
	
	GameCanvas gc;
	int cwidth = 400;
	int cheight = 300;

	public Client(String serverAddress, int serverPort)
	{
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}
	
	public class GameCanvas extends JFrame implements KeyListener
	{
		public Canvas c;
		
		GameCanvas()
		{ 
			super("canvas"); 
	  
			c = new Canvas() 
			{ 
				public void paint(Graphics g) 
				{ 
				} 
			}; 
	  
			c.setBackground(Color.gray); 
	  
			c.addKeyListener(this); 
	  
			add(c); 
			setSize(cwidth, cheight); 
			show(); 
		} 
		
		/*
		37 -- Left
		38 -- Up
		39 -- Right
		*/
		public void keyTyped(KeyEvent e){}
		
		public void keyPressed(KeyEvent e) 
		{
			if(!e.isActionKey()) {return;}
			switch(e.getKeyCode()) 
			{ 
				case KeyEvent.VK_UP:
					inputs.up = true;
					break;
				case KeyEvent.VK_DOWN: 
					break;
				case KeyEvent.VK_LEFT:
					inputs.left = true;
					break;
				case KeyEvent.VK_RIGHT :
					inputs.right = true;
					break;
			}
		}

		public void keyReleased(KeyEvent e) 
		{
			if(!e.isActionKey()){return;}
			switch(e.getKeyCode()) 
			{ 
				case KeyEvent.VK_UP:
					inputs.up = false;
					break;
				case KeyEvent.VK_DOWN: 
					break;
				case KeyEvent.VK_LEFT:
					inputs.left = false;
					break;
				case KeyEvent.VK_RIGHT :
					inputs.right = false;
					break;
			}			
		}
		
	}
	
	private void run()
	{
		try 
		{
			inputs = new PlayerInputs();
			gc = new GameCanvas();
		
			Socket socket = new Socket(serverAddress, serverPort);
			inputOut = new ObjectOutputStream(socket.getOutputStream());
			gameDataIn = new ObjectInputStream(socket.getInputStream());
						
			while (true)
			{
				inputOut.reset();
				inputOut.writeObject(inputs);
				
				recsToRender = (ArrayList<CRec>)gameDataIn.readObject();
				
				if(recsToRender == null){continue;}
				
				Graphics g = gc.c.getGraphics(); 
				g.setColor(Color.red);
				g.clearRect(0,0,cwidth,cheight);
				for(CRec crec : recsToRender)
				{
					if(crec == null){continue;}
					g.fillOval(crec.x + cwidth/2, cheight/2 - crec.y - crec.h, crec.w, crec.h); 
				}
			}
		} 
		catch(Exception e){e.printStackTrace();}
	}

	public static void main(String[] args)
	{
		if (args.length != 2) 
		{
			System.err.println("Wrong args, specify an address and a port");
			return;
		}

		Client client = new Client(args[0], Integer.parseInt(args[1]));
		client.run();
	}
}