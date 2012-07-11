package net.plaidypus.deadreckoning.skills;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeStateAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckInventory.
 */
public class ChangeState extends Skill {
	
	int targetState;

	/**
	 * Instantiates a new check inventory.
	 * 
	 * @param source
	 *            the source
	 */
	public ChangeState(int sourceID, int targetState) {
		super(sourceID);
		this.targetState = targetState;
		this.instant = true;
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
		return new ChangeStateAction(sourceID, targetState);
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
