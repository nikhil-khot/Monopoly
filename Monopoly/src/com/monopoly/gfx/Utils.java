package com.monopoly.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {

	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(Utils.class.getResource(path));
		} catch (IOException e) {
			System.out.println("Image not found");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static Font loadFont(String path, float size) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException  | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color c, Font font) {
		g.setColor(c);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
		if(center) {
			FontMetrics fm = g.getFontMetrics(font);
			x = xPos - fm.stringWidth(text) / 2;
			y = (yPos - fm.getHeight() / 2) + fm.getAscent();
		}
			g.drawString(text,x,y);
	}
	
}
