package com.monopoly.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.monopoly.gfx.Assets;


public class Slideshow extends UIObject{

	private BufferedImage[] images;
	private BufferedImage[] arrows;
	private Clickable clicker;
	private int imageIndex;
	private boolean leftHover, rightHover;
	private Rectangle leftBounds,rightBounds;
	private double arrowScale, imageScale;
	
	public Slideshow(float x, float y, int width, int height, BufferedImage[] images, double arrowScale, double imageScale, int startIndex, Clickable clicker){
		super(x, y, width, height);
		imageIndex=startIndex;
		leftHover=false;
		rightHover=false;
		this.images = images;
		this.arrows = Assets.slideshowButtons;
		this.arrowScale=arrowScale;
		this.imageScale=imageScale;
		this.clicker=clicker;
		
		leftBounds = new Rectangle((int) x, (int) ((y+height/2) - (arrows[2].getHeight()*arrowScale/2)) , (int) (arrows[0].getWidth()*arrowScale), (int) (arrows[0].getHeight()*arrowScale));
		rightBounds = new Rectangle((int) (x+width-arrows[1].getWidth()*arrowScale),  (int) ((y+height/2) - (arrows[0].getHeight()*arrowScale/2)) , (int) (arrows[1].getWidth()*arrowScale), (int) (arrows[1].getHeight()*arrowScale));
	}
	
	@Override
	public void onMouseMove(MouseEvent e){
		if(leftBounds.contains(e.getX(), e.getY())) 
			leftHover = true;
		else
			leftHover=false;
		if (rightBounds.contains(e.getX(), e.getY())) 
			rightHover = true;
		else
			rightHover=false;
	}
	
	@Override
	public void onMouseRelease(MouseEvent e){
		if((leftHover || rightHover) && images!=null)
			onClick();
	}

	@Override
	public void update() {
	
	}

	@Override
	public void render(Graphics g) {
		if(images != null) {
			if(imageIndex<0)
				imageIndex = images.length+imageIndex;
			imageIndex = imageIndex % images.length;
			
			g.drawImage(images[imageIndex],(int) (x+width/2-images[imageIndex].getWidth()*imageScale/2), (int) ((y+height/2) - (images[imageIndex].getHeight()*imageScale/2)), (int) (images[imageIndex].getWidth()*imageScale), (int) (images[imageIndex].getHeight()*imageScale), null);
			
			if (leftHover)
				g.drawImage(arrows[2],(int) x, (int) ((y+height/2) - (arrows[2].getHeight()*arrowScale/2)) , (int) (arrows[2].getWidth()*arrowScale), (int) (arrows[2].getHeight()*arrowScale), null);
			else
				g.drawImage(arrows[0],(int) x, (int) ((y+height/2) - (arrows[0].getHeight()*arrowScale/2)) , (int) (arrows[0].getWidth()*arrowScale), (int) (arrows[0].getHeight()*arrowScale), null);
			
			if(rightHover)
				g.drawImage(arrows[3],(int) (x+width-arrows[3].getWidth()*arrowScale), (int) ((y+height/2) - (arrows[0].getHeight()*arrowScale/2)) , (int) (arrows[3].getWidth()*arrowScale), (int) (arrows[1].getHeight()*arrowScale), null);
			else
				g.drawImage(arrows[1],(int) (x+width-arrows[1].getWidth()*arrowScale), (int) ((y+height/2) - (arrows[0].getHeight()*arrowScale/2)), (int) (arrows[1].getWidth()*arrowScale), (int) (arrows[1].getHeight()*arrowScale), null);
		}
	}

	@Override
	public void onClick() {
		if(rightHover)
			imageIndex++;
		if(leftHover)
			imageIndex--;
		if(imageIndex<0)
			imageIndex = images.length+imageIndex;
		imageIndex = imageIndex % images.length;
		clicker.onClick();
	}
	
	public BufferedImage[] getImages() {
		return images;
	}

	public void setImages(BufferedImage[] images) {
		this.images = images;
	}

	public int getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}

	public boolean isLeftHover() {
		return leftHover;
	}

	public void setLeftHover(boolean leftHover) {
		this.leftHover = leftHover;
	}

	public boolean isRightHover() {
		return rightHover;
	}

	public void setRightHover(boolean rightHover) {
		this.rightHover = rightHover;
	}

}
