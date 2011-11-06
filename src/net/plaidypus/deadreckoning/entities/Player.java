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
	
	public static int MoveCommand = 0, AttackCommand=1, ClassCommand = 2;
	public int CommandType;
	
	public Player(Input i) throws SlickException
	{
		super("res\\player.entity");
		this.input=i;
	}

	Player(Input i, Tile t) throws SlickException
	{
		super("res\\player.entity");
		this.input=i;
		setLocation(t);
	}
	
	public void chooseAction(GameContainer gc, int delta){
		// this is totally temporary
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			this.getParent().setPrimairyHighlight(this.input.getMouseX()/DeadReckoningGame.tileSize  , input.getMouseY()/DeadReckoningGame.tileSize);
		}
		
		if(this.getParent().getPrimairyHighlight()!=null){ //moving the primary highlighted square
			if(input.isKeyPressed(Input.KEY_LEFT)){
				this.getParent().setPrimairyHighlight(this.getParent().getPrimairyHighlight().getToLeft());
			}
			if(input.isKeyPressed(Input.KEY_RIGHT)){
				this.getParent().setPrimairyHighlight(this.getParent().getPrimairyHighlight().getToRight());
			}
			if(input.isKeyPressed(Input.KEY_UP)){
				this.getParent().setPrimairyHighlight(this.getParent().getPrimairyHighlight().getToUp());
			}
			if(input.isKeyPressed(Input.KEY_DOWN)){
				this.getParent().setPrimairyHighlight(this.getParent().getPrimairyHighlight().getToDown());
			}
		}
		
		if(input.isKeyPressed(Input.KEY_M)){
			this.getLocation().getParent().clearHighlightedSquares();
			this.getLocation().getParent().highlightInRadius( this.getLocation(),this.getMovementSpeed() );
			this.CommandType=MoveCommand;
			if(this.getParent().getPrimairyHighlight()==null){
				this.getParent().setPrimairyHighlight(this.getLocation());
			}
		}
		
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			this.getLocation().getParent().clearHighlightedSquares();
			this.getLocation().getParent().clearPrimaryHighlight();
		}
		if(input.isKeyPressed(Input.KEY_ENTER) && this.getParent().getPrimairyHighlight().isHighlighted()){
			this.nextAction=new MoveAction(this,this.getParent().getPrimairyHighlight().getX(),this.getParent().getPrimairyHighlight().getY());
			this.getLocation().getParent().clearHighlightedSquares();
			this.getLocation().getParent().clearPrimaryHighlight();
		}
		else if(input.isKeyPressed(Input.KEY_ENTER)){
			//TODO play error sound
		}
	}
	
}