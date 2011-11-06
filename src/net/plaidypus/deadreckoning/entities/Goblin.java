package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.MoveAction;
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
		if(movement.canTargetTile(this.getLocation().getToLeft())){
			return new MoveAction(this.getLocation(),this.getLocation().getToLeft());
		}
		return null;
	}

}
