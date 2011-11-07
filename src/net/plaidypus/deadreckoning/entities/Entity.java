package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
/*

Griffin Brodman & Jeffrey Tao
Created: 11/4/2011

*/


public abstract class Entity
{
	private Tile location;
	
	public float relativeX, relativeY;
	private boolean facing;
	
	public Action nextAction;
	
	public Entity()
	{
		relativeX=0;
		relativeY=0;
	}

	Entity(Tile t)
	{
	location = t;
	}

	
	public abstract void update(GameContainer gc, int delta);
	
	public abstract Action chooseAction(GameContainer gc, int delta);
	
	public boolean isIdle(){
		if(nextAction!=null){
			return nextAction.completed;
		}
		return true;
	}
	
	public void applyAction(GameContainer gc, int delta){
		if(nextAction!=null && !nextAction.completed){
			nextAction.applyAction(delta);
		}
	}
	
	public void setAction(Action action) {
		this.nextAction=action;
	}
	
	public abstract void render(Graphics g, float x, float  y);
	
	public Tile getLocation()
	{
		return location;
	}
	
	public GameBoard getParent(){
		return getLocation().getParent();
	}
	
	public void setLocation(Tile t)
	{
		location = t;
	}
	
	public int getX(){
		return getLocation().getX();
	}
			
	public int getY(){
		return getLocation().getY();
	}		
	
	public int getAbsoluteX(){
		return (int) (getLocation().getX()*DeadReckoningGame.tileSize+relativeX);
	}
			
	public int getAbsoluteY(){
		return (int) (getLocation().getY()*DeadReckoningGame.tileSize+relativeY);
	}		
	
	public void interact(Entity e)
	{
		
	}

	public void setFacing(boolean facing) {
		this.facing = facing;
	}

	public boolean getFacing() {
		return facing;
	}

	
	
	
}