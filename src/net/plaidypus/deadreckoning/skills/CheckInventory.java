package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.CheckInventoryAction;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class CheckInventory extends Skill{

	public CheckInventory(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		return new CheckInventoryAction(source.getLocation(),target);
	}

	@Override
	public boolean canTargetTile(Tile t) {
		return t==source.getLocation();
	}
	
	public boolean isInstant(){
		return true;
	}
	
	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, 0);
	}

}
