package com.monopoly.states;

import java.awt.Color;
import java.awt.Graphics;

import com.monopoly.Handler;
import com.monopoly.gfx.Assets;
import com.monopoly.gfx.Utils;
import com.monopoly.ui.Button;
import com.monopoly.ui.Clickable;
import com.monopoly.ui.Switch;
import com.monopoly.ui.UIManager;

public class SettingsState extends State{
	
	private UIManager uiManager;
	private int numPlayers, numBots;
	private static Switch playerButtonOne, playerButtonTwo, playerButtonThree,playerButtonFour;
	private static Switch botButtonZero, botButtonOne, botButtonTwo, botButtonThree;
	
	//Gets Player Count
	//Gets Bot Count
	//Players Choose Pieces
	
	public SettingsState(Handler handler) {
		this.handler=handler;
		uiManager = new UIManager(handler);
		
		playerButtonOne = new Switch(1012, 100, 137, 137,Assets.playerSelectionButtonOne,false, new Clickable() {
			@Override
			public void onClick() {
				numPlayers=1;
				playerButtonOne.setActivity(true);
				playerButtonTwo.setActivity(false);
				playerButtonThree.setActivity(false);
				playerButtonFour.setActivity(false);
			}});
		playerButtonTwo = new Switch(1161, 100, 137, 137,Assets.playerSelectionButtonTwo,false, new Clickable() {
			@Override
			public void onClick() {
				numPlayers=2;
				playerButtonOne.setActivity(false);
				playerButtonTwo.setActivity(true);
				playerButtonThree.setActivity(false);
				playerButtonFour.setActivity(false);
			}});
		playerButtonThree = new Switch(1310, 100, 137, 137,Assets.playerSelectionButtonThree,false, new Clickable() {
			@Override
			public void onClick() {
				numPlayers=3;
				playerButtonOne.setActivity(false);
				playerButtonTwo.setActivity(false);
				playerButtonThree.setActivity(true);
				playerButtonFour.setActivity(false);
			}});
		playerButtonFour = new Switch(1459, 100, 137, 137,Assets.playerSelectionButtonFour,false, new Clickable() {
			@Override
			public void onClick() {
				numPlayers=4;
				playerButtonOne.setActivity(false);
				playerButtonTwo.setActivity(false);
				playerButtonThree.setActivity(false);
				playerButtonFour.setActivity(true);
			}});

		uiManager.addObject(playerButtonOne);
		uiManager.addObject(playerButtonTwo);
		uiManager.addObject(playerButtonThree);
		uiManager.addObject(playerButtonFour);
		
		botButtonZero = new Switch(1012, 350, 137, 137,Assets.playerSelectionButtonZero,true, new Clickable() {
			@Override
			public void onClick() {
				numBots=0;
				botButtonZero.setActivity(true);
				botButtonOne.setActivity(false);
				botButtonTwo.setActivity(false);
				botButtonThree.setActivity(false);
			}});
		botButtonOne = new Switch(1161, 350, 137, 137,Assets.playerSelectionButtonOne,false, new Clickable() {
			@Override
			public void onClick() {
				numBots=1;
				botButtonZero.setActivity(false);
				botButtonOne.setActivity(true);
				botButtonTwo.setActivity(false);
				botButtonThree.setActivity(false);
			}});
		botButtonTwo = new Switch(1310, 350, 137, 137,Assets.playerSelectionButtonTwo,false, new Clickable() {
			@Override
			public void onClick() {
				numBots=2;
				botButtonZero.setActivity(false);
				botButtonOne.setActivity(false);
				botButtonTwo.setActivity(true);
				botButtonThree.setActivity(false);
			}});
		botButtonThree = new Switch(1459, 350, 137, 137,Assets.playerSelectionButtonThree,false, new Clickable() {
			@Override
			public void onClick() {
				numBots=3;
				botButtonZero.setActivity(false);
				botButtonOne.setActivity(false);
				botButtonTwo.setActivity(false);
				botButtonThree.setActivity(true);
			}});
		
		uiManager.addObject(new Button(1000,910,608,80,Assets.nextButtons, new Clickable() {
			@Override
			public void onClick() {
				if(numBots+numPlayers<=4 && numBots+numPlayers>=2 && numPlayers>=1 ) {
					handler.getGame().setNumBots(numBots);
					handler.getGame().setNumPlayers(numPlayers);
					handler.getMouseManager().setUIManager(null);
					State.setState(handler.getGame().setupState);
				}
			}}));
		
		uiManager.addObject(botButtonZero);
		uiManager.addObject(botButtonOne);
		uiManager.addObject(botButtonTwo);
		uiManager.addObject(botButtonThree);
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
		
		g.drawImage(Assets.gameBoard, 10, 10, 980, 980,null);
		
		Utils.drawString(g, "Number of Players",1304,50, true, Color.black, Assets.kabel48);
		
		Utils.drawString(g, "Number of Bots",1304,300, true, Color.black, Assets.kabel48);
		
		Utils.drawString(g, "Total: " + (numBots+numPlayers),1304,700, true, Color.black, Assets.kabel48);
		
		Utils.drawString(g, "Keep The Total Number of Players(Including Bots) Between 1-4" ,1304,900, true, Color.black, Assets.kabel12);
		
		
		
		uiManager.render(g);
	}

}
