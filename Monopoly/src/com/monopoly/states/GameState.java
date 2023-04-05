package com.monopoly.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import com.monopoly.Handler;
import com.monopoly.game.Bot;
import com.monopoly.game.Dice;
import com.monopoly.game.GameLogs;
import com.monopoly.game.Player;
import com.monopoly.game.cards.BoardCard;
import com.monopoly.game.cards.DeedCard;
import com.monopoly.game.cards.PropertyCard;
import com.monopoly.game.cards.UtilityCard;
import com.monopoly.gfx.Assets;
import com.monopoly.gfx.Utils;
import com.monopoly.ui.Button;
import com.monopoly.ui.Clickable;
import com.monopoly.ui.Slideshow;
import com.monopoly.ui.Switch;
import com.monopoly.ui.UIManager;

public class GameState extends State {

	private UIManager uiManager, gameplayManager, assetsManager, tradeManager, boardManager, historyManager;

	private static Switch gameplay, assets, trade, board, history;
	private int tab;
	private Dice diceOne, diceTwo;
	private ArrayList<Player> playerList;
	private ArrayList<PropertyCard> tradeOutList, tradeInList;
	private int playerIndex, activePlayers;
	private int repeatRolls;
	private boolean initRun;
	private boolean botActive;
	private boolean runningAnim;
	private BoardCard currentCard;
	private Slideshow playerPropertySlideshow, allPropertySlideshow, tradeOutSlideshow, tradeInSlideshow,
	nameListSlideshow;
	private boolean playerRolled, payedRent;
	private static Button mortgageButton, tradeButtonOne, tradeButtonTwo, confirmButtonOne, confirmButtonTwo;
	private boolean tradeOneConfirmed, tradeTwoConfirmed;
	private static Button jailbreakButton;

	//Demo
	private boolean firstRoll = false;

