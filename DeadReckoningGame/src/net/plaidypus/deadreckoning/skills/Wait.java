package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Wait.
 */
public class Wait extends Skill {

	/**
	 * Instantiates a new wait.
	 * 
	 * @param l
	 *            the l
	 */
	public Wait(int sourceID) {
		super(sourceID);
		this.instant=true;
		this.level=1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.
	 * deadreckoning.board.Tile)
	 */
	@Override
	public Action makeAction(Tile target) {
		return new WaitAction(sourceID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.skills.Skill#canTargetTile(net.plaidypus.
	 * deadreckoning.board.Tile)
	 */
	@Override
	public boolean canTargetTile(Tile t) {
		return t == getSource().getLocation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.skills.Skill#highlightRange(net.plaidypus
	 * .deadreckoning.board.GameBoard)
	 */
	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, 0);
	}
	
	@Override
	public void init() throws SlickException {
	}

}
