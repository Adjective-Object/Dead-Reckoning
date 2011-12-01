package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Tile;

public abstract class Action {

	public Tile source;
	public Tile target;

	public boolean completed;

	/**
	 * actions are the main method of changing things in the game's environment
	 * 
	 * @param source
	 *            the tile the entity creating the action is standing on
	 * @param target
	 *            the tile the action is going to target
	 */
	public Action(Tile source, Tile target) {
		this.source = source;
		this.target = target;
		completed = false;
	}

	/**
	 * applies the action. called repeatedley.
	 * 
	 * @param delta
	 *            the elapsed since the last call of apply in milliseconds
	 * @return if the action is complete (will not continue to call apply once completed)
	 */
	protected abstract boolean apply(int delta);

	public void applyAction(int delta) {
		if (!completed) {
			completed = apply(delta);
		}
	}
}
