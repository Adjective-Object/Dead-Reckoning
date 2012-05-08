package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.MoveAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Movement.
 */
public class Movement extends Skill {

	/**
	 * Instantiates a new movement.
	 */
	public Movement() {
		super();
	}

	/**
	 * movement subclass of skill allows for easy generation of MoveAction
	 * Actions.
	 *
	 * @param l the l
	 */
	public Movement(LivingEntity l) {
		super(l);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#canTargetTile(net.plaidypus.deadreckoning.board.Tile)
	 */
	public boolean canTargetTile(Tile t) {
		return t.isOpen(Tile.LAYER_ACTIVE);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.deadreckoning.board.Tile)
	 */
	public Action makeAction(Tile target) {
		return new MoveAction(source, target, source.getLayer());
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#highlightRange(net.plaidypus.deadreckoning.board.GameBoard)
	 */
	public void highlightRange(GameBoard board) {
		board.highLightAvailablePaths(source.getLocation(), 1,
				Tile.LAYER_ACTIVE);
	}

}
