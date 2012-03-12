package net.plaidypus.deadreckoning.hudelements;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.items.Item;

public class ItemGridElement extends HudElement{
	ArrayList<Item> contents;

	int width, height, externalBorder, internalBorder, selector;
	private Color backgroundColor;
	static Image tileimage;
	
	public ItemGridElement(int x, int y, int borderMeth){
		this(DeadReckoningGame.menuColor,x,y,borderMeth,5,8,2,10);
	}
	
	public ItemGridElement(Color background,int x, int y, int borderMeth, int width, int height, int internalBorder,
			int externalBorder) {
		super(x,y,borderMeth,true);
		this.width = width;
		this.height = height;
		this.externalBorder = externalBorder;
		this.internalBorder = internalBorder;
		this.selector = 0;
		this.backgroundColor=background;
	}
	
	public void makeFrom(Object o){
		this.selector=0;
		InteractiveEntity e = (InteractiveEntity)(o);//TODO making
		this.setContents(e.getInventory());
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		tileimage = new Image("res/lootscreen/ItemSlot.png");
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta){
		Input i = gc.getInput();
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
		
		if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON) && i.getAbsoluteMouseX()<this.getX()+this.getWidth() && i.getAbsoluteMouseY()<this.getY()+this.getHeight()){
			int rx=(i.getMouseX()-this.getX()-externalBorder)/(DeadReckoningGame.tileSize+internalBorder);
			int ry=(i.getMouseY()-this.getY()-externalBorder)/(DeadReckoningGame.tileSize+internalBorder);
			selector = rx+ry*width;
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
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.setColor(new Color(255,255,255));
		
		g.setColor(backgroundColor);
		g.fillRect(getX(),getY(),getWidth(),getHeight());
		
		g.setColor(Color.white);
		
		for(int xm=0; xm<width; xm++){
			for(int ym=0; ym<height; ym++){
				g.drawImage(tileimage,getX()+externalBorder+xm*(DeadReckoningGame.tileSize+internalBorder),getY()+externalBorder+ym*(DeadReckoningGame.tileSize+internalBorder));
				if(xm+ym*width<contents.size()){
					contents.get(xm+ym*width).render(g,getX()+externalBorder+xm*(DeadReckoningGame.tileSize+internalBorder),getY()+externalBorder+ym*(DeadReckoningGame.tileSize+internalBorder));
				}
				if(selector==xm+ym*width){
					g.drawRect(getX()+externalBorder+xm*(DeadReckoningGame.tileSize+internalBorder),getY()+externalBorder+ym*(DeadReckoningGame.tileSize+internalBorder),DeadReckoningGame.tileSize,DeadReckoningGame.tileSize);
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
