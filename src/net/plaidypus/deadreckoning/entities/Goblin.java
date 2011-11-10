package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.skills.Movement;
import net.plaidypus.deadreckoning.skills.Skill;

import org.newdawn.slick.GameContainer;

public class Goblin extends LivingEntity{
	
	public Skill movement;
	
	public int direction;
	
	/**
	 * a testing monster class
	 */
	public Goblin() {
		super("res/player.entity");
		movement=new Movement(this);
		direction = 0;
	}

	/**
	 * if something is blocking its path, it will turn left
	 */
	public Action chooseAction(GameContainer gc, int delta) {
		if(direction==0){
			if(!movement.canTargetTile(this.getLocation().getToLeft()) || this.getLocation().getToLeft().equals(this.getLocation())){direction = Utilities.randInt(0,4);}
			else{return movement.makeAction(this.getLocation().getToLeft());}
		}
		if(direction==1){
			if(!movement.canTargetTile(this.getLocation().getToDown()) || this.getLocation().getToDown().equals(this.getLocation()) ){direction = Utilities.randInt(0,4);;}
			else{return movement.makeAction(this.getLocation().getToDown());}
		}
		if(direction==2){
			if(!movement.canTargetTile(this.getLocation().getToRight()) || this.getLocation().getToRight().equals(this.getLocation()) ){direction = Utilities.randInt(0,4);;}
			else{return movement.makeAction(this.getLocation().getToRight());}
		}
		if(direction==3){
			if(!movement.canTargetTile(this.getLocation().getToUp()) || this.getLocation().getToUp().equals(this.getLocation()) ){direction = Utilities.randInt(0,4);;}
			else{return movement.makeAction(this.getLocation().getToUp());}
		}
		return new WaitAction(this.getLocation());
	}

}
