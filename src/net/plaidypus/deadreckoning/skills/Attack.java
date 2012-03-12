package net.plaidypus.deadreckoning.skills;

import org.newdawn.slick.Image;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class Attack extends Skill {
	
	public Attack(){super();}
	
	public Attack(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		return new AttackAction(source, target, 20, false);
	}

	/**
	 * says (in essence) that any occupied tile that is not the same tile as the
	 * source's tile can be passed
	 **/
	public boolean canTargetTile(Tile t) {
		if( !t.isOpen(Tile.LAYER_ACTIVE) && !(t.getX() == source.getX() && t.getY() == source.getY())){
			return  t.getEntity(Tile.LAYER_ACTIVE).isInteractive();
		}
		return false;
	}

	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, source.getAttackRange());
	}

}
