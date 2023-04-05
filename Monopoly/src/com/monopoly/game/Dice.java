package com.monopoly.game;

import java.awt.Graphics;

import com.monopoly.gfx.Assets;

public class Dice{

	private int x, y, width, height;
	private int roll;
	
	public Dice(int x, int y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		roll=(int) (Math.random()*6) + 1;
	}
	
	public void roll() {
		roll=(int) (Math.random()*6) + 1;
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.dice[roll-1],x,y,width,height,null);
	}
	
	public int getCurrent() {
		return roll;
	}

}
