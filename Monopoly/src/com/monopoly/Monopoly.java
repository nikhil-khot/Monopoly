package com.monopoly;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.monopoly.gfx.Assets;
import com.monopoly.gfx.Display;
import com.monopoly.input.MouseManager;
import com.monopoly.states.GameState;
import com.monopoly.states.MenuState;
import com.monopoly.states.SettingsState;
import com.monopoly.states.SetupState;
import com.monopoly.states.State;




public class Monopoly implements Runnable{

	//JFrame
	private Display display;
	private int width, height;
	private String title;
	
	//Multithreading
	private boolean running = false;
	private Thread thread;
	
	//Graphics
	private BufferStrategy bs;
	private Graphics g;
	
	//Input Managers
	private MouseManager mouseManager;

	//Handler
	private Handler handler;
	
	//Players
	private int numPlayers, numBots;
	private int[] playerIcon, botIcon;
	
	//States
	public State menuState;
	public State settingsState;
	public State setupState;
	public State gameState;
	
	public Monopoly (String title, int width, int height) {
		this.width=width;
		this.height=height;
		this.title=title;
		
		mouseManager = new MouseManager();
	}
	
	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getFrame().addMouseWheelListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseWheelListener(mouseManager);
		Assets.init();
		
		handler = new Handler(this);
		
		numPlayers=0;
		playerIcon = new int[4];
		playerIcon[0]=-1;
		playerIcon[1]=-1;
		playerIcon[2]=-1;
		playerIcon[3]=-1;
		
		numBots=0;
		botIcon = new int[4];
		botIcon[0]=-1;
		botIcon[1]=-1;
		botIcon[2]=-1;
		botIcon[3]=-1;
		
		menuState = new MenuState(handler);
		settingsState = new SettingsState(handler);
		setupState = new SetupState(handler);
		gameState = new GameState(handler);
		
		State.setState(menuState);
	}
	
	private void update() {
		if(State.getState() != null) 
			State.getState().update();
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs==null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		g.clearRect(0,0,width,height);
		
		if(State.getState() != null)
			State.getState().render(g);
		
		bs.show();
		g.dispose();
	}
	
	public void run() {
		init();
		int fps;
		int fpsLimit = 30;
		double timePerTick = 1000000000 / fpsLimit;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now-lastTime;
			lastTime=now;
			
			if(delta >= 1) {
				update();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000) {
				fps = ticks;
				System.out.println("FPS: " + fps);
				ticks=0;
				timer=0;
			}
		}
		
		stop();
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public int getNumBots() {
		return numBots;
	}

	public void setNumBots(int numBots) {
		this.numBots = numBots;
	}
	
	public int[] getPlayerIcon() {
		return playerIcon;
	}

	public void setPlayerIcon(int[] playerIcon) {
		this.playerIcon = playerIcon;
	}

	public int[] getBotIcon() {
		return botIcon;
	}

	public void setBotIcon(int[] botIcon) {
		this.botIcon = botIcon;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	public Display getDisplay() {
		return display;
	}
	
}
