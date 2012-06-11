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

	/** The args. */
	Object[] args;

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
	public ChangeStateAction(Entity source, Tile target, int state,
			Object[] args) {
		super(source, source.getLocation());
		takesTurn = false;
		this.state = state;
		this.args = args;
	}

	/**
	 * Instantiates a new change state action.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param state
	 *            the state
	 */
	public ChangeStateAction(Entity source, Tile target, int state) {
		this(source, target, state, new Object[] { GameplayElement.getImage(),
				null, (InteractiveEntity) (source) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	@Override
	protected boolean apply(int delta) {
		System.out.println("Moving to State:" + state);
		DeadReckoningGame.instance.getHudState(state).makeFrom(args);
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
