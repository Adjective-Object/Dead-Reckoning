package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.skills.Movement;
import net.plaidypus.deadreckoning.skills.Skill;

import org.newdawn.slick.GameContainer;

public class Goblin extends LivingEntity{
	
	public Skill movement;
	
	public Goblin() {
		super("res\\player.entity");
		movement=new Movement(this);
	}

	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		if(movement.canTargetTile(this.getLocation().getToLeft()) && (this.getLocation()!=this.getLocation().getToLeft())){
			return movement.makeAction(this.getLocation().getToLeft());
		} else if(movement.canTargetTile(this.getLocation().getToDown()) && (this.getLocation()!=this.getLocation().getToDown())){
			return movement.makeAction(this.getLocation().getToDown());
		} else if(movement.canTargetTile(this.getLocation().getToRight()) && (this.getLocation()!=this.getLocation().getToRight())){
			return movement.makeAction(this.getLocation().getToRight());
		} else if(movement.canTargetTile(this.getLocation().getToUp()) && (this.getLocation()!=this.getLocation().getToUp())){
			return movement.makeAction(this.getLocation().getToUp());
		} else{
			return new WaitAction(this.getLocation());
		}
	}

}
