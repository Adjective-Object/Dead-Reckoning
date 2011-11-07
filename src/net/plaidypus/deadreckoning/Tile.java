package net.plaidypus.deadreckoning;

import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
/*

Griffin Brodman & Jeffrey Tao
Created: 11/4/2011

*/
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Tile
{
	private Entity containedEntity;
	private GameBoard parent;
	private boolean isEmpty;
	private int x,y;
	private Image tileFace;
	public int highlighted;
	
	public static final int fogOfWarLevels = 5;
	public boolean explored;
	public int fogOfWar;
	
	static final Color[] highlightColors = new Color[] {new Color(0,0,0,0),new Color(255,75,23,100),new Color(252,125,73,100)};
	
 	Tile(GameBoard parent, int x, int y) throws SlickException
	{
 		this.parent=parent;
 		this.y=y;
 		this.x=x;
 		isEmpty = true;
 		highlighted = 0;
 		fogOfWar=5;
 		explored=false;
 		tileFace = new Image("res\\floor"+1+".png");
	}
 	
	Tile(GameBoard parent, int x, int y, Entity e) throws SlickException
	{
		this(parent, x,y);
		containedEntity = e;
		containedEntity.setLocation(this);
	}

	public void placeEntity(Entity e)
	{
		containedEntity = e;
		containedEntity.setLocation(this);
		isEmpty = false;
	}

	public Entity getEntity()
	{
		return containedEntity;
	}
	
	public void removeEntity()
	{
		containedEntity = null;
		isEmpty = true;
	}
	public boolean isOpen()
	{
		return isEmpty;
	}

	public void movedTo(Player p)
	{
		if(isEmpty)
		{ 
			placeEntity(p);
			return;
		}
		containedEntity.interact(p);
	}
	
	public void setHighlighted(int h){
		this.highlighted=h;
	}
	
	public int getHighlighted(){
		return highlighted;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta){
		if(!this.isEmpty){
			this.getEntity().update(gc, delta);
		}
	}
	
	/**
	 * renders to Graphics
	 * @param gc
	 * @param sbg
	 * @param g
	 */
	public void render(Graphics g,float x, float y)
	{
		g.drawImage(tileFace,x*DeadReckoningGame.tileSize,y*DeadReckoningGame.tileSize);
		g.setColor(highlightColors[this.highlighted]);
		g.fillRect(x*DeadReckoningGame.tileSize,y*DeadReckoningGame.tileSize,DeadReckoningGame.tileSize,DeadReckoningGame.tileSize);
		g.setColor(new Color(0,0,0,(int)(((fogOfWarLevels-fogOfWar*1.0)/fogOfWarLevels) * 255 )));
		g.fillRect(x*DeadReckoningGame.tileSize,y*DeadReckoningGame.tileSize,DeadReckoningGame.tileSize,DeadReckoningGame.tileSize);
		
	}
	
	public Tile getToLeft(){
		return parent.getTileAt( Utilities.limitTo(getX()-1,0,this.parent.getWidth()), getY());
	}
	public Tile getToRight(){
		return parent.getTileAt(Utilities.limitTo(getX()+1,0,this.parent.getWidth()), getY());
	}
	public Tile getToUp(){
		return parent.getTileAt(getX(),Utilities.limitTo(getY()-1,0,this.parent.getHeight()));
	}
	public Tile getToDown(){
		return parent.getTileAt(getX(),Utilities.limitTo(getY()+1,0,this.parent.getHeight()));
	}
	
	public GameBoard getParent(){
		return this.parent;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
}