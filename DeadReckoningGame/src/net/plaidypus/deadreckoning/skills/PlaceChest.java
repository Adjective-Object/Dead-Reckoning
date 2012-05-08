package net.plaidypus.deadreckoning.skills;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeMapAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Chest;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.items.EtcDrop;
import net.plaidypus.deadreckoning.items.Item;

// TODO: Auto-generated Javadoc
/**
 * The Class PlaceChest.
 */
public class PlaceChest extends Skill {

	/**
	 * Instantiates a new place chest.
	 */
	public PlaceChest() {
		super();
	}

	/**
	 * Instantiates a new place chest.
	 *
	 * @param source the source
	 */
	public PlaceChest(LivingEntity source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.deadreckoning.board.Tile)
	 */
	@Override
	public Action makeAction(Tile target) {
		ArrayList<Item> inv = new ArrayList<Item>(0);
		inv.add(new EtcDrop(0, Utilities.randInt(1, 10)));
		return new ChangeMapAction(source, target, Tile.LAYER_ACTIVE,
				new Chest(target, Tile.LAYER_ACTIVE, inv));
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
