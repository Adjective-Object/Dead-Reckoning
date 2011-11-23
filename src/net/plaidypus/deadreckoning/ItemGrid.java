package net.plaidypus.deadreckoning;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.Items.Item;

public class ItemGrid {
	ArrayList<Item> contents;

	int width, height, externalBorder, internalBorder, selector;
	static Color backgroundColor = new Color(100, 100, 100);
	static Image tileimage;

	public ItemGrid(int width, int height, int externalBorder,
			int internalBorder) {
		this.width = width;
		this.height = height;
		this.externalBorder = externalBorder;
		this.internalBorder = internalBorder;
		this.selector = 0;
	}

	public static void init() throws SlickException {
		tileimage = new Image("res/lootscreen/ItemSlot.png");
	}

	public void setContents(ArrayList<Item> newcont) {
		this.contents = newcont;
	}

	public ArrayList<Item> getContents() {
		return contents;
	}

	public void render(Graphics g, int offsetX, int offsetY){
		g.setColor(backgroundColor);
		g.fillRect(offsetX,offsetY,externalBorder+width*(DeadReckoningGame.tileSize+internalBorder),externalBorder+height*(DeadReckoningGame.tileSize+internalBorder));
		
		for(int x=0; x<width; x++){
			for(int y=0; x<height; x++){
				g.drawImage(tileimage,x*DeadReckoningGame.tileSize+internalBorder,y*DeadReckoningGame.tileSize+internalBorder);
			}
		}
	}
}
