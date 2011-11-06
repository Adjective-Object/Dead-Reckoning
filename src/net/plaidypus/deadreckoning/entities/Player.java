package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.skills.Attack;
import net.plaidypus.deadreckoning.skills.Movement;
import net.plaidypus.deadreckoning.skills.Skill;

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
	
	public static int[] keyBinds;
	public static Skill[] skills;
	
	public int currentSkill;
	
	public Player(Input i) throws SlickException
	{
		super("res\\player.entity");
		this.input=i;
		
		keyBinds = new int[] {Input.KEY_M, Input.KEY_A};
		skills = new Skill[] {new Movement(this), new Attack(this)};
	}
	
	public Action chooseAction(GameContainer gc, int delta){
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
		
		for(int i=0; i<keyBinds.length;i++){
			if(input.isKeyPressed(keyBinds[i])){
				this.currentSkill=i;
				
				skills[i].highlightRange(getParent());
				
				if(this.getParent().getPrimairyHighlight()==null){
					this.getParent().setPrimairyHighlight(this.getLocation());
				}
			}
		}
		
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			this.getLocation().getParent().clearHighlightedSquares();
			this.getLocation().getParent().clearPrimaryHighlight();
		}
		if(input.isKeyPressed(Input.KEY_ENTER) && this.getParent().getPrimairyHighlight()!=null && this.getParent().getPrimairyHighlight().isHighlighted){
			Action toRet = skills[currentSkill].makeAction(this.getParent().getPrimairyHighlight());
			this.getLocation().getParent().clearHighlightedSquares();
			this.getLocation().getParent().clearPrimaryHighlight();
			return toRet;
		}
		else{
			//TODO play fail sound
		}
		return null;
	}
	
}