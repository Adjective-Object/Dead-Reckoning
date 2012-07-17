package net.plaidypus.deadreckoning.actions;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.save.Save;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangeBoardAction.
 */
public class ChangeBoardAction extends Action {

	/** The target floor. */
	String targetFloor;

	/**
	 * Instantiates a new change board action.
	 * 
	 * @param source
	 *            the source
	 * @param targetFloor
	 *            the target floor
	 */
	public ChangeBoardAction(int sourceID, String targetFloor) {
		super(sourceID, null);
		this.targetFloor = targetFloor;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	@Override
	protected boolean apply(int delta) throws SlickException {
		Save.enterNewMap(
			GameBoard.getEntity(this.sourceID).getParent().getGame(),
			GameBoard.getEntity(this.sourceID).getParent().getSaveID(),
			targetFloor);
		return true;
	}

}
