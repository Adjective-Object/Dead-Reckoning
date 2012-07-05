package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.status.Status;

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
	public ApplyStatusAction(int sourceID, Tile target, int targetTile,
			Status toApply) {
		super(sourceID, target, targetTile);
		this.s = toApply;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.Entity)
	 */
	protected boolean applyToEntity(Entity entity) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.LivingEntity)
	 */
	protected boolean applyToEntity(LivingEntity e) {
		e.addCondition(s);
		sendMessage(target.getEntity(Tile.LAYER_ACTIVE).getName() + " become "
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
	protected boolean applyToEntity(InteractiveEntity e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	protected boolean isNoticed() {
		return target.canBeSeen() || GameBoard.getEntity(this.sourceID).getLocation().canBeSeen();
	}

}
