package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

public class MoveAction extends Action {

	boolean animate;
	int destLayer;

	public MoveAction(Entity source, Tile destination, int destLayer) {
		super(source, destination);
		animate = false;
		if (destination.isVisible() || source.isVisible()) {
			animate = true;
		}
		this.destLayer = destLayer;
	}

	public boolean apply(int delta) {
		if (target.getX() < source.getX()) {
			source.setFacing(false);
		} else if (target.getX() > source.getX()) {
			source.setFacing(true);
		}
		source.getParent().moveEntity(source, target, destLayer);
		return true;
	}

	public String getMessage() {
		return null;
	}

	@Override
	protected boolean isNoticed() {
		return true;
	}

}
