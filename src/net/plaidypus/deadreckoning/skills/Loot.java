package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.LootAction;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Loot.
 */
public class Loot extends Skill {

	/**
	 * Instantiates a new loot.
	 */
	public Loot() {
		super();
	}

	/**
	 * Instantiates a new loot.
	 *
	 * @param source the source
	 */
	public Loot(LivingEntity source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.deadreckoning.board.Tile)
	 */
	@Override
	public Action makeAction(Tile target) {
		for (int i = 0; i < Tile.numLayers; i++) {
			if (!target.isOpen(i)) {
				return new LootAction(source, target, i);// TODO fix that shit.
															// make it so I can
															// loot corpses on
															// the ground;
			}
		}
		return new WaitAction(source);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#canTargetTile(net.plaidypus.deadreckoning.board.Tile)
	 */
	@Override
	public boolean canTargetTile(Tile t) {
		if (t != source.getLocation()) {
			for (int i = 0; i < Tile.numLayers; i++) {
				if (!t.isOpen(i)) {
					return true;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#highlightRange(net.plaidypus.deadreckoning.board.GameBoard)
	 */
	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, 1);
	}

}
