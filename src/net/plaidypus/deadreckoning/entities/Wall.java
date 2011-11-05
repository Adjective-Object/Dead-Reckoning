package net.plaidypus.deadreckoning.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import net.plaidypus.deadreckoning.Tile;
/*

Griffin Brodman & Jeffrey Tao
Created: 11/4/2011

*/

public class Wall extends Entity
{
	public Wall()
	{
	}

	Wall(Tile t)
	{
		setLocation(t);
	}

	public void interact(Player P)
	{
	}

	@Override
	public void update(GameContainer gc, int delta) {}

	@Override
	public void render(Graphics g, int x, int y) {}
	
}