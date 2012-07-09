package net.plaidypus.deadreckoning.actions;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.status.Status;

/**
 * The Class Action.
 * 
 * Used as a method of altering the state of the game on a turn-to-turn basis.
 */
public abstract class Action {

	/** The ID of the entity that produced the action*/
	public int sourceID;

	/** The tile on which the action is focused */
	public Tile target;

	/** Indicates if the action has been completed (set to true when done)*/
	public boolean completed;
	
	/** Indicates if carrying out this action should advance the turn (no for invenory management, etc.)*/
	public boolean takesTurn = true;

	/**
	 * actions are the main method of changing things in the game's environment.
	 * 
	 * @param source
	 * 			the tile the entity creating the action is standing on
	 * @param target
	 * 			the tile the action is going to target
	 */
	public Action(int sourceID, Tile target) {
		this.sourceID = sourceID;
		this.target = target;
		completed = false;
	}

	/**
	 * Sends a message to the stringputter. A Convenience method.
	 * 
	 * @param message the message to be displayed
	 */
	public static void sendMessage(String message) {
		DeadReckoningGame.instance.getMessageElement().addMessage(message);
	}

	/**
	 * Checks if this action is noticed.
	 * 
	 * Noticing means that carrying out this action should alert the camera to the action's source
	 * 
	 * @return true, if is noticed.
	 */
	protected abstract boolean isNoticed();

	/**
	 * Applies the action.
	 * 
	 * This can be thought of as the rough equivalent of the action's main method
	 * 
	 * @param delta
	 *            the delta
	 */
	public void applyAction(int delta) {
		if (!completed) {
			completed = apply(delta);
			if(completed){
				
				LivingEntity e = (LivingEntity) GameBoard.getEntity(this.sourceID);
				ArrayList<Status> stats = e.getConditions();
				for(int i=0; i<stats.size(); i++){
					stats.get(i).onActionProduce(this);
				}
				
				if(this.target!=null && !this.target.isOpen(Tile.LAYER_ACTIVE) &&
						LivingEntity.class.isAssignableFrom(this.target.getEntity(Tile.LAYER_ACTIVE).getClass())){
					e = (LivingEntity) this.target.getEntity(Tile.LAYER_ACTIVE);
					stats = e.getConditions();
					for(int i=0; i<stats.size(); i++){
						stats.get(i).onActionReceive(this);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String[] p = this.getClass().toString().split("actions.");

		return p[p.length - 1] + " " + GameBoard.getEntity(this.sourceID) + " -> " + this.target;
	}

	/**
	 * applies constant updates of the action.
	 * Used for animation handling and other cosmetics.
	 * Does not usually directly alter the game.
	 * 
	 * @param delta
	 *            the miliseconds elapsed since the last game update
	 * @return true, if successful
	 */
	protected abstract boolean apply(int delta);
}
