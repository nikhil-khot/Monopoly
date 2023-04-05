package com.monopoly.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

import com.monopoly.game.cards.BoardCard;
import com.monopoly.game.cards.BoardDeck;
import com.monopoly.game.cards.DeedCard;
import com.monopoly.game.cards.PropertyCard;
import com.monopoly.game.cards.PropertyDeck;
import com.monopoly.game.cards.UtilityCard;

public class Assets {

	public static Font kabel48, kabel24, kabel12, kabel46;

	public static BufferedImage[] chanceCards, communityChestCards;
	public static final int chanceWidth = 458, chanceHeight = 292;

	public static BufferedImage[] titleDeeds;
	public static final int deedWidth = 450, deedHeight = 685;

	public static BufferedImage[] nextButtons;

	public static BufferedImage[] slideshowButtons;

	public static BufferedImage[] startButtons;

	public static BufferedImage[] playerSelectionButtonZero, playerSelectionButtonOne, playerSelectionButtonTwo,
			playerSelectionButtonThree, playerSelectionButtonFour;

	public static BufferedImage[] gamePieces, nameList;

	public static BufferedImage gameBoard, startScreen;

	public static BufferedImage[] buttonPalette;

	public static BufferedImage[] dice;
	
	public static BoardDeck chanceDeck, communityChestDeck;

	public static PropertyDeck propertyDeck;

