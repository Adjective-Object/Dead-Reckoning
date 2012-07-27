package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityTypeAction.
 */
public abstract class EntityTypeAction extends Action {
	/**
	 * Instantiates a new entity type action.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param targetLayer
	 *            the target layer
	 */
	public EntityTypeAction(int sourceID, Tile target) {
		super(sourceID, target);
	}
	
	public EntityTypeAction(int sourceID, Entity target) {
		super(sourceID, target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	@Override
	protected boolean apply(int delta) {
		if (getTargetTile().getX() > GameBoard.getEntity(this.sourceID).getX()) {
			GameBoard.getEntity(this.sourceID).setFacing(true);
		} else if (getTargetTile().getX() < GameBoard.getEntity(this.sourceID).getX()) {
			GameBoard.getEntity(this.sourceID).setFacing(false);
		}
		
		if(getTargetEntity()!=null){
			if (LivingEntity.class.isAssignableFrom(getTargetEntity().getClass())) {
				return applyToEntity((LivingEntity) (getTargetEntity()),delta);
			} else if (InteractiveEntity.class.isAssignableFrom(getTargetEntity().getClass())) {
				return applyToEntity((InteractiveEntity) (getTargetEntity()),delta);
			} else {
				return applyToEntity((getTargetEntity()),delta);
			} 
		}
		return true;
	}

	/**
	 * Apply to entity.
	 * 
	 * @param entity
	 *            the entity
	 * @return true, if successful
	 */
	protected abstract boolean applyToEntity(Entity entity, int delta);

	/**
	 * Apply to entity.
	 * 
	 * @param e
	 *            the e
	 * @return true, if successful
	 */
	protected abstract boolean applyToEntity(LivingEntity e, int delta);

	/**
	 * Apply to entity.
	 * 
	 * @param e
	 *            the e
	 * @return true, if successful
	 */
	protected abstract boolean applyToEntity(InteractiveEntity e, int delta);

}
