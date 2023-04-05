package com.monopoly.game.cards;

import java.awt.image.BufferedImage;

public class BoardCard extends Card{

	int id;
	
	public BoardCard(BufferedImage front, BufferedImage back, int x, int y, int animSpeed, int id) {
		super(front, back, x, y, animSpeed);
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
