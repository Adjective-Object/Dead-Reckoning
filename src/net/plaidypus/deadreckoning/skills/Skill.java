package net.plaidypus.deadreckoning.skills;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class Skill {
	
	int level;
	
	LivingEntity source;

	/**
	 * Skills are a method of having arbitrary abilities on entites (able to be
	 * stored in an arrayList) while keeping the Action class unique for each
	 * execution of an action.
	 * 
	 * @param source
	 */
	public Skill(LivingEntity source) {
		this.source = source;
	}
	
	/**
	 * created to have unlinekd instances of a skill that can be later bound. planned to be used only in character creation
	 */
	public Skill(){}
	
	/**
	 * binds the skill to a ceratain livingentity such that it is unecessary to constantly pass the correct livingentity to parse based off of.
	 * @param source
	 */
	public void bindTo(LivingEntity source){
		this.source=source;
	}
	
	/**
	 * generates an action targeted at a tile. Usually to be assigned to the
	 * source entity
	 * 
	 * @param target
	 * @return
	 */
	public abstract Action makeAction(Tile target);

	/**
	 * method for checking if a certain tile is highlight-able. used in
	 * highlightRadial && other highlighting methods.
	 * 
	 * @param t
	 *            the tile that must be checked if it can be targeted
	 * @return
	 */
	public abstract boolean canTargetTile(Tile t);

	/**
	 * method for highlighting all the tiles that a skill can target. made
	 * abstract so not all skills need to have attack ranges of certain shapes,
	 * etc.
	 * 
	 * @param board
	 */
	public abstract void highlightRange(GameBoard board);

	/**
	 * highlights all the tiles within a certain range of the skill's source.
	 * (radial). made to be called by highlightRange(GameBoard board);
	 * 
	 * @param board
	 * @param range
	 */
	public void highlightRadial(GameBoard board, int range) {
		board.clearHighlightedSquares();
		for (int vy = 0; vy < board.getHeight(); vy++) {
			for (int vx = 0; vx < board.getWidth(); vx++) {
				if (Math.sqrt(Math.pow(source.getX() - vx, 2)
						+ Math.pow(source.getY() - vy, 2)) <= range) {
					if (canTargetTile(board.getTileAt(vx, vy)) && board.getTileAt(vx, vy).lightLevel>=1) {
						board.getTileAt(vx, vy).setHighlighted(Tile.HIGHLIGHT_CONFIRM);
					} else {
						board.getTileAt(vx, vy).setHighlighted(Tile.HIGHLIGHT_DENY);
					}
				}
			}
		}
	}

	public boolean isInstant() {
		return false;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int level){
		this.level=level;
	}
}
