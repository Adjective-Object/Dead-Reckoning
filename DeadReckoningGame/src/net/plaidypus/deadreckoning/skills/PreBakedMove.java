package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.MoveAction;
import net.plaidypus.deadreckoning.board.Tile;

// TODO: Auto-generated Javadoc
/**
 * The Class PreBakedMove.
 */
public class PreBakedMove extends Movement {

	/** The yoff. */
	int xoff, yoff;


	/**
	 * Instantiates a new pre baked move.
	 * 
	 * @param l
	 *            the l
	 * @param xoff
	 *            the xoff
	 * @param yoff
	 *            the yoff
	 */
	public PreBakedMove(int sourceID, int xoff, int yoff) {
		super(sourceID);
		this.xoff = xoff;
		this.yoff = yoff;
		instant=true;
		this.level=1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.skills.Movement#makeAction(net.plaidypus.
	 * deadreckoning.board.Tile)
	 */
	@Override
	public Action makeAction(Tile target) {
		this.getSource().getParent().clearPrimaryHighlight();
		if (getSource().getLocation().getRelativeTo(xoff, yoff) .isEmpty(getSource().getLayer()) ||
				(DeadReckoningGame.debugMode && getSource().getLocation().getRelativeTo(xoff, yoff).isOpen(getSource().getLayer() )) ) {
			return new MoveAction(sourceID, getSource().getLocation().getRelativeTo(
					xoff, yoff), getSource().getLayer());
		}
		return null;
	}

}
