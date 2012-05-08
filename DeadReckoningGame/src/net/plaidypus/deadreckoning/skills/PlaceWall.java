package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeMapAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.entities.Wall;

// TODO: Auto-generated Javadoc
/**
 * The Class PlaceWall.
 */
public class PlaceWall extends Skill {

	/**
	 * Instantiates a new place wall.
	 */
	public PlaceWall() {
		super();
	}

	/**
	 * Instantiates a new place wall.
	 *
	 * @param source the source
	 */
	public PlaceWall(LivingEntity source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.deadreckoning.board.Tile)
	 */
	@Override
	public Action makeAction(Tile target) {
		return new ChangeMapAction(source, target, Tile.LAYER_ACTIVE, new Wall(
				target, Tile.LAYER_ACTIVE));
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#canTargetTile(net.plaidypus.deadreckoning.board.Tile)
	 */
	@Override
	public boolean canTargetTile(Tile t) {
		return !t.isOpen(Tile.LAYER_ACTIVE);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#highlightRange(net.plaidypus.deadreckoning.board.GameBoard)
	 */
	@Override
	public void highlightRange(GameBoard board) {
		for (int y = 0; y < board.getHeight(); y++) {
			for (int x = 0; x < board.getWidth(); x++) {
				board.highlightSquare(x, y);
			}
		}
	}

}
