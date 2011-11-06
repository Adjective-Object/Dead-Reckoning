package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class Attack extends Skill{

	public Attack(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		return new AttackAction(source.getLocation(),target,source.STR);
	}

	@Override
	public boolean canTargetTile(Tile t) {
		return !t.isOpen() && (t.getX()!=source.getX() || t.getY()!=source.getY());
	}

	@Override
	public void highlightRange(GameBoard board) {
		highlightRange(board,12);
	}

}
