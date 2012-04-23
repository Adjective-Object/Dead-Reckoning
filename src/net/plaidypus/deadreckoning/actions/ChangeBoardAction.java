package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Save;
import net.plaidypus.deadreckoning.entities.Entity;

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
	 * @param source the source
	 * @param targetFloor the target floor
	 */
	public ChangeBoardAction(Entity source, String targetFloor) {
		super(source, source.getLocation());
		this.targetFloor = targetFloor;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	@Override
	protected boolean isNoticed() {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	@Override
	protected boolean apply(int delta) {
		source.getParent()
				.getGame()
				.setBoard(
						Save.loadGame(source.getParent().getGame(), source
								.getParent().getSaveID(), targetFloor));
		return true;
	}

}
