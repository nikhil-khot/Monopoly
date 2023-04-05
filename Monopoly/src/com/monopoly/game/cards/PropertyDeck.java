package com.monopoly.game.cards;

import java.awt.image.BufferedImage;

public class PropertyDeck {

	private PropertyCard[] usedCards, unusedCards, rawDeck;
	
	public PropertyDeck(PropertyCard[] unusedCards) {
		this.unusedCards=unusedCards;
		this.rawDeck=unusedCards;
		this.usedCards=new PropertyCard[unusedCards.length];
	}
	
	public PropertyCard getCard(int index) {
		return unusedCards[index];
	}
	
	public PropertyCard[] getRawDeck() {
		return rawDeck;
	}
	
	public BufferedImage[] getRawDeckImages() {
		BufferedImage[] propImages = new BufferedImage[rawDeck.length];
		for(int i=0;i<propImages.length;i++) {
			propImages[i] = rawDeck[i].getFront();
		}
		return propImages;
	}
	
	public void setCardInUse(int index) {
		usedCards[index]=unusedCards[index];
	}
	
	public boolean cardInUse(int index) {
		if(usedCards[index]==null)
			return false;
		return true;
	}
	
	
}
