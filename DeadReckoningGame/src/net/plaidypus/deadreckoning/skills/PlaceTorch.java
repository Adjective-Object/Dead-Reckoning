package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeMapAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.entities.Torch;

// TODO: Auto-generated Javadoc
/**
 * The Class PlaceTorch.
 */
public class PlaceTorch extends Skill {

	/**
	 * Instantiates a new place torch.
	 */
	public PlaceTorch() {
		super();
	}

	/**
	 * Instantiates a new place torch.
	 *
	 * @param source the source
	 */
	public PlaceTorch(LivingEntity source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.deadreckoning.board.Tile)
	 */
	@Override
	public Action makeAction(Tile target) {
		return new ChangeMapAction(source, target, Tile.LAYER_PASSIVE_MAP,
				new Torch(target, Tile.LAYER_PASSIVE_MAP, Utilities.randInt(2,
						5)));
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
