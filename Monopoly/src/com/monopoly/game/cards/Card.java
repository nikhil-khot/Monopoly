package com.monopoly.game.cards;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class Card {
	
	private BufferedImage front, back, cardDisplay;
	private long timer, lastTime;
	private int animSpeed, cardWidth, cardHeight, cardX, cardY;
	private int initWidth;

	public Card(BufferedImage front, BufferedImage back, int x, int y, int animSpeed) {
		this.front = front;
		this.back = back;
		timer=0;
		lastTime=System.currentTimeMillis();
		cardDisplay = front;
		this.animSpeed=animSpeed;
		
		cardWidth=cardDisplay.getWidth();
		cardHeight=cardDisplay.getHeight();
		initWidth=cardDisplay.getWidth();
		
		cardX = x;
		cardY = y;
	}
	
	public Card(BufferedImage front, int x, int y) {
		this.front = front;
		this.back = null;
		timer=0;
		lastTime=System.currentTimeMillis();
		cardDisplay = front;
		this.animSpeed=0;
		
		cardWidth=cardDisplay.getWidth();
		cardHeight=cardDisplay.getHeight();
		initWidth=cardDisplay.getWidth();
		
		cardX = x;
		cardY = y;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(cardDisplay, cardX, cardY, cardWidth, cardHeight,null);
	}
	
	public void flipAnim() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(back==null)
			return;
		boolean cardFlipped = false;
		boolean halfFlip = false;
		while(!cardFlipped) {
			timer += System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			
			if(timer > animSpeed) {
				timer=0;
				if(!halfFlip) {
					cardWidth-=20;
					cardX+=10;
				}else {
					cardWidth+=20;
					cardX-=10;
				}
				
				if(cardWidth<=0) {
					halfFlip=true;
					if(cardDisplay==front)
						cardDisplay=back;
					else
						cardDisplay=front;
				}
			}
				
			if(halfFlip && cardWidth==initWidth) {
				cardFlipped=true;
			}
		}
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public BufferedImage getFront() {
		return front;
	}

	public void setFront(BufferedImage front) {
		this.front = front;
	}
	
}
