package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.LootAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class Loot extends Skill{

	public Loot(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		return new LootAction(source.getLocation(),target);
	}

	@Override
	public boolean canTargetTile(Tile t) {
		if(!t.isOpen() && !t.getEntity().isInteractive()){
			return true;
		}
		return false;
	}

	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, 1);
	}

}
