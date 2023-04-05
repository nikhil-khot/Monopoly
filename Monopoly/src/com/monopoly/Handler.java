package com.monopoly;

import com.monopoly.input.MouseManager;

public class Handler {

	Monopoly game; 
	
	public Handler(Monopoly game) {
		this.game = game;
	}

	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public Monopoly getGame() {
		return game;
	}
	
}
