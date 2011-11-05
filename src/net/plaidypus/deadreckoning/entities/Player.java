package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.MoveAction;

import org.newdawn.slick.GameContainer;
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
		this.input=i;
	}

	Player(Tile t) throws SlickException
	{
		super("res\\player.entity");
		setLocation(t);
	}
	
	public void update(GameContainer gc, int delta){
		// this is totally temporary
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			this.nextAction = new MoveAction(this,input.getMouseX()/DeadReckoningGame.tileSize,input.getMouseY()/DeadReckoningGame.tileSize);
		}
		super. update(gc,delta);
	}
	
}