	public GameState(Handler handler) {
		this.handler = handler;
		initRun = true;
		tradeOneConfirmed = false;
		tradeTwoConfirmed = false;

		uiManager = new UIManager(handler);
		gameplayManager = new UIManager(handler);
		assetsManager = new UIManager(handler);
		tradeManager = new UIManager(handler);
		boardManager = new UIManager(handler);
		historyManager = new UIManager(handler);
		tab = 1;

		diceOne = new Dice(1054, 430, 200, 200);
		diceTwo = new Dice(1354, 430, 200, 200);

		playerList = new ArrayList<Player>();
		playerIndex = 0;

		tradeOutList = new ArrayList<PropertyCard>();
		tradeInList = new ArrayList<PropertyCard>();

		allPropertySlideshow = new Slideshow(1104, 260, 400, 160, Assets.propertyDeck.getRawDeckImages(), 0.4, 0.5, 0,
				new Clickable() {
			@Override
			public void onClick() {

			}
		});

		playerPropertySlideshow = new Slideshow(1104, 340, 400, 160, null, 0.4, 0.5, 0, new Clickable() {
			@Override
			public void onClick() {

			}
		});

		tradeInSlideshow = new Slideshow(1313, 260, 300, 160, null, 0.25, 0.4, 0, new Clickable() {
			@Override
			public void onClick() {
				if (playerList.get(nameListSlideshow.getImageIndex()).getProperties().get(tradeInSlideshow.getImageIndex()).isTrading())
					tradeButtonTwo.setText("Trading");
				else
					tradeButtonTwo.setText("Trade");
			}
		});

		tradeOutSlideshow = new Slideshow(1004, 260, 300, 160, null, 0.25, 0.4, 0, new Clickable() {
			@Override
			public void onClick() {
				if (playerList.get(playerIndex).getProperties().get(tradeOutSlideshow.getImageIndex()).isTrading())
					tradeButtonOne.setText("Trading");
				else
					tradeButtonOne.setText("Trade");
			}
		});

		tradeButtonOne = new Button(1029, 500, 250, 60, Assets.buttonPalette, Assets.kabel48, "Trade", false,
				new Clickable() {
			@Override
			public void onClick() {
				boolean trading = playerList.get(playerIndex).getProperties()
						.get(tradeOutSlideshow.getImageIndex()).isTrading();
				playerList.get(playerIndex).getProperties().get(tradeOutSlideshow.getImageIndex())
				.setTrading(!trading);
				if (!trading) {
					tradeButtonOne.setText("Trading");
					tradeOutList.add(
							playerList.get(playerIndex).getProperties().get(tradeOutSlideshow.getImageIndex()));
				} else {
					tradeButtonOne.setText("Trade");
					for (int i = 0; i < tradeOutList.size(); i++) {
						if (tradeOutList.get(i) == playerList.get(playerIndex).getProperties()
								.get(tradeOutSlideshow.getImageIndex())) {
							tradeOutList.remove(i);
							break;
						}
					}
				}
			}
		});
		tradeButtonTwo = new Button(1338, 500, 250, 60, Assets.buttonPalette, Assets.kabel48, "Trade", false,
				new Clickable() {
			@Override
			public void onClick() {
				boolean trading = playerList.get(nameListSlideshow.getImageIndex()).getProperties()
						.get(tradeInSlideshow.getImageIndex()).isTrading();
				playerList.get(nameListSlideshow.getImageIndex()).getProperties().get(tradeInSlideshow.getImageIndex())
				.setTrading(!trading);
				if (!trading) {
					tradeButtonTwo.setText("Trading");
					tradeInList.add(playerList.get(nameListSlideshow.getImageIndex()).getProperties()
							.get(tradeInSlideshow.getImageIndex()));
				} else {
					tradeButtonTwo.setText("Trade");
					for (int i = 0; i < tradeInList.size(); i++) {
						if (tradeInList.get(i) == playerList.get(nameListSlideshow.getImageIndex())
								.getProperties().get(tradeInSlideshow.getImageIndex())) {
							tradeInList.remove(i);
							break;
						}
					}
				}
			}
		});
		confirmButtonOne = new Button(1029, 680, 250, 60, Assets.buttonPalette, Assets.kabel48, "Confirm", false,
				new Clickable() {
			@Override
			public void onClick() {
				tradeOneConfirmed = !tradeOneConfirmed;
				if (tradeOneConfirmed)
					confirmButtonOne.setText("Confirmed");
				else
					confirmButtonOne.setText("Confirm");
			}
		});
		confirmButtonTwo = new Button(1338, 680, 250, 60, Assets.buttonPalette, Assets.kabel48, "Confirm", false,
				new Clickable() {
			@Override
			public void onClick() {
				tradeTwoConfirmed = !tradeTwoConfirmed;
				if (tradeTwoConfirmed)
					confirmButtonTwo.setText("Confirmed");
				else
					confirmButtonTwo.setText("Confirm");
			}
		});

		nameListSlideshow = new Slideshow(1348, 90, 230, 88, null, 0.2, 0.5, 0, new Clickable() {
			@Override
			public void onClick() {
				if (nameListSlideshow.getImageIndex() == playerIndex) {
					int nextIndex = playerIndex + 1;
					nextIndex = nextIndex % handler.getGame().getNumPlayers();
					nameListSlideshow.setImageIndex(nextIndex);
				}

				if (playerList.get(nameListSlideshow.getImageIndex()).getProperties().size() == 0)
					tradeInSlideshow.setImages(null);
				else
					tradeInSlideshow.setImages(playerList.get(nameListSlideshow.getImageIndex()).getPropertyImages());
			}
		});

		gameplay = new Switch(1000, 10, 113, 60, Assets.buttonPalette, Assets.kabel24, "Gameplay", true,
				new Clickable() {
			@Override
			public void onClick() {
				tab = 1;
				gameplay.setActivity(true);
				assets.setActivity(false);
				trade.setActivity(false);
				board.setActivity(false);
				history.setActivity(false);
			}
		});
		assets = new Switch(1123, 10, 114, 60, Assets.buttonPalette, Assets.kabel24, "Assets", false, new Clickable() {
			@Override
			public void onClick() {
				tab = 2;
				gameplay.setActivity(false);
				assets.setActivity(true);
				trade.setActivity(false);
				board.setActivity(false);
				history.setActivity(false);
			}
		});
		trade = new Switch(1247, 10, 114, 60, Assets.buttonPalette, Assets.kabel24, "Trade", false, new Clickable() {
			@Override
			public void onClick() {
				if (handler.getGame().getNumPlayers() != 1) {
					tab = 3;
					if (nameListSlideshow.getImageIndex() == playerIndex) {
						int nextIndex = playerIndex + 1;
						nextIndex = nextIndex % handler.getGame().getNumPlayers();
						nameListSlideshow.setImageIndex(nextIndex);
					}
					if (playerList.get(nameListSlideshow.getImageIndex()).getProperties().size() == 0)
						tradeInSlideshow.setImages(null);
					else
						tradeInSlideshow
						.setImages(playerList.get(nameListSlideshow.getImageIndex()).getPropertyImages());

					if(playerList.get(nameListSlideshow.getImageIndex()).getProperties().size()==0)
						tradeButtonTwo.setText("Trade");
					else if (playerList.get(nameListSlideshow.getImageIndex()).getProperties().get(tradeInSlideshow.getImageIndex()).isTrading())
						tradeButtonTwo.setText("Trading");
					else
						tradeButtonTwo.setText("Trade");

					if(playerList.get(playerIndex).getProperties().size()==0)
						tradeButtonOne.setText("Trade");
					else if (playerList.get(playerIndex).getProperties().get(tradeOutSlideshow.getImageIndex()).isTrading())
						tradeButtonOne.setText("Trading");
					else
						tradeButtonOne.setText("Trade");

					gameplay.setActivity(false);
					assets.setActivity(false);
					trade.setActivity(true);
					board.setActivity(false);
					history.setActivity(false);
				} else
					trade.setActivity(false);
			}
		});
		board = new Switch(1371, 10, 114, 60, Assets.buttonPalette, Assets.kabel24, "Board", false, new Clickable() {
			@Override
			public void onClick() {
				tab = 4;
				gameplay.setActivity(false);
				assets.setActivity(false);
				trade.setActivity(false);
				board.setActivity(true);
				history.setActivity(false);
			}
		});
		history = new Switch(1495, 10, 113, 60, Assets.buttonPalette, Assets.kabel24, "History", false,
				new Clickable() {
			@Override
			public void onClick() {
				tab = 5;
				gameplay.setActivity(false);
				assets.setActivity(false);
				trade.setActivity(false);
				board.setActivity(false);
				history.setActivity(true);
			}
		});

		gameplayManager.addObject(
				new Button(1259, 800, 100, 60, Assets.buttonPalette, Assets.kabel48, "Roll", false, new Clickable() {
					@Override
					public void onClick() {
						if (botActive)
							return;
						playerRolled = true;
						if (playerList.get(playerIndex).getRollsLeft() > 0) {
							diceOne.roll();
							diceTwo.roll();
							playerList.get(playerIndex).decrementRollsLeft(1);
							if (playerList.get(playerIndex).getPosition() != -1) {
								if(firstRoll) {
									playerList.get(playerIndex).incrementPosition(7);
									firstRoll=false;
								}else 
									playerList.get(playerIndex)
									.incrementPosition(diceOne.getCurrent() + diceTwo.getCurrent());
									GameLogs.addMessage("Player " + playerList.get(playerIndex).getPlayerNumber()
								
								+ " advanced " + (diceOne.getCurrent() + diceTwo.getCurrent()) + " spaces ");
							}

							if (diceOne.getCurrent() == diceTwo.getCurrent()) {
								repeatRolls++;
								playerList.get(playerIndex).incrementRollsLeft(1);
								if (playerList.get(playerIndex).getPosition() == -1)
									playerList.get(playerIndex).setPosition(10);
							} else {
								repeatRolls = 0;
							}

							if (playerList.get(playerIndex).getPosition() == 30 || repeatRolls == 3) {
								playerList.get(playerIndex).setPosition(-1);
								GameLogs.addMessage(
										"Player " + playerList.get(playerIndex).getPlayerNumber() + " went to jail");
							}

							if (playerList.get(playerIndex).getPosition() == 4) {
								playerList.get(playerIndex).subtractMoney(200);
								GameLogs.addMessage(
										"Player " + playerList.get(playerIndex).getPlayerNumber() + " payed taxes");
							} else if (playerList.get(playerIndex).getPosition() == 38) {
								playerList.get(playerIndex).subtractMoney(100);
								GameLogs.addMessage(
										"Player " + playerList.get(playerIndex).getPlayerNumber() + " payed taxes");
							}

							if (playerList.get(playerIndex).getPosition() == 2
									|| playerList.get(playerIndex).getPosition() == 17
									|| playerList.get(playerIndex).getPosition() == 33) {
								currentCard = Assets.communityChestDeck.drawFromDeck();
								GameLogs.addMessage("Player " + playerList.get(playerIndex).getPlayerNumber()
										+ " drew a community chest card");
								int id = currentCard.getId();
								runningAnim = true;
								currentCard.flipAnim();
								runningAnim = false;
								if (id == 1) {
									playerList.get(playerIndex).addMoney(200);
								} else if (id == 2) {
									playerList.get(playerIndex).subtractMoney(50);
								} else if (id == 3) {
									playerList.get(playerIndex).addMoney(200);
									playerList.get(playerIndex).setPosition(0);
								} else if (id == 4) {
									playerList.get(playerIndex).addMoney(50);
								} else if (id == 5) {
									playerList.get(playerIndex).addJailbreakCards(1);
								} else if (id == 6) {
									playerList.get(playerIndex).setPosition(-1);
								} else if (id == 7) {
									for (int i = 0; i < playerList.size(); i++) {
										if (i != playerIndex) {
											playerList.get(i).subtractMoney(50);
											playerList.get(playerIndex).addMoney(50);
										}
									}
								} else if (id == 8) {
									playerList.get(playerIndex).addMoney(100);
								} else if (id == 9) {
									playerList.get(playerIndex).addMoney(20);
								} else if (id == 10) {
									playerList.get(playerIndex).addMoney(10);
								} else if (id == 11) {
									playerList.get(playerIndex).addMoney(100);
								} else if (id == 12) {
									playerList.get(playerIndex).subtractMoney(100);
								} else if (id == 13) {
									playerList.get(playerIndex).subtractMoney(100);
								} else if (id == 14) {
									playerList.get(playerIndex).addMoney(25);
								} else if (id == 15) {
									int houses = 0;
									int hotels = 0;
									ArrayList<PropertyCard> properties = playerList.get(playerIndex).getProperties();
									for (int i = 0; i < properties.size(); i++) {
										if (properties.get(i) instanceof DeedCard) {
											if (properties.get(i).getHouses() == 5)
												hotels++;
											else
												houses += properties.get(i).getHouses();
										}
									}
									playerList.get(playerIndex).subtractMoney(houses * 40);
									playerList.get(playerIndex).subtractMoney(hotels * 115);
								} else if (id == 16) {
									playerList.get(playerIndex).addMoney(100);
								} else if (id == 17) {
									if (playerList.get(playerIndex).getPosition() > 20
											&& playerList.get(playerIndex).getPosition() < 40) {
										playerList.get(playerIndex).addMoney(200);
									}
									playerList.get(playerIndex).setPosition(20);
								}

							}

							if (playerList.get(playerIndex).getPosition() == 7
									|| playerList.get(playerIndex).getPosition() == 22
									|| playerList.get(playerIndex).getPosition() == 36) {
								currentCard = Assets.chanceDeck.drawFromDeck();
								GameLogs.addMessage("Player " + playerList.get(playerIndex).getPlayerNumber()
										+ " drew a chance card");
								int id = currentCard.getId();
								runningAnim = true;
								currentCard.flipAnim();
								runningAnim = false;
								if (id == 1) {
									playerList.get(playerIndex).setPosition(40);
									playerList.get(playerIndex).incrementPosition(0);
								} else if (id == 2) {
									if (playerList.get(playerIndex).getPosition() > 24)
										playerList.get(playerIndex).addMoney(200);
									playerList.get(playerIndex).setPosition(24);
								} else if (id == 3) {
									if (playerList.get(playerIndex).getPosition() > 11)
										playerList.get(playerIndex).addMoney(200);
									playerList.get(playerIndex).setPosition(11);
								} else if (id == 4) {
									playerList.get(playerIndex).addMoney(50);
								} else if (id == 5) {
									playerList.get(playerIndex).addJailbreakCards(1);
								}  else if (id == 7) {
									playerList.get(playerIndex).setPosition(-1);
								} else if (id == 8) {
									int houses = 0;
									int hotels = 0;
									ArrayList<PropertyCard> properties = playerList.get(playerIndex).getProperties();
									for (int i = 0; i < properties.size(); i++) {
										if (properties.get(i) instanceof DeedCard) {
											if (properties.get(i).getHouses() == 5)
												hotels++;
											else
												houses += properties.get(i).getHouses();
										}
									}
									playerList.get(playerIndex).subtractMoney(houses * 25);
									playerList.get(playerIndex).subtractMoney(hotels * 100);
								} else if (id == 9) {
									playerList.get(playerIndex).subtractMoney(15);
								} else if (id == 10) {
									if (playerList.get(playerIndex).getPosition() > 5)
										playerList.get(playerIndex).addMoney(200);
									playerList.get(playerIndex).setPosition(5);
								} else if (id == 11) {
									playerList.get(playerIndex).setPosition(39);
								} else if (id == 12) {
									for (int i = 0; i < playerList.size(); i++) {
										if (i != playerIndex) {
											playerList.get(playerIndex).subtractMoney(50);
											playerList.get(i).addMoney(50);
										}
									}
								} else if (id == 13) {
									playerList.get(playerIndex).addMoney(150);
								} else if (id == 14) {
									playerList.get(playerIndex).addMoney(100);
								} else if (id == 15) {
									playerList.get(playerIndex).addMoney(25);
								}

							}
						}
						int cardIndex = playerList.get(playerIndex).positionToIndex();
						if (cardIndex == -1 || !Assets.propertyDeck.cardInUse(cardIndex)
								|| (Assets.propertyDeck.getCard(cardIndex).getOwner() == playerList.get(playerIndex))
								|| Assets.propertyDeck.getCard(cardIndex).isMortgaged()
								|| Assets.propertyDeck.getCard(cardIndex).isMortgaged()
								|| Assets.propertyDeck.getCard(cardIndex).getOwner().getPosition() == -1)
							payedRent = true;
						else
							payedRent = false;
					}
				}));

		gameplayManager.addObject(new Button(1209, 270, 200, 60, Assets.buttonPalette, Assets.kabel48, "Pay Rent",
				false, new Clickable() {
			@Override
			public void onClick() {
				if (botActive)
					return;
				if (playerList.get(playerIndex).getPosition() == -1)
					return;
				if (!payedRent) {
					// Check for Mortgaged Cards
					int cardIndex = playerList.get(playerIndex).positionToIndex();
					int cardGroup = Assets.propertyDeck.getCard(cardIndex).getGroup();
					int rent = 0;
					if (cardIndex != -1 && Assets.propertyDeck.cardInUse(cardIndex)
							&& !Assets.propertyDeck.getCard(cardIndex).isMortgaged()) {
						if (Assets.propertyDeck.getCard(cardIndex) instanceof DeedCard) {
							rent = Assets.propertyDeck.getCard(cardIndex).getRentCost();

							if (Assets.propertyDeck.getCard(cardIndex).getOwner()
									.getGroups(cardGroup - 1) == Assets.propertyDeck.getCard(cardIndex)
									.getOwner().getGroupCapacity(cardGroup - 1))
								rent *= 2;

							Assets.propertyDeck.getCard(cardIndex).getOwner().addMoney(rent);
							playerList.get(playerIndex).subtractMoney(rent);

							if(playerList.get(playerIndex).getMoney()<0) {
								playerList.get(playerIndex).setInactive(true);
								playerList.get(playerIndex).eliminate(playerList.get(playerIndex), Assets.propertyDeck.getCard(cardIndex).getOwner(), playerList.get(playerIndex).getProperties());
								if(playerList.get(playerIndex).getPropertyImages().length!=0) {
									playerPropertySlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
									tradeOutSlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
								}else {
									playerPropertySlideshow.setImages(null);
									tradeOutSlideshow.setImages(null);
								}
								GameLogs.addMessage(
										"Player " + Assets.propertyDeck.getCard(cardIndex).getOwner() + " bankrupted Player " + playerList.get(playerIndex).getPlayerNumber());
							}

							payedRent = true;
						} else if (Assets.propertyDeck.getCard(cardIndex) instanceof UtilityCard) {
							if (cardGroup == 9) {

								rent = 25 * Assets.propertyDeck.getCard(cardIndex).getOwner()
										.getGroups(cardGroup - 1);

								Assets.propertyDeck.getCard(cardIndex).getOwner().addMoney(rent);
								playerList.get(playerIndex).subtractMoney(rent);
								GameLogs.addMessage("Player " + playerList.get(playerIndex).getPlayerNumber()
										+ " payed " + rent + " to "
										+ Assets.propertyDeck.getCard(cardIndex).getOwner().getPlayerNumber());
								payedRent = true;
							} else if (cardGroup == 10) {
								if (Assets.propertyDeck.getCard(cardIndex).getOwner()
										.getGroups(cardGroup - 1) == Assets.propertyDeck.getCard(cardIndex)
										.getOwner().getGroupCapacity(cardGroup - 1))
									rent = (diceOne.getCurrent() + diceTwo.getCurrent()) * 10;
								else
									rent = (diceOne.getCurrent() + diceTwo.getCurrent()) * 4;

								Assets.propertyDeck.getCard(cardIndex).getOwner().addMoney(rent);
								playerList.get(playerIndex).subtractMoney(rent);
								GameLogs.addMessage("Player " + playerList.get(playerIndex).getPlayerNumber()
										+ " payed " + rent + " to "
										+ Assets.propertyDeck.getCard(cardIndex).getOwner().getPlayerNumber());
								payedRent = true;
							}
							if(playerList.get(playerIndex).getMoney()<0) {
								playerList.get(playerIndex).setInactive(true);
								playerList.get(playerIndex).eliminate(playerList.get(playerIndex), Assets.propertyDeck.getCard(cardIndex).getOwner(), playerList.get(playerIndex).getProperties());
								if(playerList.get(playerIndex).getPropertyImages().length!=0) {
									playerPropertySlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
									tradeOutSlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
								}else {
									playerPropertySlideshow.setImages(null);
									tradeOutSlideshow.setImages(null);
								}
								GameLogs.addMessage(
										"Player " + Assets.propertyDeck.getCard(cardIndex).getOwner() + " bankrupted Player " + playerList.get(playerIndex).getPlayerNumber());
							}
						}
					}
				}
			}
		}));

		gameplayManager.addObject(new Button(1169, 350, 280, 60, Assets.buttonPalette, Assets.kabel48, "Buy Property",
				false, new Clickable() {
			@Override
			public void onClick() {
				if (botActive)
					return;
				if (playerList.get(playerIndex).getPosition() == -1)
					return;
				if (playerRolled) {
					int cardIndex = playerList.get(playerIndex).positionToIndex();
					if (cardIndex == -1)
						return;
					if (!Assets.propertyDeck.cardInUse(cardIndex)) {
						playerList.get(playerIndex)
						.subtractMoney(Assets.propertyDeck.getCard(cardIndex).getPrice());
						playerList.get(playerIndex).addProperty(Assets.propertyDeck.getCard(cardIndex));
						Assets.propertyDeck.getCard(cardIndex).setOwnership(playerList.get(playerIndex));
						Assets.propertyDeck.setCardInUse(cardIndex);
						GameLogs.addMessage("Player " + playerList.get(playerIndex).getPlayerNumber()
								+ " purchased " + Assets.propertyDeck.getCard(cardIndex).getName());
						playerPropertySlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
						tradeOutSlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
					}
				}
			}
		}));

		gameplayManager.addObject(new Button(1000, 910, 608, 80, Assets.nextButtons, new Clickable() {
			@Override
			public void onClick() {
				if (botActive)
					return;
				int cardIndex = playerList.get(playerIndex).positionToIndex();
				if (cardIndex == -1 || !Assets.propertyDeck.cardInUse(cardIndex)
						|| (Assets.propertyDeck.getCard(cardIndex).getOwner() == playerList.get(playerIndex))
						|| Assets.propertyDeck.getCard(cardIndex).isMortgaged()
						|| Assets.propertyDeck.getCard(cardIndex).getOwner().getPosition() == -1)
					payedRent = true;
				if (payedRent) {
					repeatRolls = 0;
					playerRolled = false;
					payedRent = false;
					tradeOutList.clear();
					tradeInList.clear();
					tradeOneConfirmed = false;
					tradeTwoConfirmed = false;
					confirmButtonOne.setText("Confirm");
					confirmButtonTwo.setText("Confirm");
					GameLogs.addMessage(
							"Player " + playerList.get(playerIndex).getPlayerNumber() + " ended their turn");
					if (playerList.get(playerIndex).getRollsLeft() == 0) {
						playerIndex++;
						if (playerIndex > playerList.size() - 1) {
							playerIndex = 0;
						}
						if (playerList.get(playerIndex).getProperties().size() == 0) {
							playerPropertySlideshow.setImages(null);
							tradeOutSlideshow.setImages(null);
						} else {
							playerPropertySlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
							tradeOutSlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
						}
						playerList.get(playerIndex).incrementRollsLeft(1);

						if(playerIndex != 0 && playerList.get(playerIndex-1).getMoney()<0) {
							playerList.get(playerIndex-1).setInactive(true);
							GameLogs.addMessage(
									"Player " + playerList.get(playerIndex-1).getPlayerNumber() + " went bankrupt because of their own incompetence");
							int i = (int) (Math.random()*playerList.size());

							while(i == playerIndex-1) {
								i = (int) (Math.random()*playerList.size());
							}

							playerList.get(playerIndex-1).eliminate(playerList.get(playerIndex-1), playerList.get(i), playerList.get(playerIndex-1).getProperties());
							if(playerList.get(playerIndex).getPropertyImages().length!=0) {
								playerPropertySlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
								tradeOutSlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
							}else {
								playerPropertySlideshow.setImages(null);
								tradeOutSlideshow.setImages(null);
							}
							GameLogs.addMessage(
									"Player " + playerList.get(i).getPlayerNumber() + " inhereted Player " + playerList.get(playerIndex-1).getPlayerNumber() + "'s assets");

						}else if(playerIndex == 0 && playerList.get(playerList.size()-1).getMoney()<0){
							playerList.get(playerList.size()-1).setInactive(true);

							int i = (int) (Math.random()*playerList.size());
							while(i == playerList.size()-1) {
								i = (int) (Math.random()*playerList.size());
							}

							playerList.get(playerList.size()-1).eliminate(playerList.get(playerList.size()-1), playerList.get(i), playerList.get(playerList.size()-1).getProperties());
							if(playerList.get(playerIndex).getPropertyImages().length!=0) {
								playerPropertySlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
								tradeOutSlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
							}else {
								playerPropertySlideshow.setImages(null);
								tradeOutSlideshow.setImages(null);
							}
							GameLogs.addMessage(
									"Player " + playerList.get(i).getPlayerNumber() + " inhereted Player " + playerList.get(playerList.size()-1).getPlayerNumber() + "'s assets");
						}
					}
					for (int i = 0; i < playerList.size(); i++) {
						playerList.get(i).clearTrades();
					}
				}
			}
		}));

		mortgageButton = new Button(1169, 680, 280, 60, Assets.buttonPalette, Assets.kabel48, "Mortgage", false,
				new Clickable() {
			@Override
			public void onClick() {
				if (playerList.get(playerIndex).getPosition() == -1)
					return;
				boolean isMortgaged = playerList.get(playerIndex).getProperties()
						.get(playerPropertySlideshow.getImageIndex()).isMortgaged();

				if (isMortgaged && playerList.get(playerIndex).getProperties()
						.get(playerPropertySlideshow.getImageIndex()).getHouses() == 0) {// Unmortgaging
					playerList.get(playerIndex).getProperties().get(playerPropertySlideshow.getImageIndex())
					.setMortgaged(!isMortgaged);
					playerList.get(playerIndex).subtractMoney(playerList.get(playerIndex).getProperties()
							.get(playerPropertySlideshow.getImageIndex()).getMortgageVal());
					mortgageButton.setText("Mortgage");
					GameLogs.addMessage("Player " + playerList.get(playerIndex).getPlayerNumber()
							+ " unmortgaged " + playerList.get(playerIndex).getProperties()
							.get(playerPropertySlideshow.getImageIndex()).getName());
				} else if (playerList.get(playerIndex).getProperties()
						.get(playerPropertySlideshow.getImageIndex()).getHouses() == 0) {// Mortgaging
					playerList.get(playerIndex).getProperties().get(playerPropertySlideshow.getImageIndex())
					.setMortgaged(!isMortgaged);
					playerList.get(playerIndex).addMoney(playerList.get(playerIndex).getProperties()
							.get(playerPropertySlideshow.getImageIndex()).getMortgageVal());
					mortgageButton.setText("Unmortgage");
					GameLogs.addMessage("Player " + playerList.get(playerIndex).getPlayerNumber()
							+ " mortgaged " + playerList.get(playerIndex).getProperties()
							.get(playerPropertySlideshow.getImageIndex()).getName());
				}
			}
		});

		jailbreakButton = new Button(1059, 920, 500, 60, Assets.buttonPalette, Assets.kabel48,
				"Get Out of Jail (Money)", false, new Clickable() {
			@Override
			public void onClick() {
				if (playerList.get(playerIndex).getPosition() == -1) {
					GameLogs.addMessage(
							"Player " + playerList.get(playerIndex).getPlayerNumber() + " got out of jail");
					if (playerList.get(playerIndex).getJailbreakCards() >= 1) {
						playerList.get(playerIndex).useJailbreakCards();
						playerList.get(playerIndex).setPosition(10);
					} else {
						playerList.get(playerIndex).subtractMoney(50);
						playerList.get(playerIndex).setPosition(10);
					}
				}
			}
		});

		assetsManager.addObject(new Button(1184, 760, 250, 60, Assets.buttonPalette, Assets.kabel48, "Buy House", false,
				new Clickable() {
			@Override
			public void onClick() {
				if (playerList.get(playerIndex).getPosition() == -1)
					return;
				if (!playerList.get(playerIndex).getProperties().get(playerPropertySlideshow.getImageIndex())
						.isMortgaged()
						&& playerList.get(playerIndex).getProperties()
						.get(playerPropertySlideshow.getImageIndex()) instanceof DeedCard) {
					PropertyCard thisCard = playerList.get(playerIndex).getProperties()
							.get(playerPropertySlideshow.getImageIndex());

					thisCard.addHouses(1);
					playerList.get(playerIndex).subtractMoney(thisCard.getHouseCost());

					int cardGroup = thisCard.getGroup();

					if (playerList.get(playerIndex).getGroups(cardGroup - 1) == playerList.get(playerIndex)
							.getGroupCapacity(cardGroup - 1) && thisCard.getHouses() <= 5) {
						PropertyCard[] cardsInGroup = playerList.get(playerIndex).getCardbyGroup(cardGroup);
						int absoluteHouseDif = 0;
						for (int i = 0; i < cardsInGroup.length - 1; i++) {
							int dif = Math.abs(cardsInGroup[i].getHouses() - cardsInGroup[i + 1].getHouses());
							if (dif > absoluteHouseDif)
								absoluteHouseDif = dif;
						}
						if (absoluteHouseDif > 1) {
							thisCard.removeHouses(1);
							playerList.get(playerIndex).addMoney(thisCard.getHouseCost());
						} else {
							GameLogs.addMessage("Player " + playerList.get(playerIndex).getPlayerNumber()
									+ " bought a house on " + thisCard.getName());
						}

					} else {
						thisCard.removeHouses(1);
						playerList.get(playerIndex).addMoney(thisCard.getHouseCost());
					}

				}
			}
		}));

		assetsManager.addObject(new Button(1184, 840, 250, 60, Assets.buttonPalette, Assets.kabel48, "Sell House",
				false, new Clickable() {
			@Override
			public void onClick() {
				if (playerList.get(playerIndex).getPosition() == -1)
					return;
				if (!playerList.get(playerIndex).getProperties().get(playerPropertySlideshow.getImageIndex())
						.isMortgaged()
						&& playerList.get(playerIndex).getProperties()
						.get(playerPropertySlideshow.getImageIndex()) instanceof DeedCard) {
					PropertyCard thisCard = playerList.get(playerIndex).getProperties()
							.get(playerPropertySlideshow.getImageIndex());

					thisCard.removeHouses(1);
					playerList.get(playerIndex).addMoney(thisCard.getHouseCost() / 2);

					int cardGroup = thisCard.getGroup();

					if (playerList.get(playerIndex).getGroups(cardGroup - 1) == playerList.get(playerIndex)
							.getGroupCapacity(cardGroup - 1) && thisCard.getHouses() >= 0) {
						PropertyCard[] cardsInGroup = playerList.get(playerIndex).getCardbyGroup(cardGroup);
						int absoluteHouseDif = 0;
						for (int i = 0; i < cardsInGroup.length - 1; i++) {
							int dif = Math.abs(cardsInGroup[i].getHouses() - cardsInGroup[i + 1].getHouses());
							if (dif > absoluteHouseDif)
								absoluteHouseDif = dif;
						}
						if (absoluteHouseDif > 1) {
							thisCard.addHouses(1);
							playerList.get(playerIndex).subtractMoney(thisCard.getHouseCost() / 2);
						} else {
							GameLogs.addMessage("Player " + playerList.get(playerIndex).getPlayerNumber()
									+ " sold a house on " + thisCard.getName());
						}

					} else {
						thisCard.addHouses(1);
						playerList.get(playerIndex).subtractMoney(thisCard.getHouseCost() / 2);
					}
				}
			}
		}));

		gameplayManager.addObject(gameplay);
		gameplayManager.addObject(assets);
		gameplayManager.addObject(trade);
		gameplayManager.addObject(board);
		gameplayManager.addObject(history);

		assetsManager.addObject(gameplay);
		assetsManager.addObject(assets);
		assetsManager.addObject(trade);
		assetsManager.addObject(board);
		assetsManager.addObject(history);
		assetsManager.addObject(mortgageButton);
		assetsManager.addObject(jailbreakButton);
		assetsManager.addObject(playerPropertySlideshow);

		tradeManager.addObject(gameplay);
		tradeManager.addObject(assets);
		tradeManager.addObject(trade);
		tradeManager.addObject(board);
		tradeManager.addObject(history);
		tradeManager.addObject(tradeButtonOne);
		tradeManager.addObject(tradeButtonTwo);
		tradeManager.addObject(confirmButtonOne);
		tradeManager.addObject(confirmButtonTwo);
		tradeManager.addObject(nameListSlideshow);
		tradeManager.addObject(tradeInSlideshow);
		tradeManager.addObject(tradeOutSlideshow);

		boardManager.addObject(gameplay);
		boardManager.addObject(assets);
		boardManager.addObject(trade);
		boardManager.addObject(board);
		boardManager.addObject(history);
		boardManager.addObject(allPropertySlideshow);

		historyManager.addObject(gameplay);
		historyManager.addObject(assets);
		historyManager.addObject(trade);
		historyManager.addObject(board);
		historyManager.addObject(history);
	}

