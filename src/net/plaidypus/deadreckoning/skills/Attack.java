package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class Attack extends Skill {

	public Attack(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		return new AttackAction(source.getLocation(), target, source.STR, false);
	}

	/**
	 * says (in essence) that any occupied tile that is not the same tile as the
	 * source's tile can be passed
	 **/
	public boolean canTargetTile(Tile t) {
		if( !t.isOpen() && !(t.getX() == source.getX() && t.getY() == source.getY())){
			return  t.getEntity().isInteractive();
		}
		return false;
	}

	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, source.getAttackRange());
	}

}
