package net.plaidypus.deadreckoning.actions;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.entities.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionSpawner.
 */
public class ActionSpawner extends Action {

	/** The actions. */
	ArrayList<Action> actions;

	/**
	 * Instantiates a new action spawner.
	 * 
	 * @param source
	 *            the source
	 * @param actions
	 *            the actions
	 */
	public ActionSpawner(Entity source, ArrayList<Action> actions) {
		super(source, source.getLocation());
		this.actions = actions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	protected boolean apply(int delta) {
		for (int i = 0; i < actions.size(); i++) {
			source.getParent().getGame().addAction(actions.get(i));
		}
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
