package com.monopoly.game.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BoardDeck {

	private ArrayList<BoardCard> deck;
	private int runs;

	public BoardDeck(BoardCard[] deck) {
		this.deck = new ArrayList<BoardCard>(Arrays.asList(deck));
	}

	public BoardCard drawFromDeck() {
		BoardCard card = deck.get(0);
		deck.remove(0);
		deck.add(card);
		runs++;

		if (runs % deck.size() == 0)
			shuffle();

		return card;
	}

	public void shuffle() {
		Random rand = new Random();
		for (int i = 0; i < deck.size(); i++) {
			int iToSwap = rand.nextInt(deck.size());
			BoardCard temp = deck.get(iToSwap);
			deck.set(iToSwap, deck.get(i));
			deck.set(i, temp);
		}
	}

}
