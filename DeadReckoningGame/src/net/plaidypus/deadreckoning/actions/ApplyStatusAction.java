package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.status.Status;

import org.newdawn.slick.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplyStatusAction.
 */
public class ApplyStatusAction extends EntityTypeAction {

	/** The s. */
	Status s;

	/**
	 * Instantiates a new apply status action.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param targetTile
	 *            the target tile
	 * @param toApply
	 *            the to apply
	 */
	public ApplyStatusAction(int sourceID, Entity target, Status toApply) {
		super(sourceID, target);
		this.s = toApply;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.Entity)
	 */
	@Override
	protected boolean applyToEntity(Entity entity, int delta) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.LivingEntity)
	 */
	@Override
	protected boolean applyToEntity(LivingEntity e, int delta) {
		e.addCondition(s);
		Log.info(getTargetEntity().getName() + " now has the status "
				+ s.getName());
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.InteractiveEntity)
	 */
	@Override
	protected boolean applyToEntity(InteractiveEntity e, int delta) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	@Override
	protected boolean isNoticed() {
		return getTargetTile().canBeSeen() || GameBoard.getEntity(this.sourceID).getLocation().canBeSeen();
	}

}