	public void update() {
		if (initRun) {
			for (int i = 0; i < handler.getGame().getNumPlayers(); i++) {
				playerList.add(new Player(0, 0, 32, 32, Assets.gamePieces[handler.getGame().getPlayerIcon()[i]], 1500));
			}
			for (int i = 0; i < handler.getGame().getNumBots(); i++) {
				playerList.add(new Bot(0, 0, 32, 32, Assets.gamePieces[handler.getGame().getBotIcon()[i]], 1500));
			}
			playerList.get(0).incrementRollsLeft(1);
			nameListSlideshow.setImages(Arrays.copyOfRange(Assets.nameList, 0, handler.getGame().getNumPlayers()));
			tab = 3;
			tab = 4;
			tab = 1;
			initRun = false;
		} else {
			if (tab == 1) {
				handler.getMouseManager().setUIManager(gameplayManager);
				gameplayManager.update();
			} else if (tab == 2) {
				handler.getMouseManager().setUIManager(assetsManager);
				assetsManager.update();
				if (playerList.get(playerIndex).getJailbreakCards() > 0)
					jailbreakButton.setText("Get Out of Jail (Card)");
				else
					jailbreakButton.setText("Get Out of Jail (Money)");
			} else if (tab == 3) {
				handler.getMouseManager().setUIManager(tradeManager);
				if (tradeOneConfirmed && tradeTwoConfirmed && tradeOutList.size() > 0 && tradeInList.size() > 0) {
					playerList.get(playerIndex).trade(playerList.get(playerIndex),
							playerList.get(nameListSlideshow.getImageIndex()), tradeOutList, tradeInList);
					playerPropertySlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
					tradeOutSlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
					tradeInSlideshow.setImages(playerList.get(nameListSlideshow.getImageIndex()).getPropertyImages());
					confirmButtonOne.setText("Confirm");
					confirmButtonTwo.setText("Confirm");
					GameLogs.addMessage(
							"Player " + playerList.get(playerIndex).getPlayerNumber() + " traded with Player "
									+ playerList.get(nameListSlideshow.getImageIndex()).getPlayerNumber());
					tradeOutList.clear();
					tradeInList.clear();
					tradeOneConfirmed = false;
					tradeTwoConfirmed = false;
					assets.onClick();
				}
				tradeManager.update();
			} else if (tab == 4) {
				handler.getMouseManager().setUIManager(boardManager);
				boardManager.update();
			} else if (tab == 5) {
				handler.getMouseManager().setUIManager(historyManager);
				historyManager.update();
			}
		}

		if (playerList.get(playerIndex).isInactive()) {
			skipTurn();
		}

		for (int i = 0; i < playerList.size() - 1; i++) {
			playerList.get(i).update();
			if (i == 0)
				playerList.get(i).setPlaceModifier(0);
			if (playerList.get(i).getPosition() == playerList.get(i + 1).getPosition()) {
				playerList.get(i + 1).setPlaceModifier(playerList.get(i).getPlaceModifier() + 2);
			}
		}

		if (playerList.get(playerIndex) instanceof Bot)
			botActive = true;
		else
			botActive = false;

		if (botActive) {
			if (playerList.get(playerIndex).getRollsLeft() > 0) {
				rollEvent();
				payRentEvent();
				int roll = (int) (Math.random() * 4) + 1;
				if (roll == 4)
					buyPropertyEvent();
				nextEvent();
			}
		}

		activePlayers = 0;
		for (Player p : playerList) {
			if (!p.isInactive())
				activePlayers++;
		}

		if (activePlayers == 1) {
			Player winner = null;
			for (Player p : playerList) {
				if (!p.isInactive())
					winner = p;
			}
			JOptionPane.showMessageDialog(handler.getGame().getDisplay().getFrame(),
					"Player " + winner.getPlayerNumber() + " Wins");
			System.exit(0);
		}

	}

