package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.Tile;

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
	
	public abstract void render(Graphics g, int  x, int  y);
	
	public Tile getLocation()
	{
		return location;
	}
	
	public void setLocation(Tile t)
	{
		location = t;
	}
	
	public void interact(Entity e)
	{
		
	}
	
	
	
}