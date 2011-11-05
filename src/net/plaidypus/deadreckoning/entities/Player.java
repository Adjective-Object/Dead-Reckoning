package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.Tile;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/*

Griffin Brodman & Jeffrey Tao & don't forget Max / PJ
Created: 11/4/2011

*/

public class Player extends LivingEntity
{
	Input input;
	
	public Player(Input i) throws SlickException
	{
		super("res\\player.entity");
	}

	Player(Tile t) throws SlickException
	{
		super("res\\player.entity");
		setLocation(t);
	}
	
}