package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

public class Interact extends Action {

	public Interact(Entity source, Tile target) {
		super(source, target);
	}

	@Override
	protected boolean isNoticed() {
		return true;
	}

	@Override
	protected boolean apply(int delta) {
		for (int i = Tile.numLayers - 1; i >= 0; i--) {
			if (!target.isOpen(i)) {
				source.getParent().getGame()
						.addAction(target.getEntity(i).onInteract(source));
				break;
			}
		}
		return true;
	}

}
