package com.monopoly.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.monopoly.gfx.Assets;
import com.monopoly.gfx.Utils;

public class GameLogs {
	public static ArrayList<String> logMessages = new ArrayList<String>();
	
	public static void addMessage(String event) {
		logMessages.add(event);
	}
	
	public static void render(Graphics g, int x, int y) {
		int offset=0;
		if(logMessages.size()>53)
			offset=logMessages.size()-53;
		for(int i=offset;i<logMessages.size();i++) {
			Utils.drawString(g,"Event " + (i+1) + ": " + logMessages.get(i), x, y+15*(i-offset), false,
				Color.black, Assets.kabel12);
		}
	}
	
	
}
