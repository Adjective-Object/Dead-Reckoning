package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityTypeAction.
 */
public abstract class EntityTypeAction extends Action {

	/** The layer. */
	int layer;

	/**
	 * Instantiates a new entity type action.
	 *
	 * @param source the source
	 * @param target the target
	 * @param targetLayer the target layer
	 */
	public EntityTypeAction(Entity source, Tile target, int targetLayer) {
		super(source, target);
		layer = targetLayer;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	protected boolean apply(int delta) {
		if (target.getX() > source.getX()) {
			source.setFacing(true);
		} else if (target.getX() < source.getX()) {
			source.setFacing(false);
		}

		try {
			return applyToEntity((LivingEntity) target.getEntity(layer));
		} catch (ClassCastException e) {
			try {
				return applyToEntity((InteractiveEntity) target
						.getEntity(layer));
			} catch (ClassCastException y) {
				return applyToEntity(target.getEntity(layer));
			}
		}
	}

	/**
	 * Apply to entity.
	 *
	 * @param entity the entity
	 * @return true, if successful
	 */
	protected abstract boolean applyToEntity(Entity entity);

	/**
	 * Apply to entity.
	 *
	 * @param e the e
	 * @return true, if successful
	 */
	protected abstract boolean applyToEntity(LivingEntity e);

	/**
	 * Apply to entity.
	 *
	 * @param e the e
	 * @return true, if successful
	 */
	protected abstract boolean applyToEntity(InteractiveEntity e);

}
