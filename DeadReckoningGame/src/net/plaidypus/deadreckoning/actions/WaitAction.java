package net.plaidypus.deadreckoning.actions;

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
	public WaitAction(Entity source) {
		super(source, source.getLocation());
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new wait action.
	 * 
	 * @param source
	 *            the source
	 * @param takesTurn
	 *            the takes turn
	 */
	public WaitAction(Entity source, boolean takesTurn) {
		super(source, source.getLocation());
		this.takesTurn = takesTurn;
		// TODO Auto-generated constructor stub
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
		return source.getName() + " is useless";
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