	public static void init() {
		SpriteSheet chanceCardsSheet = new SpriteSheet(Utils.loadImage("/ChanceCards.png"));
		SpriteSheet communityChestCardsSheet = new SpriteSheet(Utils.loadImage("/CommunityChestCards.png"));
		SpriteSheet titleDeedsSheet = new SpriteSheet(Utils.loadImage("/TitleDeeds.png"));
		SpriteSheet startButtonsSheet = new SpriteSheet(Utils.loadImage("/StartButtons.png"));
		SpriteSheet slideshowButtonsSheet = new SpriteSheet(Utils.loadImage("/SlideshowButtons.png"));
		SpriteSheet gamePieceSheet = new SpriteSheet(Utils.loadImage("/GamePieces.png"));
		SpriteSheet playerSelectionButtonsSheet = new SpriteSheet(Utils.loadImage("/PlayerSelectionButtons.png"));
		SpriteSheet nextButtonsSheet = new SpriteSheet(Utils.loadImage("/SettingsNextButtons.png"));
		SpriteSheet buttonPaletteSheet = new SpriteSheet(Utils.loadImage("/ButtonPalette.png"));
		SpriteSheet nameListSheet = new SpriteSheet(Utils.loadImage("/NameList.png"));
		SpriteSheet diceSheet = new SpriteSheet(Utils.loadImage("/Dice.png"));

		gameBoard = Utils.loadImage("/GameBoard.png");

		startScreen = Utils.loadImage("/StartScreen.png");

		kabel12 = Utils.loadFont("res/fonts/kabelc-medium.otf", 12);
		kabel24 = Utils.loadFont("res/fonts/kabelc-medium.otf", 24);
		kabel48 = Utils.loadFont("res/fonts/kabelc-medium.otf", 48);
		kabel46 = Utils.loadFont("res/fonts/kabelc-medium.otf", 46);

		buttonPalette = new BufferedImage[2];
		buttonPalette[0] = buttonPaletteSheet.crop(0, 0, 1000, 500);
		buttonPalette[1] = buttonPaletteSheet.crop(0, 500, 1000, 500);

		playerSelectionButtonZero = new BufferedImage[2];
		playerSelectionButtonZero[0] = playerSelectionButtonsSheet.crop(0, 800, 200, 200);
		playerSelectionButtonZero[1] = playerSelectionButtonsSheet.crop(200, 800, 200, 200);

		playerSelectionButtonOne = new BufferedImage[2];
		playerSelectionButtonOne[0] = playerSelectionButtonsSheet.crop(0, 0, 200, 200);
		playerSelectionButtonOne[1] = playerSelectionButtonsSheet.crop(0, 400, 200, 200);

		playerSelectionButtonTwo = new BufferedImage[2];
		playerSelectionButtonTwo[0] = playerSelectionButtonsSheet.crop(200, 0, 200, 200);
		playerSelectionButtonTwo[1] = playerSelectionButtonsSheet.crop(200, 400, 200, 200);

		playerSelectionButtonThree = new BufferedImage[2];
		playerSelectionButtonThree[0] = playerSelectionButtonsSheet.crop(0, 200, 200, 200);
		playerSelectionButtonThree[1] = playerSelectionButtonsSheet.crop(0, 600, 200, 200);

		playerSelectionButtonFour = new BufferedImage[2];
		playerSelectionButtonFour[0] = playerSelectionButtonsSheet.crop(200, 200, 200, 200);
		playerSelectionButtonFour[1] = playerSelectionButtonsSheet.crop(200, 600, 200, 200);

		nameList = new BufferedImage[4];
		nameList[0] = nameListSheet.crop(0, 0, 295, 82);
		nameList[1] = nameListSheet.crop(0, 82, 295, 82);
		nameList[2] = nameListSheet.crop(0,82*2, 295, 82);
		nameList[3] = nameListSheet.crop(0,82*3, 295, 82);
	
		nextButtons = new BufferedImage[2];
		nextButtons[0] = nextButtonsSheet.crop(0, 0, 608, 80);
		nextButtons[1] = nextButtonsSheet.crop(0, 80, 608, 80);

		slideshowButtons = new BufferedImage[4];
		slideshowButtons[0] = slideshowButtonsSheet.crop(0, 0, 185, 220);
		slideshowButtons[1] = slideshowButtonsSheet.crop(185, 0, 185, 220);
		slideshowButtons[2] = slideshowButtonsSheet.crop(0, 220, 185, 220);
		slideshowButtons[3] = slideshowButtonsSheet.crop(185, 220, 185, 220);

		startButtons = new BufferedImage[2];
		startButtons[0] = startButtonsSheet.crop(0, 0, 496, 150);
		startButtons[1] = startButtonsSheet.crop(0, 150, 496, 150);

		gamePieces = new BufferedImage[8];
		gamePieces[0] = gamePieceSheet.crop(0, 0, 300, 300);
		gamePieces[1] = gamePieceSheet.crop(300, 0, 300, 300);
		gamePieces[2] = gamePieceSheet.crop(0, 300, 300, 300);
		gamePieces[3] = gamePieceSheet.crop(300, 300, 300, 300);
		gamePieces[4] = gamePieceSheet.crop(0, 600, 300, 300);
		gamePieces[5] = gamePieceSheet.crop(300, 600, 300, 300);
		gamePieces[6] = gamePieceSheet.crop(0, 900, 300, 300);
		gamePieces[7] = gamePieceSheet.crop(300, 900, 300, 300);

		dice = new BufferedImage[6];
		dice[0] = diceSheet.crop(0, 557 * 0, 557, 557);
		dice[1] = diceSheet.crop(0, 557 * 1, 557, 557);
		dice[2] = diceSheet.crop(0, 557 * 2, 557, 557);
		dice[3] = diceSheet.crop(0, 557 * 3, 557, 557);
		dice[4] = diceSheet.crop(0, 557 * 4, 557, 557);
		dice[5] = diceSheet.crop(0, 557 * 5, 557, 557);

		chanceCards = new BufferedImage[18];
		chanceCards[0] = chanceCardsSheet.crop(0 * chanceWidth, 0 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[1] = chanceCardsSheet.crop(1 * chanceWidth, 0 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[2] = chanceCardsSheet.crop(0 * chanceWidth, 1 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[3] = chanceCardsSheet.crop(1 * chanceWidth, 1 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[4] = chanceCardsSheet.crop(0 * chanceWidth, 2 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[5] = chanceCardsSheet.crop(1 * chanceWidth, 2 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[6] = chanceCardsSheet.crop(0 * chanceWidth, 3 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[7] = chanceCardsSheet.crop(1 * chanceWidth, 3 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[8] = chanceCardsSheet.crop(0 * chanceWidth, 4 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[9] = chanceCardsSheet.crop(1 * chanceWidth, 4 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[10] = chanceCardsSheet.crop(0 * chanceWidth, 5 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[11] = chanceCardsSheet.crop(1 * chanceWidth, 5 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[12] = chanceCardsSheet.crop(0 * chanceWidth, 6 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[13] = chanceCardsSheet.crop(1 * chanceWidth, 6 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[14] = chanceCardsSheet.crop(0 * chanceWidth, 7 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[15] = chanceCardsSheet.crop(1 * chanceWidth, 7 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[16] = chanceCardsSheet.crop(0 * chanceWidth, 8 * chanceHeight, chanceWidth, chanceHeight);
		chanceCards[17] = chanceCardsSheet.crop(1 * chanceWidth, 8 * chanceHeight, chanceWidth, chanceHeight);

		communityChestCards = new BufferedImage[18];
		communityChestCards[0] = communityChestCardsSheet.crop(0 * chanceWidth, 0 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[1] = communityChestCardsSheet.crop(1 * chanceWidth, 0 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[2] = communityChestCardsSheet.crop(0 * chanceWidth, 1 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[3] = communityChestCardsSheet.crop(1 * chanceWidth, 1 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[4] = communityChestCardsSheet.crop(0 * chanceWidth, 2 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[5] = communityChestCardsSheet.crop(1 * chanceWidth, 2 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[6] = communityChestCardsSheet.crop(0 * chanceWidth, 3 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[7] = communityChestCardsSheet.crop(1 * chanceWidth, 3 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[8] = communityChestCardsSheet.crop(0 * chanceWidth, 4 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[9] = communityChestCardsSheet.crop(1 * chanceWidth, 4 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[10] = communityChestCardsSheet.crop(0 * chanceWidth, 5 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[11] = communityChestCardsSheet.crop(1 * chanceWidth, 5 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[12] = communityChestCardsSheet.crop(0 * chanceWidth, 6 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[13] = communityChestCardsSheet.crop(1 * chanceWidth, 6 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[14] = communityChestCardsSheet.crop(0 * chanceWidth, 7 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[15] = communityChestCardsSheet.crop(1 * chanceWidth, 7 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[16] = communityChestCardsSheet.crop(0 * chanceWidth, 8 * chanceHeight, chanceWidth,
				chanceHeight);
		communityChestCards[17] = communityChestCardsSheet.crop(1 * chanceWidth, 8 * chanceHeight, chanceWidth,
				chanceHeight);

		titleDeeds = new BufferedImage[28];
		titleDeeds[0] = titleDeedsSheet.crop(0 * deedWidth, 0 * deedHeight, deedWidth, deedHeight);
		titleDeeds[1] = titleDeedsSheet.crop(1 * deedWidth, 0 * deedHeight, deedWidth, deedHeight);
		titleDeeds[2] = titleDeedsSheet.crop(2 * deedWidth, 0 * deedHeight, deedWidth, deedHeight);
		titleDeeds[3] = titleDeedsSheet.crop(3 * deedWidth, 0 * deedHeight, deedWidth, deedHeight);
		titleDeeds[4] = titleDeedsSheet.crop(0 * deedWidth, 1 * deedHeight, deedWidth, deedHeight);
		titleDeeds[5] = titleDeedsSheet.crop(1 * deedWidth, 1 * deedHeight, deedWidth, deedHeight);
		titleDeeds[6] = titleDeedsSheet.crop(2 * deedWidth, 1 * deedHeight, deedWidth, deedHeight);
		titleDeeds[7] = titleDeedsSheet.crop(3 * deedWidth, 1 * deedHeight, deedWidth, deedHeight);
		titleDeeds[8] = titleDeedsSheet.crop(0 * deedWidth, 2 * deedHeight, deedWidth, deedHeight);
		titleDeeds[9] = titleDeedsSheet.crop(1 * deedWidth, 2 * deedHeight, deedWidth, deedHeight);
		titleDeeds[10] = titleDeedsSheet.crop(2 * deedWidth, 2 * deedHeight, deedWidth, deedHeight);
		titleDeeds[11] = titleDeedsSheet.crop(3 * deedWidth, 2 * deedHeight, deedWidth, deedHeight);
		titleDeeds[12] = titleDeedsSheet.crop(0 * deedWidth, 3 * deedHeight, deedWidth, deedHeight);
		titleDeeds[13] = titleDeedsSheet.crop(1 * deedWidth, 3 * deedHeight, deedWidth, deedHeight);
		titleDeeds[14] = titleDeedsSheet.crop(2 * deedWidth, 3 * deedHeight, deedWidth, deedHeight);
		titleDeeds[15] = titleDeedsSheet.crop(3 * deedWidth, 3 * deedHeight, deedWidth, deedHeight);
		titleDeeds[16] = titleDeedsSheet.crop(0 * deedWidth, 4 * deedHeight, deedWidth, deedHeight);
		titleDeeds[17] = titleDeedsSheet.crop(1 * deedWidth, 4 * deedHeight, deedWidth, deedHeight);
		titleDeeds[18] = titleDeedsSheet.crop(2 * deedWidth, 4 * deedHeight, deedWidth, deedHeight);
		titleDeeds[19] = titleDeedsSheet.crop(3 * deedWidth, 4 * deedHeight, deedWidth, deedHeight);
		titleDeeds[20] = titleDeedsSheet.crop(0 * deedWidth, 5 * deedHeight, deedWidth, deedHeight);
		titleDeeds[21] = titleDeedsSheet.crop(1 * deedWidth, 5 * deedHeight, deedWidth, deedHeight);
		titleDeeds[22] = titleDeedsSheet.crop(2 * deedWidth, 5 * deedHeight, deedWidth, deedHeight);
		titleDeeds[23] = titleDeedsSheet.crop(3 * deedWidth, 5 * deedHeight, deedWidth, deedHeight);
		titleDeeds[24] = titleDeedsSheet.crop(0 * deedWidth, 6 * deedHeight, deedWidth, deedHeight);
		titleDeeds[25] = titleDeedsSheet.crop(1 * deedWidth, 6 * deedHeight, deedWidth, deedHeight);
		titleDeeds[26] = titleDeedsSheet.crop(2 * deedWidth, 6 * deedHeight, deedWidth, deedHeight);
		titleDeeds[27] = titleDeedsSheet.crop(3 * deedWidth, 6 * deedHeight, deedWidth, deedHeight);

		chanceDeck = new BoardDeck(new BoardCard[] { new BoardCard(chanceCards[0], chanceCards[1],271, 354, 15, 1),
				new BoardCard(chanceCards[0], chanceCards[2], 271, 354, 15, 2),
				new BoardCard(chanceCards[0], chanceCards[3], 271, 354, 15, 3),
				//new BoardCard(chanceCards[0], chanceCards[4], 271, 354, 15, 4),
				//new BoardCard(chanceCards[0], chanceCards[5], 271, 354, 15, 5),
				new BoardCard(chanceCards[0], chanceCards[6], 271, 354, 15, 4),
				new BoardCard(chanceCards[0], chanceCards[7], 271, 354, 15, 5),
				//new BoardCard(chanceCards[0], chanceCards[8], 271, 354, 15, 6),
				new BoardCard(chanceCards[0], chanceCards[9], 271, 354, 15, 7),
				new BoardCard(chanceCards[0], chanceCards[10], 271, 354, 15, 8),
				new BoardCard(chanceCards[0], chanceCards[11], 271, 354, 15, 9),
				new BoardCard(chanceCards[0], chanceCards[12], 271, 354, 15, 10),
				new BoardCard(chanceCards[0], chanceCards[13], 271, 354, 15, 11),
				new BoardCard(chanceCards[0], chanceCards[14], 271, 354, 15, 12),
				new BoardCard(chanceCards[0], chanceCards[15], 271, 354, 15, 13),
				new BoardCard(chanceCards[0], chanceCards[16], 271, 354, 15, 14),
				new BoardCard(chanceCards[0], chanceCards[17], 271, 354, 15, 15) });
		chanceDeck.shuffle();
		
		communityChestDeck = new BoardDeck(new BoardCard[] { new BoardCard(communityChestCards[0], communityChestCards[1], 271, 354, 15, 1),
				new BoardCard(communityChestCards[0], communityChestCards[2], 271, 354, 15, 2),
				new BoardCard(communityChestCards[0], communityChestCards[3], 271, 354, 15, 3),
				new BoardCard(communityChestCards[0], communityChestCards[4], 271, 354, 15, 4),
				new BoardCard(communityChestCards[0], communityChestCards[5], 271, 354, 15, 5),
				new BoardCard(communityChestCards[0], communityChestCards[6], 271, 354, 15, 6),
				new BoardCard(communityChestCards[0], communityChestCards[7], 271, 354, 15, 7),
				new BoardCard(communityChestCards[0], communityChestCards[8], 271, 354, 15, 8),
				new BoardCard(communityChestCards[0], communityChestCards[9], 271, 354, 15, 9),
				new BoardCard(communityChestCards[0], communityChestCards[10], 271, 354, 15, 10),
				new BoardCard(communityChestCards[0], communityChestCards[11], 271, 354, 15, 11),
				new BoardCard(communityChestCards[0], communityChestCards[12], 271, 354, 15, 12),
				new BoardCard(communityChestCards[0], communityChestCards[13], 271, 354, 15, 13),
				new BoardCard(communityChestCards[0], communityChestCards[14], 271, 354, 15, 14),
				new BoardCard(communityChestCards[0], communityChestCards[15], 271, 354, 15, 15),
				new BoardCard(communityChestCards[0], communityChestCards[16], 271, 354, 15, 16),
				new BoardCard(communityChestCards[0], communityChestCards[17], 271, 354, 15, 17) });
		communityChestDeck.shuffle();
		
		propertyDeck = new PropertyDeck(new PropertyCard[] {new DeedCard(titleDeeds[0], "Mediterranean Avenue", 60, 50, new int[] {2,10,30,90,160,250}, 1, 0, 0),
				new DeedCard(titleDeeds[1], "Baltic Avenue", 60, 50, new int[] {4,20,60,190,320,450}, 1, 0, 0),
				new UtilityCard(titleDeeds[24], "Reading Railroad", 200, 9, 0, 0),
				new DeedCard(titleDeeds[2], "Oriental Avenue", 100, 50, new int[] {6,30,90,270,400,550}, 2, 0, 0),
				new DeedCard(titleDeeds[3], "Vermont Avenue", 100, 50, new int[] {6,30,90,270,400,550}, 2, 0, 0),
				new DeedCard(titleDeeds[4], "Connecticut Avenue", 120, 50, new int[] {8,40,100,300,450,600}, 2, 0, 0),
				new DeedCard(titleDeeds[5], "St. Charles Place", 140, 100, new int[] {10,50,150,450,625,750}, 3, 0, 0),
				new UtilityCard(titleDeeds[22], "Electric Company", 150, 10, 0, 0),
				new DeedCard(titleDeeds[6], "States Avenue", 140, 100, new int[] {10,50,150,450,625,750}, 3, 0, 0),
				new DeedCard(titleDeeds[7], "Virginia Avenue", 160, 100, new int[] {12,60,180,500,700,900}, 3, 0, 0),
				new UtilityCard(titleDeeds[25], "Pennsylvania Railroad", 200, 9, 0, 0),
				new DeedCard(titleDeeds[8], "St. James Place", 180, 100, new int[] {14,70,200,550,750,950}, 4, 0, 0),
				new DeedCard(titleDeeds[9], "Tennessee Avenue", 180, 100, new int[] {14,70,200,550,750,950}, 4, 0, 0),
				new DeedCard(titleDeeds[10], "New York Avenue", 200, 100, new int[] {16,80,220,600,800,1000}, 4, 0, 0),
				new DeedCard(titleDeeds[11], "Kentucky Avenue", 220, 150, new int[] {18,90,250,700,875,1050}, 5, 0, 0),
				new DeedCard(titleDeeds[12], "Indiana Avenue", 220, 150, new int[] {18,90,250,700,875,1050}, 5, 0, 0),
				new DeedCard(titleDeeds[13], "Illinois Avenue", 240, 150, new int[] {20,100,300,750,925,1100}, 5, 0, 0),
				new UtilityCard(titleDeeds[26], "B. & O. Railroad", 200, 9, 0, 0),
				new DeedCard(titleDeeds[14], "Atlantic Avenue", 260, 150, new int[] {22,110,330,800,975,1150}, 6, 0, 0),
				new DeedCard(titleDeeds[15], "Ventnor Avenue", 260, 150, new int[] {22,110,330,800,975,1150}, 6, 0, 0),
				new UtilityCard(titleDeeds[23], "Water Works", 150, 10, 0, 0),
				new DeedCard(titleDeeds[16], "Marvin Gardens", 280, 150, new int[] {24,120,360,850,1025,1200}, 6, 0, 0),
				new DeedCard(titleDeeds[17], "Pacific Avenue", 300, 200, new int[] {26,130,390,900,1100,1275}, 7, 0, 0),
				new DeedCard(titleDeeds[18], "North Carolina Avenue", 300, 200, new int[] {26,130,390,900,1100,1275}, 7, 0, 0),
				new DeedCard(titleDeeds[19], "Pennsylvania Avenue", 320, 200, new int[] {26,150,450,1000,1200,1400}, 7, 0, 0),
				new UtilityCard(titleDeeds[27], "Short Line", 200, 9, 0, 0),
				new DeedCard(titleDeeds[20], "Park Place", 350, 200, new int[] {35,175,500,1100,1300,1500}, 8, 0, 0),
				new DeedCard(titleDeeds[21], "Boardwalk", 400, 200, new int[] {50,200,600,1400,1700,2000}, 8, 0, 0)});
		
		
	}

}
