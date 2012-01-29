package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.status.Status;

public class ApplyStatusAction extends EntityTypeAction{

	Status s;
	
	public ApplyStatusAction(Tile source, Tile target, Status toApply) {
		super(source, target);
		this.s=toApply;
	}

	protected boolean applyToEntity(Entity entity) {return true;}
	
	protected boolean applyToEntity(LivingEntity e) {
		e.addCondition(s);
		return true;
	}
	
	protected boolean applyToEntity(InteractiveEntity e) {return true;}

	public String getMessage() {
		return source.getEntity().getName()+" made "+target.getEntity().getName()+" become "+s.getName();
	}

}
