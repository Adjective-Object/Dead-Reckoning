package net.plaidypus.deadreckoning;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.items.Item;

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
	
	public void parseInput(Input i, int xoff, int yoff){
		if(i.isKeyPressed(Input.KEY_LEFT)){
			selector=Utilities.limitTo(selector-1,0,width*height);
		}
		if(i.isKeyPressed(Input.KEY_RIGHT)){
			selector=Utilities.limitTo(selector+1,0,width*height);
		}
		if(i.isKeyPressed(Input.KEY_UP)){
			selector=Utilities.limitTo(selector-width,0,width*height);
		}
		if(i.isKeyPressed(Input.KEY_DOWN)){
			selector=Utilities.limitTo(selector+width,0,width*height);
		}

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
				if(x+y*width<contents.size()){
					g.drawImage(contents.get(x+y*width).getImage(),offsetX+externalBorder+x*(DeadReckoningGame.tileSize+internalBorder),offsetY+externalBorder+y*(DeadReckoningGame.tileSize+internalBorder));
				}
				if(selector == x+y*width){
					g.setColor(new Color(255,255,255));
					g.drawRect(offsetX+externalBorder+x*(DeadReckoningGame.tileSize+internalBorder),offsetY+externalBorder+y*(DeadReckoningGame.tileSize+internalBorder),DeadReckoningGame.tileSize,DeadReckoningGame.tileSize);
				}
			}
		}
	}
	
	public Item getSelected() {
		return contents.get(selector);
	}

	public boolean isValidSelected() {
		return contents.size()>selector;
	}

	public void clearSelected() {
		this.contents.remove(selector);
	}
}
