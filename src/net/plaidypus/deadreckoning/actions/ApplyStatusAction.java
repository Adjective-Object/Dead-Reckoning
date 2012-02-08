package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.status.Status;

public class ApplyStatusAction extends EntityTypeAction{

	Status s;
	
	public ApplyStatusAction(Entity source, Tile target, int targetTile, Status toApply) {
		super(source, target, targetTile);
		this.s=toApply;
	}

	protected boolean applyToEntity(Entity entity) {return true;}
	
	protected boolean applyToEntity(LivingEntity e) {
		e.addCondition(s);
		sendMessage(target.getEntity(Tile.LAYER_ACTIVE).getName()+" become "+s.getName());
		return true;
	}
	
	protected boolean applyToEntity(InteractiveEntity e) {return true;}
	
	
	protected boolean isNoticed() {return target.canBeSeen() || source.getLocation().canBeSeen();}

}
