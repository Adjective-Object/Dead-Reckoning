package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangeStateAction.
 */
public class ChangeStateAction extends Action {

	/** The state. */
	int state;

	/**
	 * Instantiates a new change state action.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param state
	 *            the state
	 * @param args
	 *            the args
	 */
	public ChangeStateAction(int sourceID, int state) {
		super(sourceID);
		takesTurn = false;
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	@Override
	protected boolean apply(int delta) {
		System.out.println("Moving to State:" + state);
		DeadReckoningGame.instance.enterState(state);
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
