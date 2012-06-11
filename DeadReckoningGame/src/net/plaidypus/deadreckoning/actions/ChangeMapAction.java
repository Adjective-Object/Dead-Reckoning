package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangeMapAction.
 */
public class ChangeMapAction extends Action {

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
	public ChangeMapAction(Entity source, Tile target, int layer,
			Entity toOverWrite) {
		super(source, target);
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
		source.getParent().placeEntity(target, toWrite, layer);
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
