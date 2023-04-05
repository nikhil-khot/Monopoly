package com.monopoly.states;

import java.awt.Color;
import java.awt.Graphics;

import com.monopoly.Handler;
import com.monopoly.gfx.Assets;
import com.monopoly.ui.Button;
import com.monopoly.ui.Clickable;
import com.monopoly.ui.UIManager;

public class MenuState extends State{
	
	private UIManager uiManager;
	
	public MenuState(Handler handler) {
		this.handler=handler;
		uiManager = new UIManager(handler);
		
		uiManager.addObject(new Button(646,585,496,150,Assets.startButtons, new Clickable() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().settingsState);
			}}));
	}

	@Override
	public void update() {
		if(State.getState() == this) {
			handler.getMouseManager().setUIManager(uiManager);
		}
		uiManager.update();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1000, 1000);
		g.fillRect(1000, 0, 618, 1000);
		
		g.setColor(new Color(153,204,255));
		g.fillRect(1000,10,608,980);
		
		g.drawImage(Assets.startScreen, 0, 00, 1618, 1000,null);
		
		uiManager.render(g);
	}

}
