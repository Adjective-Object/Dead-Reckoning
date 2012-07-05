package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class WaitAction.
 */
public class WaitAction extends Action {

	/**
	 * Instantiates a new wait action.
	 * 
	 * @param source
	 *            the source
	 */
	public WaitAction(int sourceID) {
		super(sourceID, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	protected boolean apply(int delta) {
		return true;
	}

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return GameBoard.getEntity(this.sourceID).getName() + " is useless";
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
