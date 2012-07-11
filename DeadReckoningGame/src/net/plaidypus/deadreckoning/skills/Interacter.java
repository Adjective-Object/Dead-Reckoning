package net.plaidypus.deadreckoning.skills;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.Interact;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Interacter.
 */
public class Interacter extends Skill {

	/**
	 * Instantiates a new interacter.
	 * 
	 * @param source
	 *            the source
	 */
	public Interacter(int sourceID) {
		super(sourceID);
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
		return new Interact(sourceID, target);
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
		return t.isOpen();
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
		board.highlightSquare(getSource().getX() - 1, getSource().getY());
		board.highlightSquare(getSource().getX() + 1, getSource().getY());
		board.highlightSquare(getSource().getX(), getSource().getY() - 1);
		board.highlightSquare(getSource().getX(), getSource().getY() + 1);
	}

	@Override
	public void init() throws SlickException {
	}
}
