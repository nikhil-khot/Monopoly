package com.monopoly.states;

import java.awt.Graphics;

import com.monopoly.Handler;

public abstract class State {
	
	protected Handler handler;
	
	private static State currentState = null;
	
	public static void setState(State state) {
		currentState = state;
	}
	public static State getState() {
		return currentState;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
}