	public void render(Graphics g) {
		if (!initRun) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1000, 1000);
			g.fillRect(1000, 0, 618, 1000);

			g.setColor(new Color(153, 204, 255));
			g.fillRect(1000, 10, 608, 980);

			g.setColor(Color.BLACK);
			g.fillRect(1000, 0, 618, 80);

			g.drawImage(Assets.gameBoard, 10, 10, 980, 980, null);

			if (tab == 1) {
				g.setColor(Color.black);
				g.fillRect(1254, 795, 110, 70);

				g.fillRect(1204, 265, 210, 70);

				g.fillRect(1164, 345, 290, 70);

				if (playerIndex < handler.getGame().getNumPlayers()) {
					Utils.drawString(g, "Player " + (playerIndex + 1), 1304, 110, true, Color.black, Assets.kabel48);
					g.drawImage(Assets.gamePieces[handler.getGame().getPlayerIcon()[playerIndex]], 1249, 140, 120, 120,
							null);
				} else if (playerIndex < handler.getGame().getNumPlayers() + handler.getGame().getNumBots()) {
					Utils.drawString(g, "Bot " + (playerIndex + 1), 1304, 110, true, Color.black, Assets.kabel48);
					g.drawImage(Assets.gamePieces[handler.getGame().getBotIcon()[playerIndex
					                                                             - handler.getGame().getNumPlayers()]], 1249, 140, 120, 120, null);
				}

				diceOne.render(g);
				diceTwo.render(g);
				Utils.drawString(g, "Total: " + (diceOne.getCurrent() + diceTwo.getCurrent()), 1304, 670, true,
						Color.black, Assets.kabel48);
				Utils.drawString(g, "Rolls Left: " + (playerList.get(playerIndex).getRollsLeft()), 1304, 750, true,
						Color.black, Assets.kabel48);

				gameplayManager.render(g);
			} else if (tab == 2) {
				g.setColor(Color.BLACK);
				g.fillRect(1164, 675, 290, 70);
				g.fillRect(1179, 755, 260, 70);
				g.fillRect(1179, 835, 260, 70);
				g.fillRect(1054, 915, 510, 70);

				assetsManager.render(g);

				jailbreakButton.render(g);

				Utils.drawString(g, "Money: $" + playerList.get(playerIndex).getMoney(), 1304, 110, true, Color.black,
						Assets.kabel48);
				Utils.drawString(g, "Properties", 1304, 210, true, Color.black, Assets.kabel48);
				if (playerList.get(playerIndex).getProperties().size() != 0) {
					if (playerList.get(playerIndex).getProperties().get(playerPropertySlideshow.getImageIndex())
							.isMortgaged())
						Utils.drawString(g, "Mortgaged", 1304, 610, true, Color.black, Assets.kabel24);
					else
						Utils.drawString(g, "Unmortgaged", 1304, 610, true, Color.black, Assets.kabel24);
					Utils.drawString(g,
							"Houses: " + playerList.get(playerIndex).getProperties()
							.get(playerPropertySlideshow.getImageIndex()).getHouses(),
							1304, 640, true, Color.black, Assets.kabel24);
				}
			} else if (tab == 3) {
				g.setColor(Color.black);
				g.fillRect(1024, 495, 260, 70);
				g.fillRect(1024, 675, 260, 70);
				g.fillRect(1334, 495, 260, 70);
				g.fillRect(1334, 675, 260, 70);

				tradeManager.render(g);
				Utils.drawString(g, "Player " + playerList.get(playerIndex).getPlayerNumber(), 1154, 130, true,
						Color.black, Assets.kabel46);
			} else if (tab == 4) {
				boardManager.render(g);

				Utils.drawString(g, "Board Info", 1304, 120, true, Color.black, Assets.kabel48);
				Utils.drawString(g,
						"Price: $" + Assets.propertyDeck.getCard(allPropertySlideshow.getImageIndex()).getPrice(), 1304,
						530, true, Color.black, Assets.kabel24);

				if (Assets.propertyDeck.getCard(allPropertySlideshow.getImageIndex()).getOwner() == null)
					Utils.drawString(g, "Owner: None", 1304, 560, true, Color.black, Assets.kabel24);
				else if (Assets.propertyDeck.getCard(allPropertySlideshow.getImageIndex()).getOwner() instanceof Bot)
					Utils.drawString(g, "Owner: Bot " + Assets.propertyDeck
							.getCard(allPropertySlideshow.getImageIndex()).getOwner().getPlayerNumber(), 1304, 560,
							true, Color.black, Assets.kabel24);
				else
					Utils.drawString(g, "Owner: Player " + Assets.propertyDeck
							.getCard(allPropertySlideshow.getImageIndex()).getOwner().getPlayerNumber(), 1304, 560,
							true, Color.black, Assets.kabel24);

				if (Assets.propertyDeck.getCard(allPropertySlideshow.getImageIndex()).isMortgaged())
					Utils.drawString(g, "Mortgaged", 1304, 590, true, Color.black, Assets.kabel24);
				else
					Utils.drawString(g, "Unmortgaged", 1304, 590, true, Color.black, Assets.kabel24);

				Utils.drawString(g,
						"Houses: " + Assets.propertyDeck.getCard(allPropertySlideshow.getImageIndex()).getHouses(),
						1304, 620, true, Color.black, Assets.kabel24);
			} else if (tab == 5) {
				historyManager.render(g);
				g.setColor(Color.black);
				g.fillRect(1049, 130, 510, 810);
				g.setColor(Color.white);
				g.fillRect(1054, 135, 500, 800);
				GameLogs.render(g, 1060, 150);
			}

