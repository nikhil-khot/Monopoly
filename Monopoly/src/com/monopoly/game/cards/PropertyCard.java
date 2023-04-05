package com.monopoly.game.cards;

import java.awt.image.BufferedImage;

import com.monopoly.game.Player;

public class PropertyCard extends Card{
	
	private int rentCost[], houses, houseCost;
	private int mortgageVal;
	private int price;
	private String name;
	private Player owner;
	private boolean owned, mortgaged, trading;
	private int group;
	
	public PropertyCard(BufferedImage front, String name, int price, int group, int x, int y) {
		super(front, x, y);
		rentCost=new int[1];
		rentCost[0]=0;
		this.name=name;
		this.price=price;
		this.mortgageVal=100;
		
		this.group=group;
		this.trading=false;
		
		owner=null;
		owned=false;
		mortgaged=false;
	}
	
	public PropertyCard(BufferedImage front, String name, int price, int houseCost, int[] rentCost,int group, int x, int y) {
		super(front, x, y);
		houses=0;
		this.trading=false;
		this.rentCost=rentCost;
		this.name=name;
		this.price=price;
		this.houseCost=houseCost;
		this.mortgageVal=price/2;
		
		this.group=group;
		
		owner=null;
		owned=false;
		mortgaged=false;
	}

	
	
	public boolean isTrading() {
		return trading;
	}

	public void setTrading(boolean trading) {
		this.trading = trading;
	}

	public int getRentCost() {
		return rentCost[houses];
	}

	public void setRentCost(int[] rentCost) {
		this.rentCost = rentCost;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public void setHouses(int houses) {
		this.houses = houses;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}

	public void setOwnership(Player player) {
		owner = player;
		owned=true;
	}

	public int getHouses() {
		return houses;
	}

	public void addHouses(int houses) {
		this.houses += houses;
	}
	
	public void removeHouses(int houses) {
		this.houses -= houses;
	}

	public int getHouseCost() {
		return houseCost;
	}

	public void setHouseCost(int houseCost) {
		this.houseCost = houseCost;
	}

	public int getMortgageVal() {
		return mortgageVal;
	}

	public void setMortgageVal(int mortgageVal) {
		this.mortgageVal = mortgageVal;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player getOwner() {
		return owner;
	}

	public boolean isOwned() {
		return owned;
	}

	public boolean isMortgaged() {
		return mortgaged;
	}

	public void setMortgaged(boolean mortgaged) {
		this.mortgaged = mortgaged;
	}
	
	
	
}
