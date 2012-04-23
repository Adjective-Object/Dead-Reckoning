package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class MoveAction.
 */
public class MoveAction extends Action {

	/** The animate. */
	boolean animate;
	
	/** The dest layer. */
	int destLayer;

	/**
	 * Instantiates a new move action.
	 *
	 * @param source the source
	 * @param destination the destination
	 * @param destLayer the dest layer
	 */
	public MoveAction(Entity source, Tile destination, int destLayer) {
		super(source, destination);
		animate = false;
		if (destination.isVisible() || source.isVisible()) {
			animate = true;
		}
		this.destLayer = destLayer;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	public boolean apply(int delta) {
		if (target.getX() < source.getX()) {
			source.setFacing(false);
		} else if (target.getX() > source.getX()) {
			source.setFacing(true);
		}
		source.getParent().moveEntity(source, target, destLayer);
		return true;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	@Override
	protected boolean isNoticed() {
		return true;
	}

}
