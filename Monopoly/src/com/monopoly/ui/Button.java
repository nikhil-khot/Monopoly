package com.monopoly.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.monopoly.gfx.Utils;

public class Button extends UIObject{

	private BufferedImage[] images;
	private Clickable clicker;
	private String text;
	private Font font;
	
	public Button(float x, float y, int width, int height, BufferedImage[] images, Clickable clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;
	}
	
	public Button(float x, float y, int width, int height, BufferedImage[] images, Font font, String text, boolean active, Clickable clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;
		this.font=font;
		this.text=text;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		if(hovering) {
			g.drawImage(images[1],(int) x, (int) y , width, height, null);
		}else {
			g.drawImage(images[0],(int) x, (int) y , width, height, null);
		}
		
		if(font!=null) 
			Utils.drawString(g, text, (int) (x+width/2), (int) (y+height/2), true, Color.BLACK, font);
	}

	@Override
	public void onClick() {
		clicker.onClick();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