			for (Player p : playerList) {
				if (!p.isInactive())
					p.render(g);
			}

			uiManager.render(g);

			if (runningAnim)
				currentCard.render(g);
		}
	}

	public void rollEvent() {
		playerRolled = true;
		if (playerList.get(playerIndex).getRollsLeft() > 0) {
			diceOne.roll();
			diceTwo.roll();
			playerList.get(playerIndex).decrementRollsLeft(1);
			if (playerList.get(playerIndex).getPosition() != -1) {
				// playerList.get(playerIndex).incrementPosition(30);
				playerList.get(playerIndex).incrementPosition(diceOne.getCurrent() + diceTwo.getCurrent());
				GameLogs.addMessage("Bot " + playerList.get(playerIndex).getPlayerNumber() + " advanced "
						+ (diceOne.getCurrent() + diceTwo.getCurrent()) + " spaces ");
			}

			if (diceOne.getCurrent() == diceTwo.getCurrent()) {
				repeatRolls++;
				playerList.get(playerIndex).incrementRollsLeft(1);
				if (playerList.get(playerIndex).getPosition() == -1)
					playerList.get(playerIndex).setPosition(10);
			} else {
				repeatRolls = 0;
			}

			if (playerList.get(playerIndex).getPosition() == 30 || repeatRolls == 3) {
				playerList.get(playerIndex).setPosition(-1);
				GameLogs.addMessage("Bot " + playerList.get(playerIndex).getPlayerNumber() + " went to jail");
			}

			if (playerList.get(playerIndex).getPosition() == 4) {
				playerList.get(playerIndex).subtractMoney(200);
				GameLogs.addMessage("Bot " + playerList.get(playerIndex).getPlayerNumber() + " payed taxes");
			} else if (playerList.get(playerIndex).getPosition() == 38) {
				playerList.get(playerIndex).subtractMoney(100);
				GameLogs.addMessage("Bot " + playerList.get(playerIndex).getPlayerNumber() + " payed taxes");
			}

			if (playerList.get(playerIndex).getPosition() == 2 || playerList.get(playerIndex).getPosition() == 17
					|| playerList.get(playerIndex).getPosition() == 33) {
				currentCard = Assets.communityChestDeck.drawFromDeck();
				GameLogs.addMessage(
						"Bot " + playerList.get(playerIndex).getPlayerNumber() + " drew a community chest card");
				int id = currentCard.getId();
				runningAnim = true;
				currentCard.flipAnim();
				runningAnim = false;
				if (id == 1) {
					playerList.get(playerIndex).addMoney(200);
				} else if (id == 2) {
					playerList.get(playerIndex).subtractMoney(50);
				} else if (id == 3) {
					playerList.get(playerIndex).addMoney(200);
					playerList.get(playerIndex).setPosition(0);
				} else if (id == 4) {
					playerList.get(playerIndex).addMoney(50);
				} else if (id == 5) {
					playerList.get(playerIndex).addJailbreakCards(1);
				} else if (id == 6) {
					playerList.get(playerIndex).setPosition(-1);
				} else if (id == 7) {
					for (int i = 0; i < playerList.size(); i++) {
						if (i != playerIndex) {
							playerList.get(i).subtractMoney(50);
							playerList.get(playerIndex).addMoney(50);
						}
					}
				} else if (id == 8) {
					playerList.get(playerIndex).addMoney(100);
				} else if (id == 9) {
					playerList.get(playerIndex).addMoney(20);
				} else if (id == 10) {
					playerList.get(playerIndex).addMoney(10);
				} else if (id == 11) {
					playerList.get(playerIndex).addMoney(100);
				} else if (id == 12) {
					playerList.get(playerIndex).subtractMoney(100);
				} else if (id == 13) {
					playerList.get(playerIndex).subtractMoney(100);
				} else if (id == 14) {
					playerList.get(playerIndex).addMoney(25);
				} else if (id == 15) {
					int houses = 0;
					int hotels = 0;
					ArrayList<PropertyCard> properties = playerList.get(playerIndex).getProperties();
					for (int i = 0; i < properties.size(); i++) {
						if (properties.get(i) instanceof DeedCard) {
							if (properties.get(i).getHouses() == 5)
								hotels++;
							else
								houses += properties.get(i).getHouses();
						}
					}
					playerList.get(playerIndex).subtractMoney(houses * 40);
					playerList.get(playerIndex).subtractMoney(hotels * 115);
				} else if (id == 16) {
					playerList.get(playerIndex).addMoney(100);
				} else if (id == 17) {
					if (playerList.get(playerIndex).getPosition() > 20
							&& playerList.get(playerIndex).getPosition() < 40) {
						playerList.get(playerIndex).addMoney(200);
					}
					playerList.get(playerIndex).setPosition(20);
				}

			}

			if (playerList.get(playerIndex).getPosition() == 7 || playerList.get(playerIndex).getPosition() == 22
					|| playerList.get(playerIndex).getPosition() == 36) {
				currentCard = Assets.chanceDeck.drawFromDeck();
				GameLogs.addMessage("Bot " + playerList.get(playerIndex).getPlayerNumber() + " drew a chance card");
				int id = currentCard.getId();
				runningAnim = true;
				currentCard.flipAnim();
				runningAnim = false;
				if (id == 1) {
					playerList.get(playerIndex).setPosition(40);
					playerList.get(playerIndex).incrementPosition(0);
				} else if (id == 2) {
					if (playerList.get(playerIndex).getPosition() > 24)
						playerList.get(playerIndex).addMoney(200);
					playerList.get(playerIndex).setPosition(24);
				} else if (id == 3) {
					if (playerList.get(playerIndex).getPosition() > 11)
						playerList.get(playerIndex).addMoney(200);
					playerList.get(playerIndex).setPosition(11);
				} else if (id == 4) {
					playerList.get(playerIndex).addMoney(50);
				} else if (id == 5) {
					playerList.get(playerIndex).addJailbreakCards(1);
				} else if (id == 6) {
					playerList.get(playerIndex).incrementPosition(-3);
				} else if (id == 7) {
					playerList.get(playerIndex).setPosition(-1);
				} else if (id == 8) {
					int houses = 0;
					int hotels = 0;
					ArrayList<PropertyCard> properties = playerList.get(playerIndex).getProperties();
					for (int i = 0; i < properties.size(); i++) {
						if (properties.get(i) instanceof DeedCard) {
							if (properties.get(i).getHouses() == 5)
								hotels++;
							else
								houses += properties.get(i).getHouses();
						}
					}
					playerList.get(playerIndex).subtractMoney(houses * 25);
					playerList.get(playerIndex).subtractMoney(hotels * 100);
				} else if (id == 9) {
					playerList.get(playerIndex).subtractMoney(15);
				} else if (id == 10) {
					if (playerList.get(playerIndex).getPosition() > 5)
						playerList.get(playerIndex).addMoney(200);
					playerList.get(playerIndex).setPosition(5);
				} else if (id == 11) {
					playerList.get(playerIndex).setPosition(39);
				} else if (id == 12) {
					for (int i = 0; i < playerList.size(); i++) {
						if (i != playerIndex) {
							playerList.get(playerIndex).subtractMoney(50);
							playerList.get(i).addMoney(50);
						}
					}
				} else if (id == 13) {
					playerList.get(playerIndex).addMoney(150);
				} else if (id == 14) {
					playerList.get(playerIndex).addMoney(100);
				} else if (id == 15) {
					playerList.get(playerIndex).addMoney(25);
				}

			}
		}
		int cardIndex = playerList.get(playerIndex).positionToIndex();
		if (cardIndex == -1 || !Assets.propertyDeck.cardInUse(cardIndex)
				|| (Assets.propertyDeck.getCard(cardIndex).getOwner() == playerList.get(playerIndex))
				|| Assets.propertyDeck.getCard(cardIndex).isMortgaged()
				|| Assets.propertyDeck.getCard(cardIndex).isMortgaged()
				|| Assets.propertyDeck.getCard(cardIndex).getOwner().getPosition() == -1)
			payedRent = true;
		else
			payedRent = false;
	}

	public void payRentEvent() {
		if (playerList.get(playerIndex).getPosition() == -1)
			return;
		if (!payedRent) {
			// Check for Mortgaged Cards
			int cardIndex = playerList.get(playerIndex).positionToIndex();
			int cardGroup = Assets.propertyDeck.getCard(cardIndex).getGroup();
			int rent = 0;
			if (cardIndex != -1 && Assets.propertyDeck.cardInUse(cardIndex)
					&& !Assets.propertyDeck.getCard(cardIndex).isMortgaged()) {
				if (Assets.propertyDeck.getCard(cardIndex) instanceof DeedCard) {
					rent = Assets.propertyDeck.getCard(cardIndex).getRentCost();

					if (Assets.propertyDeck.getCard(cardIndex).getOwner().getGroups(cardGroup
							- 1) == Assets.propertyDeck.getCard(cardIndex).getOwner().getGroupCapacity(cardGroup - 1))
						rent *= 2;

					Assets.propertyDeck.getCard(cardIndex).getOwner().addMoney(rent);
					playerList.get(playerIndex).subtractMoney(rent);
					payedRent = true;
				} else if (Assets.propertyDeck.getCard(cardIndex) instanceof UtilityCard) {
					if (cardGroup == 9) {

						rent = 25 * Assets.propertyDeck.getCard(cardIndex).getOwner().getGroups(cardGroup - 1);

						Assets.propertyDeck.getCard(cardIndex).getOwner().addMoney(rent);
						playerList.get(playerIndex).subtractMoney(rent);
						GameLogs.addMessage("Bot " + playerList.get(playerIndex).getPlayerNumber() + " payed " + rent
								+ " to " + Assets.propertyDeck.getCard(cardIndex).getOwner().getPlayerNumber());
						payedRent = true;
					} else if (cardGroup == 10) {
						if (Assets.propertyDeck.getCard(cardIndex).getOwner()
								.getGroups(cardGroup - 1) == Assets.propertyDeck.getCard(cardIndex).getOwner()
								.getGroupCapacity(cardGroup - 1))
							rent = (diceOne.getCurrent() + diceTwo.getCurrent()) * 10;
						else
							rent = (diceOne.getCurrent() + diceTwo.getCurrent()) * 4;

						Assets.propertyDeck.getCard(cardIndex).getOwner().addMoney(rent);
						playerList.get(playerIndex).subtractMoney(rent);
						GameLogs.addMessage("Bot " + playerList.get(playerIndex).getPlayerNumber() + " payed " + rent
								+ " to " + Assets.propertyDeck.getCard(cardIndex).getOwner().getPlayerNumber());
						payedRent = true;
					}
				}
			}
		}
	}

	public void buyPropertyEvent() {
		if (playerList.get(playerIndex).getPosition() == -1)
			return;
		if (playerRolled) {
			int cardIndex = playerList.get(playerIndex).positionToIndex();
			if (cardIndex == -1)
				return;
			if (!Assets.propertyDeck.cardInUse(cardIndex)) {
				playerList.get(playerIndex).subtractMoney(Assets.propertyDeck.getCard(cardIndex).getPrice());
				playerList.get(playerIndex).addProperty(Assets.propertyDeck.getCard(cardIndex));
				Assets.propertyDeck.getCard(cardIndex).setOwnership(playerList.get(playerIndex));
				Assets.propertyDeck.setCardInUse(cardIndex);
				GameLogs.addMessage("Bot " + playerList.get(playerIndex).getPlayerNumber() + " purchased "
						+ Assets.propertyDeck.getCard(cardIndex).getName());
				playerPropertySlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
				tradeOutSlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
			}
		}
	}

	public void nextEvent() {
		int cardIndex = playerList.get(playerIndex).positionToIndex();
		if (cardIndex == -1 || !Assets.propertyDeck.cardInUse(cardIndex)
				|| (Assets.propertyDeck.getCard(cardIndex).getOwner() == playerList.get(playerIndex))
				|| Assets.propertyDeck.getCard(cardIndex).isMortgaged()
				|| Assets.propertyDeck.getCard(cardIndex).getOwner().getPosition() == -1)
			payedRent = true;
		if (payedRent) {
			repeatRolls = 0;
			playerRolled = false;
			payedRent = false;
			GameLogs.addMessage("Bot " + playerList.get(playerIndex).getPlayerNumber() + " ended their turn");
			if (playerList.get(playerIndex).getRollsLeft() == 0) {
				playerIndex++;
				if (playerIndex > playerList.size() - 1) {
					playerIndex = 0;
				}
				if (playerList.get(playerIndex).getProperties().size() == 0)
					playerPropertySlideshow.setImages(null);
				else
					playerPropertySlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
				playerList.get(playerIndex).incrementRollsLeft(1);
			}
		}
	}

	public void skipTurn() {
		repeatRolls = 0;
		playerRolled = false;
		payedRent = false;
		playerIndex++;
		if (playerIndex > playerList.size() - 1) {
			playerIndex = 0;
		}
		if (playerList.get(playerIndex).getProperties().size() == 0)
			playerPropertySlideshow.setImages(null);
		else
			playerPropertySlideshow.setImages(playerList.get(playerIndex).getPropertyImages());
		playerList.get(playerIndex).incrementRollsLeft(1);

	}

}