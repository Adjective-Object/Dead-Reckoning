package net.plaidypus.deadreckoning;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.ergomgs.Item;

public class ItemGrid {
	ArrayList<Item> contents;

	int width, height, externalBorder, internalBorder, selector;
	private Color backgroundColor;
	static Image tileimage;

	public ItemGrid(Color background, int width, int height, int internalBorder,
			int externalBorder) {
		this.width = width;
		this.height = height;
		this.externalBorder = externalBorder;
		this.internalBorder = internalBorder;
		this.selector = 0;
		this.backgroundColor=background;
	}

	public static void init() throws SlickException {
		tileimage = new Image("res/lootscreen/ItemSlot.png");
	}

	public void setContents(ArrayList<Item> newcont) {
		this.contents = newcont;
	}
	
	public void setColor(Color newColor){
		backgroundColor=newColor;
	}

	public ArrayList<Item> getContents() {
		return contents;
	}
	
	public int getWidth(){
		return 2*externalBorder+width*(DeadReckoningGame.tileSize+internalBorder);
	}
	
	public int getHeight(){
		return 2*externalBorder+height*(DeadReckoningGame.tileSize+internalBorder);
	}
	
	public void render(Graphics g, int offsetX, int offsetY){
		g.setColor(backgroundColor);
		g.fillRect(offsetX,offsetY,getWidth(),getHeight());
		
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				g.drawImage(tileimage,offsetX+externalBorder+x*(DeadReckoningGame.tileSize+internalBorder),offsetY+externalBorder+y*(DeadReckoningGame.tileSize+internalBorder));
			}
		}
	}
}
