package net.plaidypus.deadreckoning.skills;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.LootAction;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Loot.
 */
public class Loot extends Skill {
	/**
	 * Instantiates a new loot.
	 * 
	 * @param source
	 *            the source
	 */
	public Loot(int sourceID) {
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
		for (int i = 0; i < Tile.numLayers; i++) {
			if (!target.isOpen(i) && InteractiveEntity.class.isAssignableFrom(target.getEntity(i).getClass())) {
				return new LootAction(this.sourceID, (InteractiveEntity)target.getEntity(i));
			}
		}
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
		if (t != getSource().getLocation()) {
			for (int i = 0; i < Tile.numLayers; i++) {
				if (!t.isOpen(i)) {
					return true;
				}
			}
		}
		return false;
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
		highlightRadial(board, 1);
	}

	@Override
	public void init() throws SlickException {
	}

}
