package net.plaidypus.deadreckoning;

import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Player;

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
	Image tileFace;
	
 	Tile(GameBoard parent, int x, int y) throws SlickException
	{
 		this.parent=parent;
 		this.y=y;
 		this.x=x;
 		isEmpty = true;
 		tileFace = new Image("res\\floor1.png");
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
	public void render(Graphics g,int x, int y)
	{
		g.drawImage(tileFace,x*DeadReckoningGame.tileSize,y*DeadReckoningGame.tileSize);
		if(!this.isEmpty){
			System.out.println(x+" "+y);
			this.getEntity().render(g, x, y);
		}
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