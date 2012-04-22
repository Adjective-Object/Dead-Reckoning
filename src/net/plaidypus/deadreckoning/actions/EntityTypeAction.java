package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class EntityTypeAction extends Action {

	int layer;

	public EntityTypeAction(Entity source, Tile target, int targetLayer) {
		super(source, target);
		layer = targetLayer;
	}

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

	protected abstract boolean applyToEntity(Entity entity);

	protected abstract boolean applyToEntity(LivingEntity e);

	protected abstract boolean applyToEntity(InteractiveEntity e);

}
