package com.monopoly.states;

import java.awt.Color;
import java.awt.Graphics;

import com.monopoly.Handler;
import com.monopoly.gfx.Assets;
import com.monopoly.gfx.Utils;
import com.monopoly.ui.Button;
import com.monopoly.ui.Clickable;
import com.monopoly.ui.Slideshow;
import com.monopoly.ui.UIManager;

public class SetupState extends State {

	private UIManager uiManager;
	private boolean initRun;
	private int[] playerIndexes, botIndexes;
	private static Slideshow playerOneSlide, playerTwoSlide, playerThreeSlide, playerFourSlide;

	public SetupState(Handler handler) {
		this.handler = handler;
		uiManager = new UIManager(handler);
		
		initRun=true;

		playerIndexes = new int[4];
		playerIndexes[0] = 0;
		playerIndexes[1] = -1;
		playerIndexes[2] = -1;
		playerIndexes[3] = -1;
		
		botIndexes = new int[4];
		botIndexes[0] = -1;
		botIndexes[1] = -1;
		botIndexes[2] = -1;
		botIndexes[3] = -1;
		
		
		uiManager.addObject(new Button(1000, 910, 608, 80, Assets.nextButtons, new Clickable() {
			@Override
			public void onClick() {
				handler.getGame().setPlayerIcon(playerIndexes);
				int recentIndex=0;
				for(int i=0;i<handler.getGame().getNumBots();i++) {
					botIndexes[i] = recentIndex+i;
					while(botIndexes[i] == playerIndexes[0] || botIndexes[i] == playerIndexes[1] || botIndexes[i] == playerIndexes[2] || botIndexes[i] == playerIndexes[3] || botIndexes[i] == playerIndexes[0] || botIndexes[i] == playerIndexes[1] || botIndexes[i] == playerIndexes[2] || botIndexes[i] == playerIndexes[3]) {
						botIndexes[i]++;
						recentIndex++;
					}
				}
				handler.getGame().setBotIcon(botIndexes);
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
			}
		}));

		playerOneSlide = new Slideshow(1154, 80 + 215 * 0, 300, 160, Assets.gamePieces, 0.4, 0.5, 0, new Clickable() {
			@Override
			public void onClick() {
				playerIndexes[0] = playerOneSlide.getImageIndex();
				if(playerIndexes[0]<0)
					playerIndexes[0] = Assets.gamePieces.length+playerIndexes[0];
				playerIndexes[0] = playerIndexes[0] % Assets.gamePieces.length;
				if (playerOneSlide.isRightHover()) {
					while (playerIndexes[0] == playerIndexes[1] || playerIndexes[0] == playerIndexes[2]
							|| playerIndexes[0] == playerIndexes[3]) {
						playerIndexes[0]++;
					}
				} else if (playerOneSlide.isLeftHover()) {
					while (playerIndexes[0] == playerIndexes[1] || playerIndexes[0] == playerIndexes[2]
							|| playerIndexes[0] == playerIndexes[3]) {
						playerIndexes[0]--;
					}
				}
				playerOneSlide.setImageIndex(playerIndexes[0]);

			}
		});

		playerTwoSlide = new Slideshow(1154, 80 + 215 * 1, 300, 160, Assets.gamePieces, 0.4, 0.5, 1, new Clickable() {
			@Override
			public void onClick() {
				playerIndexes[1] = playerTwoSlide.getImageIndex();
				if(playerIndexes[1]<0)
					playerIndexes[1] = Assets.gamePieces.length+playerIndexes[1];
				playerIndexes[1] = playerIndexes[1] % Assets.gamePieces.length;
				if (playerTwoSlide.isRightHover()) {
					while (playerIndexes[1] == playerIndexes[0] || playerIndexes[1] == playerIndexes[2]
							|| playerIndexes[1] == playerIndexes[3]) {
						playerIndexes[1]++;
					}
				} else if (playerTwoSlide.isLeftHover()) {
					while (playerIndexes[1] == playerIndexes[0] || playerIndexes[1] == playerIndexes[2]
							|| playerIndexes[1] == playerIndexes[3]) {
						playerIndexes[1]--;
					}
				}
				playerTwoSlide.setImageIndex(playerIndexes[1]);
			}
		});

		playerThreeSlide = new Slideshow(1154, 80 + 215 * 2, 300, 160, Assets.gamePieces, 0.4, 0.5, 2, new Clickable() {
			@Override
			public void onClick() {
				playerIndexes[2] = playerThreeSlide.getImageIndex();
				if(playerIndexes[2]<0)
					playerIndexes[2] = Assets.gamePieces.length+playerIndexes[2];
				playerIndexes[2] = playerIndexes[2] % Assets.gamePieces.length;
				if (playerThreeSlide.isRightHover()) {
					while (playerIndexes[2] == playerIndexes[0] || playerIndexes[2] == playerIndexes[1]
							|| playerIndexes[2] == playerIndexes[3]) {
						playerIndexes[2]++;
					}
				} else if (playerThreeSlide.isLeftHover()) {
					while (playerIndexes[2] == playerIndexes[0] || playerIndexes[2] == playerIndexes[1]
							|| playerIndexes[2] == playerIndexes[3]) {
						playerIndexes[2]--;
					}
				}
				playerThreeSlide.setImageIndex(playerIndexes[2]);

			}
		});

		playerFourSlide = new Slideshow(1154, 80 + 215 * 3, 300, 160, Assets.gamePieces, 0.4, 0.5, 3, new Clickable() {
			@Override
			public void onClick() {
				playerIndexes[3] = playerFourSlide.getImageIndex();
				if(playerIndexes[3]<0)
					playerIndexes[3] = Assets.gamePieces.length+playerIndexes[3];
				playerIndexes[3] = playerIndexes[3] % Assets.gamePieces.length;
				if (playerFourSlide.isRightHover()) {
					while (playerIndexes[3] == playerIndexes[0] || playerIndexes[3] == playerIndexes[1]
							|| playerIndexes[3] == playerIndexes[2]) {
						playerIndexes[3]++;
					}
				} else if (playerFourSlide.isLeftHover()) {
					while (playerIndexes[3] == playerIndexes[0] || playerIndexes[3] == playerIndexes[1]
							|| playerIndexes[3] == playerIndexes[2]) {
						playerIndexes[3]--;
					}
				}
				playerFourSlide.setImageIndex(playerIndexes[3]);

			}
		});

		uiManager.addObject(playerOneSlide);

	}

	public void update() {
		if(initRun) {
			if (handler.getGame().getNumPlayers() > 1) {
				playerIndexes[1] = 1;
				uiManager.addObject(playerTwoSlide);
			}

			if (handler.getGame().getNumPlayers() > 2) {
				playerIndexes[2] = 2;
				uiManager.addObject(playerThreeSlide);
			}

			if (handler.getGame().getNumPlayers() > 3) {
				playerIndexes[3] = 3;
				uiManager.addObject(playerFourSlide);
			}
			initRun=false;
		}
		if (State.getState() == this) {
			handler.getMouseManager().setUIManager(uiManager);
		}
		uiManager.update();
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1000, 1000);
		g.fillRect(1000, 0, 618, 1000);

		g.setColor(new Color(153, 204, 255));
		g.fillRect(1000, 10, 608, 980);

		g.drawImage(Assets.gameBoard, 10, 10, 980, 980, null);

		for (int i = 0; i < handler.getGame().getNumPlayers(); i++) {
			Utils.drawString(g, "Player " + (i + 1), 1304, 50 + 215 * i, true, Color.black, Assets.kabel48);
		}

		uiManager.render(g);
	}

}
