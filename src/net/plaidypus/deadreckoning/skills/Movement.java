package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.MoveAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class Movement extends Skill {

	/**
	 * movement subclass of skill allows for easy generation of MoveAction
	 * Actions
	 * 
	 * @param l
	 */
	public Movement(LivingEntity l) {
		super(l);
	}

	public boolean canTargetTile(Tile t) {
		return t.isOpen();
	}

	public Action makeAction(Tile target) {
		return new MoveAction(source.getLocation(), target);
	}

	public void highlightRange(GameBoard board) {
		board.highLightAvailablePaths( source.getLocation(), source.getMovementSpeed());
	}

}
