package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

public abstract class Action {

	public Entity source;
	public Tile target;

	public boolean completed, takesTurn;

	/**
	 * actions are the main method of changing things in the game's environment
	 * 
	 * @param source
	 *            the tile the entity creating the action is standing on
	 * @param target
	 *            the tile the action is going to target
	 */
	public Action(Entity source, Tile target) {
		this.source = source;
		this.target = target;
		completed = false;
		takesTurn=true;
	}

	/**
	 * applies the action. called repeatedley.
	 * 
	 * @param delta
	 *            the elapsed since the last call of apply in milliseconds
	 * @return if the action is complete (will not continue to call apply once completed)
	 */
	protected void sendMessage(String message){
		if(isNoticed()){
			DeadReckoningGame.instance.getMessageElement().addMessage(message);
		}
	}

	protected abstract boolean isNoticed();

	public void applyAction(int delta) {
		if (!completed) {
			completed = apply(delta);
		}
	}
	
	public String toString(){
		String[] p = this.getClass().toString().split("actions.");

		return p[p.length-1]+" "+this.source+" -> "+this.target;
	}

	protected abstract boolean apply(int delta);
}
