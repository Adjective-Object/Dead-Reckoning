package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class Interact.
 */
public class Interact extends Action {

	/**
	 * Instantiates a new interact.
	 *
	 * @param source the source
	 * @param target the target
	 */
	public Interact(Entity source, Tile target) {
		super(source, target);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	@Override
	protected boolean isNoticed() {
		return true;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
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
