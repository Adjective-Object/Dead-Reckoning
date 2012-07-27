package net.plaidypus.deadreckoning.actions;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

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
	public int sourceID, millisTaken;

	/** The focus of the action */
	private Tile targetTile;
	private Entity targetEntity;

	/** Indicates if the action has been completed (set to true when done)*/
	public boolean completed, logged, is;
	
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
	public Action(int sourceID, Tile targetTile) {
		this.sourceID = sourceID;
		this.targetTile = targetTile;
	}
	
	public Action(int sourceID, Entity targetEntity){
		this.sourceID = sourceID;
		this.targetEntity = targetEntity;
	}
	
	public Action(int sourceID){
		this.sourceID=sourceID;
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
	 * @throws SlickException 
	 */
	public void applyAction(int delta) throws SlickException {
		if (!completed) {
			if(!logged){
				//logging
				if(this.targetTile!=null){
					Log.debug(this.getClass().getSimpleName()+" applied from "+getSource()
							+"	to "+this.targetTile+", containing "
							+this.targetTile.getEntity(Tile.LAYER_ACTIVE));
				} else if (this.targetEntity!=null){
					Log.debug(this.getClass().getSimpleName()+" applied from "+getSource()
							+" to "+this.targetEntity + " at "+this.targetEntity.getLocation());
				} else{
					Log.debug(this.getClass().getSimpleName()+" applied from "+getSource());
				}
				logged=true;
			}
			completed = apply(delta);
			if(completed){
				// on action completion, call entities' status' action triggers
				LivingEntity e = (LivingEntity) GameBoard.getEntity(this.sourceID);
				ArrayList<Status> stats = e.getConditions();
				for(int i=0; i<stats.size(); i++){
					stats.get(i).onActionProduce(this);
				}
				
				if(this.targetTile!=null && !this.targetTile.isOpen(Tile.LAYER_ACTIVE) &&
						LivingEntity.class.isAssignableFrom(this.targetTile.getEntity(Tile.LAYER_ACTIVE).getClass())){
					e = (LivingEntity) this.targetTile.getEntity(Tile.LAYER_ACTIVE);
					stats = e.getConditions();
					for(int i=0; i<stats.size(); i++){
						stats.get(i).onActionReceive(this);
					}
				}
			}
			millisTaken+=delta;		
		}
	}

	protected InteractiveEntity getSource() {
		return (InteractiveEntity)GameBoard.getEntity(sourceID);
	}
	
	protected Tile getSourceTile() {
		return GameBoard.getEntity(sourceID).getLocation();
	}
	
	protected Entity getTargetEntity(){
		if(this.targetEntity!= null){
			return this.targetEntity;
		}
		else{
			return this.targetTile.getEntity(Tile.LAYER_ACTIVE);
		}
	}
	
	public Tile getTargetTile(){
		if(this.targetTile!=null){
			return this.targetTile;
		}
		else{
			return this.targetEntity.getLocation();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String[] p = this.getClass().toString().split("actions.");

		return p[p.length - 1] + " " + GameBoard.getEntity(this.sourceID) + " -> " + this.targetTile;
	}

	/**
	 * applies constant updates of the action.
	 * Used for animation handling and other cosmetics.
	 * Does not usually directly alter the game.
	 * 
	 * @param delta
	 *            the miliseconds elapsed since the last game update
	 * @return true, if successful
	 * @throws SlickException 
	 */
	protected abstract boolean apply(int delta) throws SlickException;
}
