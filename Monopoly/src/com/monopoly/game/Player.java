package com.monopoly.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.monopoly.game.cards.PropertyCard;

public class Player {
	
	public static int nextPlayerNumber=1;
	private int playerNumber;
	private ArrayList<PropertyCard> properties; 
	private int money;
	private int width,height;
	private BufferedImage icon;
	private int rollsLeft;
	private int position;
	private int jailbreakCards;
	private boolean inactive;
	private int[] xPos = new int[] 	{0, 806, 725, 645, 564, 484, 404, 323, 243, 163, 0,  32,  32,  32,  32,  32,  32,  32,  32,  32, 0, 163, 243, 323, 404, 484, 564, 645, 725, 806, 936, 936, 936, 936, 936, 936, 936, 936, 936, 936};
	private int[] yPos = new int[]  {0, 936, 936, 936, 936, 936, 936, 936, 936, 936, 0, 805, 725, 645, 565, 484, 403, 322, 242, 162, 0,  32,  32,  32,  32,  32,  32,  32,  32,  32,  32, 162, 242, 322, 403, 484, 565, 645, 725, 805};
	private int[] goPos = new int[] {884, 884, 936, 884, 884, 936, 936, 936};
	private int[] visitPos = new int[] {12, 865, 12, 923, 45, 957, 103, 957};
	private int[] freePos = new int[] {32, 32, 84, 32, 32, 84, 84, 84};
	private int[] jailPos = new int[] {55, 869, 99, 869, 55, 913, 99, 913};
	private int[] groupCapacity = new int[] {2, 3, 3, 3, 3, 3, 3, 2, 4, 2};
	private int[] groupOwned = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private int[] deckPosRelation = new int[] {1,3,5,6,8,9,11,12,13,14,15,16,18,19,21,23,24,25,26,27,28,29,31,32,34,35,37,39};
	private int placeModifier;
	
	public Player(int x, int y, int width, int height, BufferedImage icon, int money) {
		properties = new ArrayList<PropertyCard>();
		this.width=width;
		this.height=height;
		this.icon=icon;
		this.money=money;
		this.inactive=false;
		position=0;
		rollsLeft=0;
		placeModifier=0;
		playerNumber=nextPlayerNumber;
		jailbreakCards=0;
		nextPlayerNumber++;
	}
	
	//Give Money
	public void addMoney(int amount){
		money+=amount;
	}
	
	//Take Away Money
	public void subtractMoney(int amount) {
		money-=amount;
	}
	
	//Adds Property
	public void addProperty(PropertyCard property) {
		properties.add(property);
		if(property.getGroup()!=-1) 
			groupOwned[property.getGroup()-1]++;
	}
	
	//Remove Property
	public void removeProperty(PropertyCard property) {
		properties.remove(property);
		if(property.getGroup()!=-1) 
			groupOwned[property.getGroup()-1]--;
	}

	/*Trade n Cards for n cards
	  [Player out] gives [Player in] [propertyOut] cards for [propertyIn]*/
	public void trade(Player out, Player in, ArrayList<PropertyCard> propertyOut, ArrayList<PropertyCard> propertyIn) {
		for(PropertyCard c : propertyOut) {
			c.setOwner(in);
			c.setTrading(false);
			out.removeProperty(c);
			in.addProperty(c);
		}
		for(PropertyCard c : propertyIn) {
			c.setOwner(out);
			c.setTrading(false);
			in.removeProperty(c);
			out.addProperty(c);
		}
	}
	
	public void eliminate(Player out, Player in, ArrayList<PropertyCard> propertyOut) {
		for(int i=propertyOut.size()-1;i>=0;i--) {
			propertyOut.get(i).setOwner(in);
			propertyOut.get(i).setTrading(false);
			in.addProperty(propertyOut.get(i));
			out.removeProperty(propertyOut.get(i));
		}
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		if(position==-1)
			g.drawImage(icon,jailPos[0+placeModifier],jailPos[1+placeModifier],width,height,null);
		else if(position==0)
			g.drawImage(icon,goPos[0+placeModifier],goPos[1+placeModifier],width,height,null);
		else if(position==10)
			g.drawImage(icon,visitPos[0+placeModifier],visitPos[1+placeModifier],width,height,null);
		else if(position==20)
			g.drawImage(icon,freePos[0+placeModifier],freePos[1+placeModifier],width,height,null);
		else
			g.drawImage(icon,xPos[position],yPos[position],width,height,null);
	}
	
	public void incrementPosition(int x) {
		position+=x;
		if(position>xPos.length-1) {
			position %= xPos.length;
			addMoney(200);
		}
	}
	
	public int positionToIndex() {
		int index=-1;
		for(int i=0;i<deckPosRelation.length;i++) {
			if(deckPosRelation[i]==position) {
				index=i;
				break;
			}
		}
		return index;
	}

	public boolean owns(PropertyCard property) {
		boolean owned=false;
		for(PropertyCard p : properties) {
			if(p==property) {
				owned=true;
				break;
			}
		}
		return owned;
	}
	
	public PropertyCard[] getCardbyGroup(int groupNum) {
		PropertyCard[] groupCards = new PropertyCard[groupCapacity[groupNum-1]];
		int arrayIndex=0;
		for(int i=0;i<properties.size();i++) {
			if(properties.get(i).getGroup() == groupNum) {
				groupCards[arrayIndex]=properties.get(i);
				arrayIndex++;
			}
		}
		return groupCards;
	}
	
	//Getters and Setters
	
	
	public void clearTrades() {
		for(PropertyCard c : properties) {
			c.setTrading(false);
		}
	}
	
	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	public void setPosition(int pos) {
		position = pos;
	}
	
	public int getJailbreakCards() {
		return jailbreakCards;
	}

	public void addJailbreakCards(int jailbreakCards) {
		this.jailbreakCards += jailbreakCards;
	}
	
	public void useJailbreakCards() {
		this.jailbreakCards -= 1;
	}

	public int getGroupCapacity(int i) {
		return groupCapacity[i];
	}
	
	public int getGroups(int i) {
		return groupOwned[i];
	}
	
	public int getPosition() {
		return position;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public int getPlaceModifier() {
		return placeModifier;
	}

	public void setPlaceModifier(int placeModifier) {
		this.placeModifier = placeModifier;
	}

	public ArrayList<PropertyCard> getProperties() {
		return properties;
	}

	public BufferedImage[] getPropertyImages() {
		BufferedImage[] propImages = new BufferedImage[properties.size()];
		for(int i=0;i<propImages.length;i++) {
			propImages[i] = properties.get(i).getFront();
		}
		return propImages;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getRollsLeft() {
		return rollsLeft;
	}

	public void incrementRollsLeft(int n) {
		rollsLeft+=n;
	}
	
	public void decrementRollsLeft(int n) {
		rollsLeft-=n;
	}
	
	
	
}
