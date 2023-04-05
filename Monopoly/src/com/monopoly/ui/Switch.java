package com.monopoly.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.monopoly.gfx.Utils;



public class Switch extends UIObject{

	private BufferedImage[] images;
	private Clickable clicker;
	private boolean active;
	private String text;
	private Font font;
	
	public Switch(float x, float y, int width, int height, BufferedImage[] images, boolean active, Clickable clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;
		this.active=active;
	}
	
	public Switch(float x, float y, int width, int height, BufferedImage[] images, Font font, String text, boolean active, Clickable clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;
		this.active=active;
		this.font=font;
		this.text=text;
	}

	@Override
	public void update() {
	
	}

	@Override
	public void render(Graphics g) {
		if(active)
			g.drawImage(images[0],(int) x, (int) y , width, height, null);
		else
			g.drawImage(images[1],(int) x, (int) y , width, height, null);
		
		if(font!=null) 
			Utils.drawString(g, text, (int) (x+width/2), (int) (y+height/2), true, Color.BLACK, font);
			
	}

	@Override
	public void onClick() {
		active=!active;
		clicker.onClick();
	}
	
	public void setActivity(boolean active) {
		this.active=active;
	}
	
	public boolean getActivity() {
		return active;
	}


}
