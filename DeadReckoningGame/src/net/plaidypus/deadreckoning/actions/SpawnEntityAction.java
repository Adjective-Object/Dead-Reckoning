package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangeMapAction.
 */
public class SpawnEntityAction extends Action {

	/** The to write. */
	Entity toWrite;

	/** The layer. */
	int layer;

	/**
	 * Instantiates a new change map action.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param layer
	 *            the layer
	 * @param toOverWrite
	 *            the to over write
	 */
	public SpawnEntityAction(int sourceID, Tile target, int layer,
			Entity toOverWrite) {
		super(sourceID, target);
		toWrite = toOverWrite;
		this.layer = layer;
		takesTurn = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	protected boolean apply(int delta) {
		GameBoard.getEntity(this.sourceID).getParent().placeEntity(getTargetTile(), toWrite, layer);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	@Override
	protected boolean isNoticed() {
		return false;
	}
}
