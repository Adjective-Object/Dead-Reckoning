package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class Wait extends Skill{

	public Wait(LivingEntity l) {
		super(l);
	}

	@Override
	public Action makeAction(Tile target) {
		return new WaitAction(target);
	}

	@Override
	public boolean canTargetTile(Tile t) {
		return t==source.getLocation();
	}

	@Override
	public void highlightRange(GameBoard board) {
		highlightRange(board,0);
	}
	

}
