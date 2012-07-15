package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.GameBoard;
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
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 */
	public Interact(int sourceID, Tile target) {
		super(sourceID, target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	@Override
	protected boolean isNoticed() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	@Override
	protected boolean apply(int delta) {
		for (int i = Tile.numLayers - 1; i >= 0; i--) {
			if (!target.isOpen(i)) {
				Action a = target.getEntity(i).onInteract(GameBoard.getEntity(this.sourceID));
				if(a!=null){
					GameBoard.getEntity(this.sourceID).getParent().getGame().addAction(a);
				}
			}
		}
		return true;
	}

}
